package apiTest;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestData;
import utils.apiUtils.MethodsForApi;
import utils.apiUtils.modelMessage.Message;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("API тесты для проверки работы Яндекс почты")
class YandexApiTest {
    private static MethodsForApi methods = new MethodsForApi();
    private TestData testData = new TestData();
    private static Response responseMail;

    @BeforeAll
    @DisplayName("Авторизация и сохранение необходимых данных, для уменьшения количества запросов к сайту")
    static void setUP() {
       responseMail = methods.loginMail();
    }

    @Test
    @DisplayName("Проверка статус кода и присутствие логина в ответе")
    void testLogin() {
        assertEquals(responseMail.getStatusCode(), 200, "Ожидаемый статус код 200");
        assertTrue(responseMail.asString().contains(testData.getLoginMail()),
                "Ожидалось нахождение логина: " + testData.getLoginMail() + " внутри тела ответа");
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
        String topicDate = methods.getTopicDate();
        List<Message> message = methods.sendLetter("other","4",topicDate);
        assertTrue(message.size() > 0, "Отправленные сообщения отсутствуют");
        //  проверить что нужное письмо присутствует в ответе
        assertEquals(message.stream().anyMatch(m -> m.getSubject().equalsIgnoreCase(topicDate)), true, "Письма с такой темой нет");
    }

    @Test
    @DisplayName("Отправка письма себе")
    void testSendMySelfEmail() {
        String topicDate = methods.getTopicDate();
        List<Message> message = methods.sendLetter("me","1",topicDate);
        assertTrue(message.size() > 0, "Входящие сообщения отсутствуют");
        //  проверить что нужное письмо присутствует в ответе
        assertEquals(message.stream().anyMatch(m -> m.getSubject().equalsIgnoreCase(topicDate)), true, "Письма с такой темой нет");
    }
}
