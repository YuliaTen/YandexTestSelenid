package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;

public class MailPage {

    @Step("Получение элемента Войти")
    public static SelenideElement income() {
        return  $(By.linkText("Войти"));
    }

    @Step("Получение элемента для ввода логина")
    public static SelenideElement loginMail() {
        return  $(By.name("login"));
    }

    @Step("Получение элемента для ввода пароля")
    public static SelenideElement passMail() {
        return  $(By.name("passwd"));
    }

    @Step("Получение элемента для проверки иконки пользователя")
    public static SelenideElement userIMG() {
        return  $(By.className("user-pic__image"));
    }

    @Step("Получение элемента для проверки логина внутри почты")
    public static SelenideElement loginTextIMG() {
        return  $(By.className("user-account__name"));
    }

    @Step("Получение элемента Входящих писем")
    public static SelenideElement incomeMail() {
        return  $(byText("Входящие"));
    }

    @Step("Получение элемента Отправленных писем")
    public static SelenideElement outcomeMail() {
        return  $(byText("Отправленные"));
    }

    @Step("Получение элементов темы входящих писем")
    public static ElementsCollection incomeMailTextVisible() {
        return  $$(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']"));
    }

    @Step("Получение элементов темы отправленных писем")
    public static ElementsCollection outcomeTextMail() {
        return  $$(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']"));
    }

    @Step("Получение элемента Написать письмо")
    public static SelenideElement writeMail() {
        return  $(byText("Написать"));
    }

    @Step("Получение элемента поля Кому")
    public static SelenideElement whomMail() {
        return  $(byXpath("//div[@class='composeYabbles']"));
    }

    @Step("Получение элемента для ввода тела письма")
    public static SelenideElement contantNewMail() {
        return $(byXpath("//div[@class='cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed " +
                "cke_contents_ltr cke_htmlplaceholder']"));
    }

    @Step("Получение элемента для ввода Темы письма")
    public static SelenideElement topicNewMail() {
        return  $(byName("subject"));
    }

    @Step("Получение элемента Отправить письмо")
    public static SelenideElement sendMail() {
        return $(byXpath("//button[@class='Button2 Button2_pin_circle-circle Button2_view_default Button2_size_l']"));
    }

    @Step("Получение элемента для проверки темы отправленного письма")
    public static SelenideElement checkTopicOutMail() {
        return $(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']"));
    }

    @Step("Получение элемента для проверки тела отправленного письма")
    public static SelenideElement checkContentOutMail() {
        return $(byXpath("//div[@class='MessageBody_body_pmf3j react-message-wrapper__body']"));
    }

    @Step("Получение элемента для проверки темы входящего письма")
    public static SelenideElement checkTopicIncomeMail() {
        return $(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']"));
    }

    @Step("Получение элемента для проверки тела входящего письма")
    public static SelenideElement checkContentIncomeMail() {
        return $(byXpath("//div[@class='MessageBody_body_pmf3j react-message-wrapper__body']"));
    }
}
