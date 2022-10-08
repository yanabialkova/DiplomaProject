package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PurchasePage {
    private SelenideElement heading = $$(".heading").find(Condition.exactText("Купить в кредит"));
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthInputField = $("[placeholder='08']");
    private SelenideElement yearInputField = $("[placeholder='22']");
    private SelenideElement ownerField = $$(".input__top").find(Condition.exactText("Владелец"));
    private SelenideElement cvcInputField = $("[placeholder='999']");
    private SelenideElement continueButton = $$("button").find(Condition.exactText("Продолжить"));
    private SelenideElement successNotification = $(withText("Операция одобрена Банком."));
    private SelenideElement errorNotification = $(withText("Ошибка! Банк отказал в проведении операции."));

    public void putCardData(String cardNumber, String month, String year, String owner, String cvc) {
        cardNumberField.setValue(cardNumber);
        monthInputField.setValue(month);
        yearInputField.setValue(year);
        ownerField.setValue(owner);
        cvcInputField.setValue(cvc);
        continueButton.click();
    }

}
