package utils.apiUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.DBConnect;
import utils.Letters;
import utils.TestData;
import utils.apiUtils.modelMessage.Message;
import utils.apiUtils.modelMessage.RootMessage;

import java.io.FileReader;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// класс хранит методы для упрощения работы с АПИ
public class MethodsForApi {
    private static JsonRequestMessageClass jsonRequestMessageClass = new JsonRequestMessageClass();
    private static TestData testData = new TestData();
    private final String BASE_PATH = "web-api";
    private final String BASE_URI = "https://mail.yandex.ru/";
    private static Cookies cookiesAuto;
    private static String ckey;
    private static Response responseMail;
    private static AutorizationYandexApi auto = new AutorizationYandexApi();
    private DBConnect dbConnect = new DBConnect();
    private Letters letter = dbConnect.getDataLetter();

    @Step("Залогиниться в почте")
    public Response loginMail(){
        cookiesAuto = auto.getAutorizationCookies();
        responseMail = getMailAnswer(cookiesAuto);
        ckey = getDataFromResponseHTML(responseMail, "name", "_ckey");
        return responseMail;
    }

    @Step("Отправить письмо")
    public List<Message> sendLetter(String reciver,String folderNum, String topicDate){
        if (reciver.equalsIgnoreCase("me")) {
            reciver = testData.getLoginMail() + "@yandex.ru";
        }
        else reciver =  letter.getReciver();
        Letters letterNew = new Letters(topicDate, letter.getContext(), reciver);
        getSendMessageAnswer(letterNew);
        return getMessageList(folderNum);
    }

    @Step("Получить тему письма")
    public String getTopicDate(){
        return letter.getSubject() + " " + testData.dateTime();
    }


    @Step("получаем ответ после обращения к главной странице почты")
    public Response getMailAnswer(Cookies cookies) {
        return
                given()
                        .cookies(cookies)
                        .when()
                        .get(BASE_URI);
    }

    @Step("получаем ключ из тела ответа, для формирования запроса по получению писем")
    public String getDataFromResponseHTML(Response response, String attribute, String valueAttribute) {
        Document doc = Jsoup.parse(response.asString());
        Elements newsHeadlines = doc.getElementsByAttributeValue(attribute, valueAttribute);
        String key = "testkey";
        for (Element headline : newsHeadlines) {
            key = headline.attr("value");
        }
        return key;
    }

    @Step("Создаем json в виде строки, на основе ключа для дальнейшей передачи в запрос, номер папки( 1-входящие, 4-отправленные)")
    public String getJsonStringForRequest(String numberFolder) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "test";
        try {
            json = mapper.writeValueAsString(jsonRequestMessageClass.getJsonClass(ckey, numberFolder));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Step("получаем лист с сообщениями")
    public List<Message> getMessageList(String numberFolder) {
        String json = getJsonStringForRequest(numberFolder);
        Response responseMessage =
                given()
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
    public Response getSendMessageAnswer(Letters letter) {

        Response responseSendMessage =
                given()
                        .baseUri(BASE_URI)
                        .basePath(BASE_PATH)
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .cookies(cookiesAuto)
                        .formParam("current_folder", "6")
                        .formParam("from_mailbox", testData.getLoginMail() + "@yandex.ru")
                        .formParam("send", "<div>" + letter.getContext() + "</div>")
                        .formParam("subj", letter.getSubject())
                        .formParam("to", "<" + letter.getReciver() + ">")
                        .formParam("_ckey", ckey)
                        .formParam("ttype", "html")
                        .when()
                        .post("/do-send/liza1?_send=true");
        assertEquals(responseSendMessage.getStatusCode(), 200,
                "Ожидаемый статус код 200, пришел - " + responseSendMessage.getStatusCode());
        String status = responseSendMessage.getBody().jsonPath().getJsonObject("status");
        assertEquals(status,"ok", "Статус должен соответствовать ok");
        return responseSendMessage;
    }
}
