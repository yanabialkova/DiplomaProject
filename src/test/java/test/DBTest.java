package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import data.APIHelper;
import data.DataHelper;
import data.SQLHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldSuccessTransactionWithApprovedPaymentCardThroughAPI() {
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        APIHelper.createPayment(cardInfo);
        var paymentCardData = SQLHelper.getPaymentCardData();
        assertEquals("APPROVED", paymentCardData.getStatus());
    }

    @Test
    void shouldSuccessTransactionWithApprovedCreditCardThroughAPI() {
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        APIHelper.createCredit(cardInfo);
        var creditCardData = SQLHelper.getCreditCardData();
        assertEquals("APPROVED", creditCardData.getStatus());
    }
}
