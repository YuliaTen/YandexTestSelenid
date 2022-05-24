package utils.apiUtils;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import utils.TestData;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

//класс для авторизации на яндексе
public class AutorizationYandexApi {
    private TestData testData = new TestData();
    private MethodsForApi methods = new MethodsForApi();
    private final String BASE_PATH = "registration-validations/auth";
    private final String BASE_URI = "https://passport.yandex.ru/";

    @Step("Получение Cookies авторизации")
    public Cookies getAutorizationCookies() {
        Response response = get(BASE_URI);
        Cookies cookiesStart = response.getDetailedCookies();
        String token = methods.getDataFromResponseHTML(response, "name", "csrf_token");
        Response loginResponse = getLoginResponse(cookiesStart, token);
        String track_id = loginResponse.getBody().jsonPath().getJsonObject("track_id");
        Response passwordResponse = getPasswordResponse(cookiesStart, token, track_id);
        Cookies cookiesPass = passwordResponse.getDetailedCookies();
        String state = loginResponse.getBody().jsonPath().getJsonObject("state");
        Cookies cookiesAnswer = cookiesPass;
        if (state != null && state.equals("auth_challenge")) {
                cookiesAnswer = getControlAnswer(cookiesStart, track_id, token);
        }
        return cookiesAnswer;
    }

    @Step("Прохождение контрольного вопроса")
    private Cookies getControlAnswer(Cookies cookies, String track_id, String token) {
        Response response =
                given()
                        .basePath(BASE_PATH)
                        .baseUri(BASE_URI)
                        .contentType(ContentType.URLENC)
                        .cookies(cookies)
                        .formParam("challenge", "question")
                        .formParam("csrf_token", token)
                        .formParam("track_id", track_id)
                        .formParam(" answer", "344093")
                        .when()
                        .post("/challenge/commit");

        return response.getDetailedCookies();
    }

    @Step("Получение ответа на ввод логина")
    private Response getLoginResponse(Cookies cookies, String token) {
        return
                given()
                        .basePath(BASE_PATH)
                        .baseUri(BASE_URI)
                        .contentType(ContentType.URLENC)
                        .cookies(cookies)
                        .formParam("login", testData.getLoginMail())
                        .formParam("csrf_token", token)
                        .when()
                        .post("/multi_step/start")
                        .then()
                        .statusCode(200)
                        .extract().response();
    }

    @Step("Получение ответа на ввод пароля")
    private Response getPasswordResponse(Cookies cookies, String token, String track_id) {
        return
                given()
                        .basePath(BASE_PATH)
                        .baseUri(BASE_URI)
                        .contentType(ContentType.URLENC)
                        .cookies(cookies)
                        .formParam("password", testData.getPasswordMail())
                        .formParam("track_id", track_id)
                        .formParam("csrf_token", token)
                        .when()
                        .post("/multi_step/commit_password")
                        .then()
                        .statusCode(200)
                        .extract().response();
    }
}
