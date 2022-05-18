package uiTest;

import utils.DBConnect;
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

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverRunner.addListener;
import static utils.HighlighterNew.highlight;


@DisplayName("UI тесты для проверки работы Яндекс почты")
public class YandexMailTest {
    private static MailPage mailPage = new MailPage();
    private static TestData testData = new TestData();
    private static DBConnect dbConnect = new DBConnect();
    private Letters letter = dbConnect.getDataLetter();

    @BeforeAll
    @DisplayName("Стартовые настройки браузера и вход в почту")
    static void setUp() {
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
    void visibleLoginAndImage() {
        highlight(element(mailPage.userIMG().shouldBe(visible, Duration.ofSeconds(10))));
        highlight(element(mailPage.loginTextIMG().shouldHave(text(testData.getLoginMail()))));
    }

    @Test
    @DisplayName("Проверка видимости писем")
    void visibleElementLetters() {
        mailPage.incomeMail().shouldBe(visible, Duration.ofSeconds(10)).click();
        mailPage.subjectMailTextVisible().shouldHave(sizeGreaterThanOrEqual(1));
    }

    @Test
    @DisplayName("Отправка письма другому человеку")
    void sendLetter() {
        String topicDate = letter.getSubject() + " " + testData.dateTime();
        Letters letterNew = new Letters(topicDate, letter.getContext(), letter.getReciver());
        mailPage.writeLetter(letterNew);
        mailPage.subjectMailTextVisible().filter(text(topicDate)).shouldBe(sizeGreaterThan(0));
        mailPage.checkTopicMail(topicDate);
        mailPage.checkContentMail(letterNew.getContext());
    }

    @Test
    @DisplayName("Отправка письма себе")
    void sendLetterMyself() {
        String topicDate = letter.getSubject() + " " + testData.dateTime();
        Letters letterNew = new Letters(topicDate, letter.getContext(), testData.getLoginMail() + "@yandex.ru");
        mailPage.writeLetter(letterNew);
        mailPage.subjectMailTextVisible().filter(text(topicDate)).shouldBe(sizeGreaterThan(0));
        mailPage.checkTopicMail(topicDate);
        mailPage.checkContentMail(letterNew.getContext());
        mailPage.newMail().click();
        mailPage.incomeMail().shouldBe(visible, Duration.ofSeconds(10)).click();
        mailPage.checkTopicMail(topicDate);
        mailPage.checkContentMail(letterNew.getContext());
    }

    @AfterAll
    static void close() {
        WebDriverRunner.closeWebDriver();
    }
}
