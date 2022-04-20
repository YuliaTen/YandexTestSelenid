package uiTest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import dataSource.Highlighter;
import dataSource.TestData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MailPage;
import java.time.Duration;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverRunner.addListener;
import static dataSource.Highlighter.highlight;

@DisplayName("UI тесты для проверки работы Яндекс почты")
public class YandexMail extends MailPage {

    @BeforeAll
    @DisplayName("Стартовые настройки браузера и вход в почту")
    static void setUp(){
        Configuration.browser = "chrome";
        addListener(new Highlighter());
        open(TestData.getStartURL());
        WebDriverRunner.getWebDriver().manage().window().maximize();
        income().click();
        loginMail().setValue(TestData.getLoginMail()).pressEnter();
        passMail().setValue(TestData.getPasswordMail()).pressEnter();
    }

    @Test
    @DisplayName("Проверка отображения иконки пользователя и логина")
    void visibleLoginAndImage(){
        highlight(element(userIMG().shouldBe(visible, Duration.ofSeconds(10))));
        highlight(element(loginTextIMG().shouldHave(text(TestData.getLoginMail()))));
    }

    @Test
    @DisplayName("Проверка видимости писем")
    void visibleElementLetters(){
        incomeMail().shouldBe(visible, Duration.ofSeconds(10)).click();
        incomeMailTextVisible().shouldHave(sizeGreaterThanOrEqual(1));
    }

    @Test
    @DisplayName("Отправка письма другому человеку")
    void sendLetter(){
        writeMail().click();
        whomMail().setValue(TestData.getSendEmail());
        topicNewMail().setValue(TestData.getTopic());
        contantNewMail().setValue(TestData.getContent());
        sendMail().click();
        TestData.waiting(3000);
        outcomeMail().shouldBe(visible, Duration.ofSeconds(10)).click();
        outcomeTextMail().filter(text(TestData.getTopic())).shouldBe(sizeGreaterThanOrEqual(1));
        checkTopicOutMail().shouldHave(text(TestData.getTopic())).click();
        checkContentOutMail().shouldHave(text(TestData.getContent()));
    }

    @Test
    @DisplayName("Отправка письма себе")
    void sendLetterMyself(){
        writeMail().click();
        whomMail().setValue(TestData.getMyEmail());
        topicNewMail().setValue(TestData.getTopicMe());
        contantNewMail().setValue(TestData.getContentMe());
        sendMail().click();
        TestData.waiting(3000);
        outcomeMail().shouldBe(visible, Duration.ofSeconds(10)).click();
        outcomeTextMail().filter(text(TestData.getTopicMe())).shouldBe(sizeGreaterThanOrEqual(1));
        checkTopicOutMail().shouldHave(text(TestData.getTopicMe())).click();
        checkContentOutMail().shouldHave(text(TestData.getContentMe()));
        incomeMail().shouldBe(visible, Duration.ofSeconds(10)).click();
        checkTopicIncomeMail().shouldHave(text(TestData.getTopicMe())).click();
        checkContentIncomeMail().shouldHave(text(TestData.getContentMe()));
    }

    @AfterAll
    static void close(){
        WebDriverRunner.closeWebDriver();
    }


}
