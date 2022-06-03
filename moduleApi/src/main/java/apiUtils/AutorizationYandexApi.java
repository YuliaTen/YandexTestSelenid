package apiUtils;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import utils.User;

import static apiUtils.MethodsForYandexMailApi.getDataFromResponseHTML;

//класс для авторизации на яндексе
public class AutorizationYandexApi {
    private final String BASE_PATH = "registration-validations/auth";
    private final String BASE_URI = "https://passport.yandex.ru/";
    private String token;
    private Cookies cookiesStart;

    @Step("Получение Cookies авторизации")
    public Cookies getAutorizationCookies(User user) {
        Response response = RestAssured.get(BASE_URI);
        cookiesStart = response.getDetailedCookies();
        token = getDataFromResponseHTML(response, "name", "csrf_token");
        Response loginResponse = getLoginResponse(user);
        String track_id = loginResponse.getBody().jsonPath().getJsonObject("track_id");
        Response passwordResponse = getPasswordResponse(track_id, user);
        Cookies cookiesPass = passwordResponse.getDetailedCookies();
        String state = loginResponse.getBody().jsonPath().getJsonObject("state");
        Cookies cookiesAnswer = cookiesPass;
        if (state != null && state.equals("auth_challenge")) {
            cookiesAnswer = getControlAnswer(track_id, user);
        }
        return cookiesAnswer;
    }

    @Step("Прохождение контрольного вопроса")
    private Cookies getControlAnswer(String track_id, User user) {
        Response response =
                RestAssured.given()
                        .basePath(BASE_PATH)
                        .baseUri(BASE_URI)
                        .contentType(ContentType.URLENC)
                        .cookies(cookiesStart)
                        .formParam("challenge", "question")
                        .formParam("csrf_token", token)
                        .formParam("track_id", track_id)
                        .formParam(" answer", user.getAnswerControl())
                        .when()
                        .post("/challenge/commit");

        return response.getDetailedCookies();
    }

    @Step("Получение ответа на ввод логина")
    private Response getLoginResponse(User user) {
        return
                RestAssured.given()
                        .basePath(BASE_PATH)
                        .baseUri(BASE_URI)
                        .contentType(ContentType.URLENC)
                        .cookies(cookiesStart)
                        .formParam("login", user.getLogin())
                        .formParam("csrf_token", token)
                        .when()
                        .post("/multi_step/start")
                        .then()
                        .statusCode(200)
                        .extract().response();
    }

    @Step("Получение ответа на ввод пароля")
    private Response getPasswordResponse(String track_id, User user) {
        return
                RestAssured.given()
                        .basePath(BASE_PATH)
                        .baseUri(BASE_URI)
                        .contentType(ContentType.URLENC)
                        .cookies(cookiesStart)
                        .formParam("password", user.getPassword())
                        .formParam("track_id", track_id)
                        .formParam("csrf_token", token)
                        .when()
                        .post("/multi_step/commit_password")
                        .then()
                        .statusCode(200)
                        .extract().response();
    }
}
