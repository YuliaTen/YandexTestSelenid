package utils;

// класс для создания экземпляра пользователя для тестирования
public class User {
    private  String login;
    private  String password;
    // ответ на контрольный вопрос в используемой почте
    private  String answerControl;

    public User(String login, String password, String answerControl) {
        this.login = login;
        this.password = password;
        this.answerControl = answerControl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAnswerControl() {
        return answerControl;
    }

    public void setAnswerControl(String answerControl) {
        this.answerControl = answerControl;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", answerControl='" + answerControl + '\'' +
                '}';
    }
}
