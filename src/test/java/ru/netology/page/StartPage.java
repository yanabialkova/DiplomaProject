package ru.netology.page;


import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;

public class StartPage {

    private SelenideElement purchase = $x("//*[text()='Купить']");
    private SelenideElement purchaseOnCredit = $x("//*[text()='Купить в кредит']");

    public PurchasePage getTour(){
        purchase.click();
        return new PurchasePage();
    }

    public void getTourOnCredit(){
        purchaseOnCredit.click();
    }
}
