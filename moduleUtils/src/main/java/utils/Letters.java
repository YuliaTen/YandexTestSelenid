package utils;

// класс для хранения объекта письма
public class Letters {
    //тема письма
    private String subject;
    //содержание письма
    private String context;
    //получатель письма
    private String reciver;

    public Letters() {}

    public Letters(String subject, String context, String reciver) {
        this.subject = subject;
        this.context = context;
        this.reciver = reciver;
    }

    @Override
    public String toString() {
        return "Letters{" +
                "subject='" + subject + '\'' +
                ", context='" + context + '\'' +
                ", reciver='" + reciver + '\'' +
                '}';
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }
}
