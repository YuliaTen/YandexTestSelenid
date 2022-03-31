package dataSource;

public class TestData {
    static final String LOGIN = "eXVsaWF0ZXN0aXJvdmE";
    static final String PASS = "QXNkTGtqVmJuLTUxMA";
    static String loginMail = Base64.decode(LOGIN);   //"yuliatestirova";
    static String passwordMail = Base64.decode(PASS);  //"AsdLkjVbn-510";
    static String sendEmail = "teenager111@yandex.ru";
    static String myEmail = "yuliatestirova@yandex.ru";
    static String content = "тестовое  тело письма";
    static String topic = "Тестовое письмо";
    static String contentMe = "тестовое тело письма себе";
    static String topicMe = "Тестовое письмо себе";

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

    public static void setLoginMail(String loginMail) {
        TestData.loginMail = loginMail;
    }

    public static String getPasswordMail() {
        return passwordMail;
    }

    public static void setPasswordMail(String passwordMail) {
        TestData.passwordMail = passwordMail;
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
}
