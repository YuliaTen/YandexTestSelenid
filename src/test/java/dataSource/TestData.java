package dataSource;

import pages.MailPage;

import static com.codeborne.selenide.Selenide.open;

public class TestData {
    static final String LOGIN = "eXVsaWF0ZXN0aXJvdmE";
    static final String PASS = "QXNkTGtqVmJuLTUxMA";
    private static String loginMail = Base64.decode(LOGIN);
    private static String passwordMail = Base64.decode(PASS);
    private static String sendEmail = "teenager111@yandex.ru";
    private static String myEmail = "yuliatestirova@yandex.ru";
    private static String content = "тестовое  тело письма";
    private static String topic = "Тестовое письмо";
    private static String contentMe = "тестовое тело письма себе";
    private static String topicMe = "Тестовое письмо себе";
    private static String  startURL = "https://mail.yandex.ru/";

    public static String getContentMe() {
        return contentMe;
    }

    public static void setContentMe(String contentMe) {
        TestData.contentMe = contentMe;
    }

    public static String getTopicMe() {
        return topicMe;
    }

    public static void setTopicMe(String topicMe) {
        TestData.topicMe = topicMe;
    }

    public static String getLoginMail() {
        return loginMail;
    }


    public static String getPasswordMail() {
        return passwordMail;
    }


    public static String getSendEmail() {
        return sendEmail;
    }

    public static void setSendEmail(String sendEmail) {
        TestData.sendEmail = sendEmail;
    }

    public static String getMyEmail() {
        return myEmail;
    }

    public static void setMyEmail(String myEmail) {
        TestData.myEmail = myEmail;
    }

    public static String getContent() {
        return content;
    }

    public static void setContent(String content) {
        TestData.content = content;
    }

    public static String getTopic() {
        return topic;
    }

    public static void setTopic(String topic) {
        TestData.topic = topic;
    }

    public static String getStartURL() {
        return startURL;
    }

    public static void setStartURL(String startURL) {
        TestData.startURL = startURL;
    }

}
