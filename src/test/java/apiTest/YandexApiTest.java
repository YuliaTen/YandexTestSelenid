package apiTest;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DBConnect;
import utils.Letters;
import utils.TestData;
import utils.apiUtils.AutorizationYandexApi;
import utils.apiUtils.MethodsForApi;
import utils.apiUtils.modelMessage.MessageModels;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("API тесты для проверки работы Яндекс почты")
class YandexApiTest {
    private static AutorizationYandexApi auto = new AutorizationYandexApi();
    private static Cookies cookiesAuto;
    private static MethodsForApi methods = new MethodsForApi();
    private TestData testData = new TestData();
    private DBConnect dbConnect = new DBConnect();
    private Letters letter = dbConnect.getDataLetter();
    private static String ckey;
    private static Response responseMail;

    @BeforeAll
    @DisplayName("Авторизация и сохраниние необходимых данных, для уменьшения колличества запросов к сайту")
    static void setUP() {
        cookiesAuto = auto.getAutorizationCookies();
        responseMail = methods.getMailAnswer(cookiesAuto);
        ckey = methods.getDataFromResponseHTML(responseMail, "name", "_ckey");
    }

    @Test
    @DisplayName("Проверка статус кода и присутствие логина в ответе")
    void testLogin() {
        assertTrue(responseMail.getStatusCode() == 200, "Ожидаемый статус код 200");
        assertTrue(responseMail.asString().contains(testData.getLoginMail()),
                "Ожидалось нахождение логина: " + testData.getLoginMail() + " внутри тела ответа");
    }

    @Test
    @DisplayName("Проверяем наличие списка входящих сообщений")
    void testVisibleLetters() {
        String json = methods.getJsonStringForRequest(ckey, "1");
        ArrayList<MessageModels> message = methods.getMessageList(cookiesAuto, json);
        assertTrue(message.size() > 0, "Входящие сообщения отсутствуют");
    }

    @Test
    @DisplayName("Отправка письма другому человеку")
    void testSendEmail() {
        String topicDate = letter.getSubject() + " " + testData.dateTime();
        Letters letterNew = new Letters(topicDate, letter.getContext(), letter.getReciver());
        methods.getSendMessegeAnswer(cookiesAuto, ckey, letterNew);
        String json = methods.getJsonStringForRequest(ckey, "4");
        ArrayList<MessageModels> message = methods.getMessageList(cookiesAuto, json);
        assertTrue(message.size() > 0, "Отправленные сообщения отсутствуют");
        //  проверить что нужное письмо присутствует в ответе
        assertTrue(message.stream().filter(m -> m.getSubject().equalsIgnoreCase(topicDate)).count() == 1, "Письма с такой темой нет");
    }

    @Test
    @DisplayName("Отправка письма себе")
    void testSendMySelfEmail() {
        String topicDate = letter.getSubject() + " " + testData.dateTime();
        Letters letterNew = new Letters(topicDate, letter.getContext(), testData.getLoginMail() + "@yandex.ru");
        methods.getSendMessegeAnswer(cookiesAuto, ckey, letterNew);
        String json = methods.getJsonStringForRequest(ckey, "1");
        ArrayList<MessageModels> message = methods.getMessageList(cookiesAuto, json);
        assertTrue(message.size() > 0, "Входящие сообщения отсутствуют");
        //  проверить что нужное письмо присутствует в ответе
        assertTrue(message.stream().filter(m -> m.getSubject().equalsIgnoreCase(topicDate)).count() == 1, "Письма с такой темой нет");
    }

}
