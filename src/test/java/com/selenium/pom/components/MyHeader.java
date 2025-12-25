package com.selenium.pom.components;

import com.selenium.pom.base.BasePage;
import com.selenium.pom.pages.StorePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyHeader extends BasePage {
    private final By storeMenuLink = By.linkText("Store");
    public MyHeader(WebDriver driver) {
        super(driver);
    }
    public StorePage navigateToStoreUsingMenu() {
        clickWhenClickable(storeMenuLink);
        return new StorePage(driver);
    }

}
