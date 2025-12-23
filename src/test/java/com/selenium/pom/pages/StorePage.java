package com.selenium.pom.pages;

import com.selenium.pom.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StorePage extends BasePage {
    private static final String LEFT_QUOTE = "\u201C";
    private static final String RIGHT_QUOTE = "\u201D";

    private final By searchField = By.name("s");
    private final By searchButton = By.cssSelector("button[type='submit'][value='Search']");
    private final By resultsHeading = By.cssSelector(".woocommerce-products-header__title.page-title");
    private final By viewCartLink = By.cssSelector("a[title='View cart']");

    public StorePage(WebDriver driver) { super(driver); }
    public StorePage load() {
        load("/store");
        return this;
    }

    public StorePage search(String term) {
        WebElement input = waitVisible(searchField);
        input.clear();
        input.sendKeys(term);
        clickWhenClickable(searchButton);
        waitTextContains(resultsHeading, "Search results");
        return this;
    }

    public StorePage waitForResultsHeading(String term) {
        waitTextToBe(resultsHeading, buildResultsHeading(term));
        return this;
    }


    public String buildResultsHeading(String term) {
        return "Search results: " + LEFT_QUOTE + term + RIGHT_QUOTE;
    }

    public String getResultsHeadingText() {
        return waitVisible(resultsHeading).getText();
    }

    private By addToCartElement(String productName) {
        return By.cssSelector("a[aria-label='Add " + LEFT_QUOTE + productName + RIGHT_QUOTE + " to your cart']");
    }

    public StorePage clickAddToCartFor(String productName) {
        clickWhenClickable(addToCartElement(productName));
        return this;
    }

    public CartPage clickViewCart() {
        clickWhenClickable(viewCartLink);
        return new CartPage(driver);
    }
}
