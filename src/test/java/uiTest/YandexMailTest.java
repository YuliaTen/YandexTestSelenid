package uiTest;

import utils.HighlighterNew;
import utils.Letters;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import utils.TestData;
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
import static utils.HighlighterNew.highlight;


@DisplayName("UI тесты для проверки работы Яндекс почты")
public class YandexMailTest  {
    private static MailPage mailPage = new MailPage();
    private static TestData testData = new TestData();

    @BeforeAll
    @DisplayName("Стартовые настройки браузера и вход в почту")
    static void setUp(){
        Configuration.browser = "chrome";
        addListener(new HighlighterNew());
        open(testData.getStartURL());
        WebDriverRunner.getWebDriver().manage().window().maximize();
        mailPage.income().click();
        mailPage.loginMail().setValue(testData.getLoginMail()).pressEnter();
        mailPage.passMail().setValue(testData.getPasswordMail()).pressEnter();
    }

    @Test
    @DisplayName("Проверка отображения иконки пользователя и логина")
    void visibleLoginAndImage(){
    highlight(element(mailPage.userIMG().shouldBe(visible, Duration.ofSeconds(10))));
    highlight(element(mailPage.loginTextIMG().shouldHave(text(testData.getLoginMail()))));
}

    @Test
    @DisplayName("Проверка видимости писем")
    void visibleElementLetters(){
        mailPage.incomeMail().shouldBe(visible, Duration.ofSeconds(10)).click();
        mailPage.subjectMailTextVisible().shouldHave(sizeGreaterThanOrEqual(1));
    }

    @Test
    @DisplayName("Отправка письма другому человеку")
    void sendLetter(){
        String topicDate = testData.getLetter().getSubject() + " " + testData.dateTime();
        Letters letter = new Letters(topicDate,testData.getLetter().getContext(),testData.getLetter().getReciver());
        mailPage.writeLetter(letter);
        mailPage.subjectMailTextVisible().filter(text(topicDate)).shouldBe(sizeGreaterThanOrEqual(1));
        mailPage.checkTopicMail(topicDate);
        mailPage.checkContentMail(testData.getLetter().getContext());
    }

    @Test
    @DisplayName("Отправка письма себе")
    void sendLetterMyself(){
        String topicDate = testData.getTopicMe()+" " + testData.dateTime();
        Letters letter = new Letters(topicDate,testData.getContentMe(),testData.getMyEmail());
        mailPage.writeLetter(letter);
        mailPage.subjectMailTextVisible().filter(text(topicDate)).shouldBe(sizeGreaterThanOrEqual(1));
        mailPage.checkTopicMail(topicDate);
        mailPage.checkContentMail(testData.getContentMe());
        mailPage.newMail().click();
        mailPage.incomeMail().shouldBe(visible, Duration.ofSeconds(10)).click();
        mailPage.checkTopicMail(topicDate);
        mailPage.checkContentMail(testData.getContentMe());
    }

    @AfterAll
    static void close(){
        WebDriverRunner.closeWebDriver();
    }
}