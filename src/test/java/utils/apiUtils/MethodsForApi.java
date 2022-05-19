package utils.apiUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.Letters;
import utils.TestData;
import utils.apiUtils.modelMessage.MessageModels;
import utils.apiUtils.modelMessage.Root;
import java.io.FileReader;
import java.util.ArrayList;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

// класс хранит методы для упрощения работы с АПИ
public class MethodsForApi {
    private static JsonRequestMessageClass jsonRequestMessageClass = new JsonRequestMessageClass();
    private static TestData testData = new TestData();


    @Step("получаем ответ после обращения к главной странице почты")
    public Response getMailAnswer(Cookies cookies) {
        Response responseMail =
                given()
                        .cookies(cookies)
                        .when()
                        .get("https://mail.yandex.ru/");
        return responseMail;
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
    public String getJsonStringForRequest(String key, String numberFolder) {
        ObjectMapper mapper1 = new ObjectMapper();
        String json = "test";
        try {
            json = mapper1.writeValueAsString(jsonRequestMessageClass.getJsonClass(key, numberFolder));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Step("получаем лист с сообщениями")
    public ArrayList<MessageModels> getMessageList(Cookies cookies, String json) {
        Response responseMessage =
                given()
                        .contentType("text/plain; charset=utf-8")
                        .cookies(cookies)
                        .body(json).contentType("application/json")
                        .when()
                        .post("https://mail.yandex.ru/web-api/models/liza1?_m=messages");
        Root root2 = responseMessage.body().as(Root.class);
        ArrayList<MessageModels> message = root2.getModels().get(0).getData().getMessage();
        return message;
    }

    @Step("получаем сообщения")
    public Response getSendMessegeAnswer(Cookies cookies, String ckey, Letters letter) {

        Response responseSendMessage =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .cookies(cookies)
                        .formParam("current_folder", "6")
                        .formParam("from_mailbox", testData.getLoginMail() + "@yandex.ru")
                        .formParam("send", "<div>" + letter.getContext() + "</div>")
                        .formParam("subj", letter.getSubject())
                        .formParam("to", "<" + letter.getReciver() + ">")
                        .formParam("_ckey", ckey)
                        .formParam("ttype", "html")
                        .when()
                        .post("https://mail.yandex.ru/web-api/do-send/liza1?_send=true");
        assertTrue(responseSendMessage.getStatusCode() == 200,
                "Ожидаемый статус код 200, пришел - " + responseSendMessage.getStatusCode());
        String status = responseSendMessage.getBody().jsonPath().getJsonObject("status");
        assertTrue(status.equals("ok"), "Статус должен соответствовать ok");
        return responseSendMessage;
    }

    @Step("мой вспомогательный метод чтение json из файла локально, потом убрать")
    public ArrayList<MessageModels> getMessageListFromFile() {
        ArrayList<MessageModels> message = null;
        try (FileReader reader = new FileReader("jsonka")) {
            ObjectMapper mapper = new ObjectMapper();
            Root root = mapper.readValue(reader, Root.class);
            message = root.getModels().get(0).getData().getMessage();
            assertTrue(message.size() > 0, "Присутствуют сообщения в списке");
        } catch (Exception e) {
            System.out.println(e);
        }
        return message;

    }
}
