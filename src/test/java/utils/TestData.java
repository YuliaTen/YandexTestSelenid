package utils;

import io.qameta.allure.Step;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Класс хранит и получает тестовые данные
public class TestData {
    private String loginMail = Base64.decode(ConfProperties.getProperty("LOGIN"));
    private String passwordMail = Base64.decode(ConfProperties.getProperty("PASS"));
    private DBConnect dbConnect = new DBConnect();
    private Letters letter = dbConnect.getDataLetter();
    private String myEmail = "yuliatestirova@yandex.ru";
    private String contentMe = "тестовое тело письма себе";
    private String topicMe = "Тестовое письмо себе";
    private String startURL = "https://mail.yandex.ru/";

    public Letters getLetter() {
        return letter;
    }

    @Step("Получение тела письма себе")
    public String getContentMe() {
        return contentMe;
    }

    @Step("Получение темы письма себе")
    public String getTopicMe() {
        return topicMe;
    }

    @Step("Получение логина")
    public String getLoginMail() {
        return loginMail;
    }

    public String getPasswordMail() {
        return passwordMail;
    }

    @Step("Получение адреса своей почты")
    public String getMyEmail() {
        return myEmail;
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
