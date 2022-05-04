package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import Utils.TestData;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
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

    @Step("Проверка иконки пользователя")
    public static SelenideElement userIMG() {
        return  $(By.className("user-pic__image"));
    }

    @Step("Проверка логина внутри почты")
    public static SelenideElement loginTextIMG() {
        return  $(By.className("user-account__name"));
    }

    @Step("Входящие письма")
    public static SelenideElement incomeMail() {
        return  $(byText("Входящие"));
    }

    @Step("Отправленные письма")
    public static SelenideElement outcomeMail() {
        return  $(byText("Отправленные"));
    }

    @Step("Темы входящих писем")
    public static ElementsCollection incomeMailTextVisible() {
        return  $$(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']"));
    }

    @Step("Темы отправленных писем")
    public static ElementsCollection outcomeTextMail() {
        return  $$(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']"));
    }

    @Step("Написать письмо")
    public static SelenideElement writeMail() {
        return  $(byText("Написать"));
    }

    @Step("Поле Кому")
    public static SelenideElement whomMail() {
        return  $(byXpath("//div[@class='composeYabbles']"));
    }

    @Step("Проверить новые письма")
    public static SelenideElement newMail() {
        return  $(byXpath("//span[@title='Проверить, есть ли новые письма (F9)']"));
    }

    @Step("Вводим тело письма")
    public static SelenideElement contantNewMail() {
        return $(byXpath("//div[@class='cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed " +
                "cke_contents_ltr cke_htmlplaceholder']"));
    }

    @Step("Вводим тему")
    public static SelenideElement topicNewMail() {
        return  $(byName("subject"));
    }

    @Step("Отправить письмо")
    public static SelenideElement sendMail() {
        return $(byXpath("//button[@class='Button2 Button2_pin_circle-circle Button2_view_default Button2_size_l']"));
    }

    @Step("Проверка темы отправленного письма")
    public static SelenideElement checkTopicOutMail() {
        return $(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']"));
    }

    @Step("Проверки тела отправленного письма")
    public static SelenideElement checkContentOutMail() {
        return $(byXpath("//div[@class='MessageBody_body_pmf3j react-message-wrapper__body']"));
    }

    @Step("Проверки темы входящего письма")
    public static SelenideElement checkTopicIncomeMail() {
        return $(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']"));
    }

    @Step("Проверки тела входящего письма")
    public static SelenideElement checkContentIncomeMail() {
        return $(byXpath("//div[@class='MessageBody_body_pmf3j react-message-wrapper__body']"));
    }

    @Step("Написать письмо")
    public static String writeLetter(String email, String topic, String context){
        String topicDate = topic+ " " + TestData.dateTime();
        writeMail().click();
        whomMail().setValue(email);
        topicNewMail().setValue(topicDate);
        contantNewMail().setValue(context);
        sendMail().click();
        newMail().click();
        outcomeMail().shouldBe(visible, Duration.ofSeconds(10)).click();
        return topicDate;
    }
}
