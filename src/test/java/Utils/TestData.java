package Utils;

import io.qameta.allure.Step;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestData {
    private static String loginMail = Base64.decode(ConfProperties.getProperty("LOGIN"));
    private static String passwordMail = Base64.decode(ConfProperties.getProperty("PASS"));
    private  static DBConnect dbConnect = new DBConnect();
    private  static Letters letter = dbConnect.getDataLetter();
    private  static String sendEmail = letter.reciver;
    private static String myEmail = "yuliatestirova@yandex.ru";
    private  static String content = letter.context;
    private  static String topic = letter.subject;
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


    public static String getPasswordMail() {
        return passwordMail;
    }

    @Step("Получение адреса e-mail")
    public static String getSendEmail() {

        return sendEmail;
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

    @Step("Получение темы письма")
    public static String getTopic() {
        return topic ;
    }


    public static String getStartURL() {
        return startURL;
    }


    public static void waiting(long mlSec){
        try {
            Thread.sleep(mlSec);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Step("Получение текущей даты и времени")
    public static String dateTime(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String date = dateTime.format(formatter);
        return date;
    }


}
