package apiTest;

import apiUtils.MethodsForYandexMailApi;
import apiUtils.modelMessage.Message;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DBConnect;
import utils.Letters;
import utils.TestData;
import utils.User;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.TestData.dateTime;

@DisplayName("API тесты для проверки работы Яндекс почты")
class YandexApiTest {
    private static MethodsForYandexMailApi methods = new MethodsForYandexMailApi();
    private static TestData testData = new TestData();
    private static Response responseMail;
    private static User user;
    private Letters letter = (new DBConnect()).getDataLetter();
    private String topicDate;

    @BeforeAll
    @DisplayName("Авторизация и сохранение необходимых данных, для уменьшения количества запросов к сайту")
    static void setUP() {
        user = new User(testData.getLoginMail(), testData.getPasswordMail(), testData.getControlAnswer());
        responseMail = methods.loginMail(user);
    }

    @Test
    @DisplayName("Проверка статус кода и присутствие логина в ответе")
    void testLogin() {
        assertEquals(200, responseMail.getStatusCode(), "Ожидаемый статус код 200");
        assertTrue(responseMail.asString().contains(user.getLogin()),
                "Ожидалось нахождение логина: " + user.getLogin() + " внутри тела ответа");
    }

    @Test
    @DisplayName("Проверяем наличие списка входящих сообщений")
    void testVisibleLetters() {
        List<Message> message = methods.getMessageList("1");
        assertTrue(message.size() > 0, "Входящие сообщения отсутствуют");
    }

    @Test
    @DisplayName("Отправка письма другому человеку")
    void testSendEmail() {
        sendLetter(letter.getReciver());
        List<Message> message = methods.getMessageList("4");
        assertTrue(message.size() > 0, "Отправленные сообщения отсутствуют");
        //  проверить что нужное письмо присутствует в ответе
        assertTrue(message.stream().anyMatch(m -> m.getSubject().equalsIgnoreCase(topicDate)), "Письма с такой темой нет");
    }

    @Test
    @DisplayName("Отправка письма себе")
    void testSendMySelfEmail() {
        sendLetter(user.getLogin() + "@yandex.ru");
        List<Message> message = methods.getMessageList("1");
        assertTrue(message.size() > 0, "Входящие сообщения отсутствуют");
        //  проверить что нужное письмо присутствует в ответе
        assertTrue(message.stream().anyMatch(m -> m.getSubject().equalsIgnoreCase(topicDate)), "Письма с такой темой нет");
    }

    @Step("отправить письмо")
    void sendLetter(String reciver) {
        topicDate = letter.getSubject() + " " + dateTime();
        Letters letterNew = new Letters(topicDate, letter.getContext(), reciver);
        methods.getSendMessageAnswer(letterNew,  user);
    }
}
