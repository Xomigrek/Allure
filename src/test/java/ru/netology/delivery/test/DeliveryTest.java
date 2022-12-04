package ru.netology.delivery.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {
    public DataGenerator data = new DataGenerator();

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        Configuration.holdBrowserOpen = true;

        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        String planningFirstDate = data.generateDate(daysToAddForFirstMeeting);
        String planningSecondDate = data.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id=\"city\"] .input__control").setValue(data.generateCity("ru"));
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL + "A");
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(planningFirstDate);
        $("[data-test-id=\"name\"] .input__control").setValue(data.generateName("ru"));
        $x("//input[@name=\"phone\"]").setValue(data.generatePhone ("ru"));
        $(".checkbox").click();
        $("button.button").click();
        $("[data-test-id='success-notification'] .notification__content").should(text("Встреча успешно запланирована на " + planningFirstDate));
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL + "A");
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(planningSecondDate);
        $("button.button").click();
        $("[data-test-id=\"replan-notification\"] .button").click();
        $("[data-test-id='success-notification'] .notification__content").should(text("Встреча успешно запланирована на " + planningSecondDate));
    }
}
