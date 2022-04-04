package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;

public class MailPage {

    public static SelenideElement income() {
        return  $(By.linkText("Войти"));
    }

    public static SelenideElement loginMail() {
        return  $(By.name("login"));
    }

    public static SelenideElement passMail() {
        return  $(By.name("passwd"));
    }

    public static SelenideElement userIMG() {
        return  $(By.className("user-pic__image"));
    }

    public static SelenideElement loginTextIMG() {
        return  $(By.className("user-account__name"));
    }

    public static SelenideElement incomeMail() {
        return  $(byText("Входящие"));
    }

    public static SelenideElement outcomeMail() {
        return  $(byText("Отправленные"));
    }

    public static ElementsCollection incomeMailTextVisible() {
        return  $$(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']"));
    }

    public static ElementsCollection outcomeTextMail() {
        return  $$(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']"));
    }

    public static SelenideElement writeMail() {
        return  $(byText("Написать"));
    }

    public static SelenideElement whomMail() {
        return  $(byXpath("//div[@class='composeYabbles']"));
    }

    public static SelenideElement contantNewMail() {
        return $(byXpath("//div[@class='cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed " +
                "cke_contents_ltr cke_htmlplaceholder']"));
    }

    public static SelenideElement topicNewMail() {
        return  $(byName("subject"));
    }

    public static SelenideElement sendMail() {
        return $(byXpath("//button[@class='Button2 Button2_pin_circle-circle Button2_view_default Button2_size_l']"));
    }

    public static SelenideElement checkTopicOutMail() {
        return $(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']"));
    }

    public static SelenideElement checkContentOutMail() {
        return $(byXpath("//div[@class='MessageBody_body_pmf3j react-message-wrapper__body']"));
    }

    public static SelenideElement checkTopicIncomeMail() {
        return $(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']"));
    }

    public static SelenideElement checkContentIncomeMail() {
        return $(byXpath("//div[@class='MessageBody_body_pmf3j react-message-wrapper__body']"));
    }
}
