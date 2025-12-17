package com.selenium.pom.pages;

import com.selenium.pom.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final By storeMenuLink = By.linkText("Store");

    public HomePage(WebDriver driver) { super(driver); }

    public HomePage load() {
        load("/");
        return this;
    }

    public StorePage navigateToStoreUsingMenu() {
        clickWhenClickable(storeMenuLink);
        return new StorePage(driver);
    }
}

