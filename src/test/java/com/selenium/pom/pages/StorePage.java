package com.selenium.pom.pages;

import com.selenium.pom.base.BasePage;
import com.selenium.pom.components.ProductThumbnail;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StorePage extends BasePage {
    public ProductThumbnail getProductThumbnail() {
        return productThumbnail;
    }

    private ProductThumbnail productThumbnail;
    private static final String LEFT_QUOTE = "\u201C";
    private static final String RIGHT_QUOTE = "\u201D";

    private final By searchField = By.name("s");
    private final By searchButton = By.cssSelector("button[type='submit'][value='Search']");
    private final By resultsHeading = By.cssSelector(".woocommerce-products-header__title.page-title");

    public StorePage(WebDriver driver) {
        super(driver);
    productThumbnail = new ProductThumbnail(driver);}
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
}
