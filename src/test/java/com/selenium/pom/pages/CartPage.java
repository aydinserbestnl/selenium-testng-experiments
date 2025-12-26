package com.selenium.pom.pages;

import com.selenium.pom.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {
    private final By productName = By.cssSelector("td.product-name");
    private final By checkoutButton = By.cssSelector("a.checkout-button");

    public CartPage(WebDriver driver) { super(driver); }
    @Step
    public String getProductName() {
        return waitVisible(productName).getText();
    }

    public CheckoutPage clickCheckoutButton() {
        clickWhenClickable(checkoutButton);
        return new CheckoutPage(driver);
    }
}
