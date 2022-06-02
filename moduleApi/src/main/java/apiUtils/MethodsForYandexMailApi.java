package apiUtils;

import apiUtils.modelMessage.Message;
import apiUtils.modelMessage.RootMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import utils.Letters;
import utils.User;
import java.util.List;

import static apiUtils.JsonRequestMessageClass.getJsonClass;
import static org.junit.jupiter.api.Assertions.assertEquals;

// класс хранит методы для упрощения работы с АПИ
public class MethodsForYandexMailApi {
    private final String BASE_PATH = "web-api";
    private final String BASE_URI = "https://mail.yandex.ru/";
    private String ckey;
    private Cookies cookiesAuto;

    @Step("Залогиниться в почте")
    public Response loginMail(User user){
        AutorizationYandexApi auto = new AutorizationYandexApi();
        cookiesAuto = auto.getAutorizationCookies(user);
        Response responseMail = getMailAnswer();
        ckey = getDataFromResponseHTML(responseMail, "name", "_ckey");
        return responseMail;
    }

    @Step("получаем ответ после обращения к главной странице почты")
    public Response getMailAnswer() {
        return
                RestAssured.given()
                        .cookies(cookiesAuto)
                        .when()
                        .get(BASE_URI);
    }

    @Step("получаем ключ из тела ответа, для формирования запроса по получению писем")
    public static String getDataFromResponseHTML(Response response, String attribute, String valueAttribute) {
        Document doc = Jsoup.parse(response.asString());
        Elements newsHeadlines = doc.getElementsByAttributeValue(attribute, valueAttribute);
        return newsHeadlines.isEmpty() ? "testkey" : newsHeadlines.get(0).attr("value");
    }

    @Step("Создаем json в виде строки, на основе ключа для дальнейшей передачи в запрос, номер папки( 1-входящие, 4-отправленные)")
    public String getJsonStringForRequest(String numberFolder) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "test";
        try {
            json = mapper.writeValueAsString(getJsonClass(ckey, numberFolder));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Step("получаем лист с сообщениями")
    public List<Message> getMessageList(String numberFolder) {
        String json = getJsonStringForRequest(numberFolder);
        Response responseMessage =
                RestAssured.given()
                        .baseUri(BASE_URI)
                        .basePath(BASE_PATH)
                        .contentType("text/plain; charset=utf-8")
                        .cookies(cookiesAuto)
                        .body(json).contentType("application/json")
                        .when()
                        .post("/models/liza1?_m=messages");
        RootMessage rootMessage = responseMessage.body().as(RootMessage.class);
        return rootMessage.getModels().get(0).getData().getMessage();
    }


    @Step("получаем сообщения")
    public Response getSendMessageAnswer(Letters letter, User user) {

        Response responseSendMessage =
                RestAssured.given()
                        .baseUri(BASE_URI)
                        .basePath(BASE_PATH)
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .cookies(cookiesAuto)
                        .formParam("current_folder", "6")
                        .formParam("from_mailbox", user.getLogin() + "@yandex.ru")
                        .formParam("send", "<div>" + letter.getContext() + "</div>")
                        .formParam("subj", letter.getSubject())
                        .formParam("to", "<" + letter.getReciver() + ">")
                        .formParam("_ckey", ckey)
                        .formParam("ttype", "html")
                        .when()
                        .post("/do-send/liza1?_send=true");

        assertEquals(200, responseSendMessage.getStatusCode(),
                "Ожидаемый статус код 200, пришел - " + responseSendMessage.getStatusCode());
        String status = responseSendMessage.getBody().jsonPath().getJsonObject("status");
        assertEquals("ok", status, "Статус должен соответствовать ok");
        return responseSendMessage;
    }
}
