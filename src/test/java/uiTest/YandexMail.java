package uiTest;

import com.codeborne.selenide.Configuration;
import dataSource.Highlighter;
import dataSource.TestData;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.WebDriverRunner.addListener;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static dataSource.Highlighter.highlight;


public class YandexMail {

    @Before
    public  void setUp(){
        Configuration.browser = "chrome";
        Configuration.browserSize = "2100x1080";
        addListener(new Highlighter());
    }

    @Test
    public void visibleLoginAndImage(){
        open("https://mail.yandex.ru/");
        element(By.linkText("Войти")).click();
        element(By.name("login")).setValue(TestData.getLoginMail()).pressEnter();
        element(By.name("passwd")).setValue(TestData.getPasswordMail()).pressEnter();
        highlight(element(By.className("user-pic__image")).shouldBe(visible));
        highlight(element(By.className("user-account__name")).shouldHave(text(TestData.getLoginMail())));
    }

    @Test
    public void visibleElementLetters(){
        element(byText("Входящие")).click();
        elements(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']")).shouldHave(sizeGreaterThanOrEqual(1));
    }

    @Test
    public void sendLetter(){
        element(byText("Написать")).click();
        element(byXpath("//div[@class='composeYabbles']")).setValue(TestData.getSendEmail());
        element(byName("subject")).setValue(TestData.getTopic());
        element(byXpath("//div[@class='cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed " +
                "cke_contents_ltr cke_htmlplaceholder']")).setValue(TestData.getContent());
        element(byXpath("//button[@class='Button2 Button2_pin_circle-circle Button2_view_default Button2_size_l']")).click();
        element(byText("Отправленные")).click();
        elements(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']"))
                .filter(text(TestData.getTopic())).shouldBe(sizeGreaterThanOrEqual(1));
        element(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']")).shouldHave(text(TestData.getTopic())).click();
        element(byXpath("//div[@class='MessageBody_body_pmf3j react-message-wrapper__body']")).shouldHave(text(TestData.getContent()));


    }

    @Test
    public void sendLetterMyself(){
        element(byText("Написать")).click();
        element(byXpath("//div[@class='composeYabbles']")).pressEnter().setValue(TestData.getMyEmail());
        element(byName("subject")).setValue(TestData.getTopicMe());
        element(byXpath("//div[@class='cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed " +
                "cke_contents_ltr cke_htmlplaceholder']")).setValue(TestData.getContentMe());
        element(byXpath("//button[@class='Button2 Button2_pin_circle-circle Button2_view_default Button2_size_l']")).click();
        element(byText("Отправленные")).click();
        elements(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']"))
                .filter(text(TestData.getTopicMe())).shouldBe(sizeGreaterThanOrEqual(1));
        element(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']")).shouldHave(text(TestData.getTopicMe())).click();
        element(byXpath("//div[@class='MessageBody_body_pmf3j react-message-wrapper__body']")).shouldHave(text(TestData.getContentMe()));
        element(byText("Входящие")).click();
        element(byXpath("//span[@class='mail-MessageSnippet-Item mail-MessageSnippet-Item_subject']")).shouldHave(text(TestData.getTopicMe())).click();
        element(byXpath("//div[@class='MessageBody_body_pmf3j react-message-wrapper__body']")).shouldHave(text(TestData.getContentMe()));
    }


}
