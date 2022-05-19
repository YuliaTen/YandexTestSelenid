package utils.apiUtils;

import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import utils.TestData;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

//класс для авторизации на яндексе
public class AutorizationYandexApi {
    private TestData testData = new TestData();
    private MethodsForApi methods = new MethodsForApi();

    @Step("Получение Cookies авторизации")
    public Cookies getAutorizationCookies() {
        Response response = get("https://passport.yandex.ru/");
        Cookies cookiesStart = response.getDetailedCookies();
        String token = methods.getDataFromResponseHTML(response, "name", "csrf_token");
        Response response2 = getLoginResponse(cookiesStart, token);
        String track_id = response2.getBody().jsonPath().getJsonObject("track_id");
        Response response3 = getPasswordResponse(cookiesStart, token, track_id);
        Cookies cookiesPass = response3.getDetailedCookies();
        String state = response2.getBody().jsonPath().getJsonObject("state");
        Cookies cookiesAnswer = cookiesPass;
        if (state != null) {
            if (state.equals("auth_challenge")) {
                cookiesAnswer = getControlAnswer(cookiesStart, track_id, token);
            }
        }
        System.out.println(response3);

        return cookiesAnswer;
    }

    @Step("Прохождение контрольного вопроса")
    private Cookies getControlAnswer(Cookies cookies, String track_id, String token) {
        Response response =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .cookies(cookies)
                        .formParam("challenge", "question")
                        .formParam("csrf_token", token)
                        .formParam("track_id", track_id)
                        .formParam(" answer", "344093")
                        .when()
                        .post("https://passport.yandex.ru/registration-validations/auth/challenge/commit");

        Cookies cookiesAnswer = response.getDetailedCookies();
        return cookiesAnswer;
    }

    @Step("Получение ответа на ввод логина")
    private Response getLoginResponse(Cookies cookies, String token) {
        Response response =
                given()
                        .contentType("application/x-www-form-urlencoded")
                        .cookies(cookies)
                        .formParam("login", testData.getLoginMail())
                        .formParam("csrf_token", token)
                        .when()
                        .post("https://passport.yandex.ru/registration-validations/auth/multi_step/start");
        assertTrue(response.getStatusCode() == 200);
        return response;
    }

    @Step("Получение ответа на ввод пароля")
    private Response getPasswordResponse(Cookies cookies, String token, String track_id) {
        Response response =
                given()
                        .contentType("application/x-www-form-urlencoded")
                        .cookies(cookies)
                        .formParam("password", testData.getPasswordMail())
                        .formParam("track_id", track_id)
                        .formParam("csrf_token", token)
                        .when()
                        .post("https://passport.yandex.ru/registration-validations/auth/multi_step/commit_password");
        assertTrue(response.getStatusCode() == 200);
        return response;
    }
}
