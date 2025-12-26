package com.selenium.pom.components;

import com.selenium.pom.base.BasePage;
import com.selenium.pom.pages.CartPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ProductThumbnail extends BasePage {
    private static final String LEFT_QUOTE = "\u201C";
    private static final String RIGHT_QUOTE = "\u201D";
    private final By viewCartLink = By.cssSelector("a[title='View cart']");

    public ProductThumbnail(WebDriver driver) {
        super(driver);
    }

    private By addToCartElement(String productName) {
        return By.cssSelector("a[aria-label='Add " + LEFT_QUOTE + productName + RIGHT_QUOTE + " to your cart']");
    }
    @Step
    public ProductThumbnail clickAddToCartFor(String productName) {
        clickWhenClickable(addToCartElement(productName));
        return this;
    }
    @Step
    public CartPage clickViewCart() {
        clickWhenClickable(viewCartLink);
        return new CartPage(driver);
    }
}
