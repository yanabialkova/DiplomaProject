package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import data.DataHelper;
import page.MainPage;


import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;

public class PaymentTest {
    MainPage mainPage = open("http://localhost:8080/", MainPage.class);

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUP() {
        Configuration.holdBrowserOpen = true;
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
    @AfterEach
    void tearDown() {
        closeWindow();
    }

    @Test
    void shouldSuccessTransactionWithPaymentCard() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    @Test
    void shouldSuccessTransactionWithMaxAllowedDate() {
        var toPaymentPage = mainPage.paymentPage();
        var currentMonth = DataHelper.getCurrentMonth();
        var maxYear = Integer.parseInt(DataHelper.getCurrentYear()) + 5;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear(currentMonth,
                String.valueOf(maxYear));
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    //card

    @Test
    void shouldDeclineWithRandomPaymentCard() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithRandomCardNumber();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkErrorMessDeclineFromBank();
    }

    @Test
    void shouldShowErrorIfAllCardNumberFieldAreZero() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setNumber("0000 0000 0000 0000");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkErrorMessDeclineFromBank();
    }

    @Test
    void shouldShowErrorIfAllCardNumberFieldAtEmpty() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setNumber("");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardNumberField("Неверный формат");
    }

    @Test
    void shouldShowErrorIfAllCardNumberFieldAreSpecialCharacters() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setNumber("#$%% ^%$$ &&%% &%*&");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardNumberField("Неверный формат");
    }

    //month
    @Test
    void shouldDeclineWithFalseMonth() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear("90","22");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderMonthField("Неверно указан срок действия карты");
    }

    @Test
    void shouldShowErrorIfAllMonthNumberFieldAreZero() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setMonth("00");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkErrorMessDeclineFromBank();
    }

    @Test
    void shouldShowErrorIfAllMonthNumberFieldAtEmpty() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setMonth("");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderMonthField("Неверный формат");
    }

    @Test
    void shouldShowErrorIfAllMonthNumberFieldAreSpecialCharacters() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setMonth("#$");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderMonthField("Неверный формат");
    }

    //year

    @Test
    void shouldDeclineWithFalseYear() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear("10","88");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderYearField("Неверно указан срок действия карты");
    }

    @Test
    void shouldShowErrorIfAllYearNumberFieldAreZero() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setYear("00");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderYearField ("Истёк срок действия карты");
    }

    @Test
    void shouldShowErrorIfAllYearNumberFieldAtEmpty() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setYear("");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderYearField("Неверный формат");
    }

    @Test
    void shouldShowErrorIfAllYearNumberFieldAreSpecialCharacters() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setYear("#$");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderYearField("Неверный формат");
    }

    //name

    @Test
    void shouldDeclineWithFalseName() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerNameApprovedCard("ИВАНОВ ИВАНОВ");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkErrorMessDeclineFromBank();
    }

    @Test
    void shouldShowErrorIfAllNameFieldAreZero() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerNameApprovedCard("0000 00000");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkErrorMessDeclineFromBank();
    }

    @Test
    void shouldShowErrorIfAllNameFieldAtEmpty() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerNameApprovedCard("");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardOwnerField("Поле обязательно для заполнения");
    }

    @Test
    void shouldShowErrorIfAllNameFieldAreSpecialCharacters() {
            var toPaymentPage = mainPage.paymentPage();
            var cardInfo = DataHelper.generateDataWithParamCardOwnerNameApprovedCard("%::.;;(");
            toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
            toPaymentPage.checkErrorMessDeclineFromBank();
    }

    //cvc

    @Test
    void shouldDeclineWithFalseCvc() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setCvc("23");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCvcField("Неверный формат");
    }

    @Test
    void shouldShowErrorIfAllCvcNumberFieldAreZero() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setCvc("000");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkErrorMessDeclineFromBank();
    }

    @Test
    void shouldShowErrorIfAllCvcNumberFieldAtEmpty() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setCvc("");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCvcField("Неверный формат");
    }

    @Test
    void shouldShowErrorIfAllCvcNumberFieldAreSpecialCharacters() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setCvc("#$!");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCvcField("Неверный формат");
    }
}

















