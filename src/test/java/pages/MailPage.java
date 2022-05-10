package pages;

import utils.Letters;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import java.time.Duration;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;

// PageObject для страницы почты
public class MailPage {

    public SelenideElement income() {
        return $(By.linkText("Войти"));
    }

    public SelenideElement loginMail() {
        return $(By.name("login"));
    }

    public SelenideElement passMail() {
        return $(By.name("passwd"));
    }

    @Step("Проверка иконки пользователя")
    public SelenideElement userIMG() {
        return $(By.className("user-pic__image"));
    }

    @Step("Проверка логина внутри почты")
    public SelenideElement loginTextIMG() {
        return $(By.className("user-account__name"));
    }

    @Step("Входящие письма")
    public SelenideElement incomeMail() {
        return $x("//a[@href='#tabs/relevant']");
    }

    @Step("Отправленные письма")
    public SelenideElement outcomeMail() {
        return $x("//a[@href='#sent']");
    }

    @Step("Темы писем")
    public ElementsCollection subjectMailTextVisible() {
        return $$x("//*[contains(@class,'mail-MessageSnippet-Item_subject')]"); }

    @Step("Написать письмо")
    public SelenideElement writeMail() {return $x("//a[@href='#compose']");}

    @Step("Поле Кому")
    public SelenideElement whomMail() {
        return $x("//div[@class='composeYabbles']");
    }

    @Step("Проверить новые письма")
    public SelenideElement newMail() {
        return $x("//button[@aria-label='Проверить, есть ли новые письма']");
    }

    @Step("Вводим тело письма")
    public SelenideElement contantNewMail() {
        return $x("//*[contains(@class,'cke_editable_themed')]"); }

    @Step("Вводим тему")
    public SelenideElement topicNewMail() {
        return $(byName("subject"));
    }

    @Step("Отправить письмо")
    public SelenideElement sendMail() {
        return $x("//button[contains(@class,'Button2_view_default')]"); }

    @Step("Проверка темы письма")
    public void checkTopicMail(String topic) {
        SelenideElement element =
                $x("//*[contains(@class,'mail-MessageSnippet-Item_subject')]");
        element.shouldHave(text(topic)).click(); }

    @Step("Проверки тела  письма")
    public void checkContentMail(String content) {
        SelenideElement element = $x("//div[contains(@class,'MessageBody_body_pmf3j')]");
        element.shouldHave(text(content));  }

    @Step("Написать письмо")
    public void writeLetter(Letters letter) {
        writeMail().click();
        whomMail().setValue(letter.getReciver());
        topicNewMail().setValue(letter.getSubject());
        contantNewMail().setValue(letter.getContext());
        sendMail().click();
        newMail().click();
        outcomeMail().shouldBe(visible, Duration.ofSeconds(10)).click(); }
}
