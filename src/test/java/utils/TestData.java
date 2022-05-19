package utils;

import io.qameta.allure.Step;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Класс хранит и получает тестовые данные
public class TestData {
    private String loginMail = MyBase64.decode(ConfProperties.getProperty("LOGIN"));
    private String passwordMail = MyBase64.decode(ConfProperties.getProperty("PASS"));
    private String startURL = "https://mail.yandex.ru/";

    @Step("Получение логина")
    public String getLoginMail() {
        return loginMail;
    }

    public String getPasswordMail() {
        return passwordMail;
    }

    public String getStartURL() {
        return startURL;
    }

    @Step("Получение текущей даты и времени")
    public String dateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String date = dateTime.format(formatter);
        return date;
    }

}
