package apiTest;

import dataSource.Base64;
import dataSource.ConfProperties;
import dataSource.TestData;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.json.simple.JSONObject;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class YandexApiTest {

    //@BeforeAll
    @Test
     void setUp(){
        Response response = get("https://passport.yandex.ru/auth/");

// Get all headers
        Headers allHeaders = response.getHeaders();
// Get a single header value:
        String headerName = response.getHeader("Cookie");

// Get all cookies as simple name-value pairs
        Map<String, String> allCookies = response.getCookies();
// Get a single cookie value:
        String cookieValue = response.getCookie("cookieName");

// Get status line
        String statusLine = response.getStatusLine();
// Get status code
        int statusCode = response.getStatusCode();

    }

    @Test
    void test1() {
        baseURI = "https://passport.yandex.ru/auth/";
        RequestSpecification httpRequest = given();

        JSONObject requestParams = new JSONObject();
        httpRequest.header("Content-Type", "application/json");
        requestParams.put("email", TestData.getLoginMail());
        requestParams.put("password", TestData.getPasswordMail());

        httpRequest.body(requestParams.toJSONString());
        Response response = httpRequest.post();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        System.out.println(response.body().asString());

    }
}
