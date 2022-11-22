package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import data.DataHelper;
import page.MainPage;


import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;

public class CreditCardTest {
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
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclinedCard();
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkApprovedMessFromBank();
    }

    @Test
    void shouldSuccessTransactionWithMaxAllowedDate() {
        var toCreditCard = mainPage.creditPage();
        var currentMonth = DataHelper.getCurrentMonth();
        var maxYear = Integer.parseInt(DataHelper.getCurrentYear()) + 5;
        var cardInfo = DataHelper.generateDataWithDeclineCardAndParametrizedMonthAndYear(currentMonth,
                String.valueOf(maxYear));
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkApprovedMessFromBank();
    }

    //card

    @Test
    void shouldDeclineWithRandomPaymentCard() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclinedCard();
        cardInfo.setNumber("4444 4444 4444 3456");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkErrorMessDeclineFromBank();
    }

    @Test
    void shouldShowErrorIfAllCardNumberFieldAreZero() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclinedCard();
        cardInfo.setNumber("0000 0000 0000 0000");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkErrorMessDeclineFromBank();
    }

    @Test
    void shouldShowErrorIfAllCardNumberFieldAtEmpty() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclinedCard();
        cardInfo.setNumber("");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCardNumberField("Неверный формат");
    }

    @Test
    void shouldShowErrorIfAllCardNumberFieldAreSpecialCharacters() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclinedCard();
        cardInfo.setNumber("#$%% ^%$$ &&%% &%*&");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCardNumberField("Неверный формат");
    }

    //month
    @Test
    void shouldDeclineWithFalseMonth() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclineCardAndParametrizedMonthAndYear("90","22");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderMonthField("Неверно указан срок действия карты");
    }

    @Test
    void shouldShowErrorIfAllMonthNumberFieldAreZero() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclinedCard();
        cardInfo.setMonth("00");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderMonthField("Неверно указан срок действия карты");

    }

    @Test
    void shouldShowErrorIfAllMonthNumberFieldAtEmpty() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclinedCard();
        cardInfo.setMonth("");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderMonthField("Неверный формат");
    }

    @Test
    void shouldShowErrorIfAllMonthNumberFieldAreSpecialCharacters() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclinedCard();
        cardInfo.setMonth("#$");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderMonthField("Неверный формат");
    }

    //year
    @Test
    void shouldDeclineWithFalseYear() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclineCardAndParametrizedMonthAndYear("10","88");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderYearField("Неверно указан срок действия карты");
    }

    @Test
    void shouldShowErrorIfAllYearNumberFieldAreZero() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclinedCard();
        cardInfo.setYear("00");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderYearField ("Истёк срок действия карты");
    }

    @Test
    void shouldShowErrorIfAllYearNumberFieldAtEmpty() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclinedCard();
        cardInfo.setYear("");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderYearField("Неверный формат");
    }

    @Test
    void shouldShowErrorIfAllYearNumberFieldAreSpecialCharacters() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclinedCard();
        cardInfo.setYear("#$");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderYearField("Неверный формат");
    }

    //name
    @Test
    void shouldDeclineWithFalseName() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerNameDeclineCard("ИВАНОВ ИВАНОВ");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkErrorMessDeclineFromBank();
    }

    @Test
    void shouldShowErrorIfAllNameFieldAreZero() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerNameDeclineCard("0000 00000");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkErrorMessDeclineFromBank();
    }

    @Test
    void shouldShowErrorIfAllNameFieldAtEmpty() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerNameDeclineCard("");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCardOwnerField("Поле обязательно для заполнения");
    }

    @Test
    void shouldShowErrorIfAllNameFieldAreSpecialCharacters() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerNameDeclineCard("%::.;;(");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkErrorMessDeclineFromBank();
    }

    //cvc
    @Test
    void shouldDeclineWithFalseCvc() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setCvc("23");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCvcField("Неверный формат");
    }

    @Test
    void shouldShowErrorIfAllCvcNumberFieldAreZero() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setCvc("000");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkErrorMessDeclineFromBank();
    }

    @Test
    void shouldShowErrorIfAllCvcNumberFieldAtEmpty() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setCvc("");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCvcField("Неверный формат");
    }

    @Test
    void shouldShowErrorIfAllCvcNumberFieldAreSpecialCharacters() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setCvc("#$!");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCvcField("Неверный формат");
    }
}
