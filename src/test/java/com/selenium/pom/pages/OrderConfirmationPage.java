package com.selenium.pom.pages;

import com.selenium.pom.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderConfirmationPage extends BasePage {
    private final By successNotice = By.cssSelector(".woocommerce-notice--success");

    public OrderConfirmationPage(WebDriver driver) { super(driver); }

    public String getSuccessMessage() {
        return waitVisible(successNotice).getText();
    }
}

