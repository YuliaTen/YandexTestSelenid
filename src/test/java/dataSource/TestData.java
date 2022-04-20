package dataSource;

import io.qameta.allure.Step;

public class TestData {
    private static String loginMail = Base64.decode(ConfProperties.getProperty("LOGIN"));
    private static String passwordMail = Base64.decode(ConfProperties.getProperty("PASS"));
    private static String sendEmail = "teenager111@yandex.ru";
    private static String myEmail = "yuliatestirova@yandex.ru";
    private static String content = "тестовое  тело письма";
    private static String topic = "Тестовое письмо";
    private static String contentMe = "тестовое тело письма себе";
    private static String topicMe = "Тестовое письмо себе";
    private static String  startURL = "https://mail.yandex.ru/";

    @Step("Получение тела письма себе")
    public static String getContentMe() {
        return contentMe;
    }

    public static void setContentMe(String contentMe) {
        TestData.contentMe = contentMe;
    }

    @Step("Получение темы письма себе")
    public static String getTopicMe() {
        return topicMe;
    }

    public static void setTopicMe(String topicMe) {
        TestData.topicMe = topicMe;
    }

    @Step("Получение логина")
    public static String getLoginMail() {
        return loginMail;
    }

    @Step("Получение пароля")
    public static String getPasswordMail() {
        return passwordMail;
    }

    @Step("Получение адреса e-mail")
    public static String getSendEmail() {
        return sendEmail;
    }

    public static void setSendEmail(String sendEmail) {
        TestData.sendEmail = sendEmail;
    }

    @Step("Получение адреса своей почты")
    public static String getMyEmail() {
        return myEmail;
    }

    public static void setMyEmail(String myEmail) {
        TestData.myEmail = myEmail;
    }

    @Step("Получение тела письма")
    public static String getContent() {
        return content;
    }

    public static void setContent(String content) {
        TestData.content = content;
    }

    @Step("Получение темы письма")
    public static String getTopic() {
        return topic;
    }

    public static void setTopic(String topic) {
        TestData.topic = topic;
    }

    @Step("Получение стартовой страницы")
    public static String getStartURL() {
        return startURL;
    }

    @Step("Явное ожидание")
    public static void waiting(long mlSec){
        try {
            Thread.sleep(mlSec);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
