package Utils;

public class Letters  {

    String subject;
    String context;
    String reciver;

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
