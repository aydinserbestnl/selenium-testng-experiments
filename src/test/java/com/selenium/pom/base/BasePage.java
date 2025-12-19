package com.selenium.pom.base;

import com.selenium.pom.utils.ConfigLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private final String baseUrl = ConfigLoader.getInstance().getBaseUrl();

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                java.time.Duration.ofSeconds(ConfigLoader.getInstance().getExplicitWaitSeconds()));
    }

    public void load(String endpoint) {
        String normalized = endpoint.startsWith("/") ? endpoint.substring(1) : endpoint;
        String separator = baseUrl.endsWith("/") ? "" : "/";
        driver.get(baseUrl + separator + normalized);
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected boolean waitInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void clickWhenClickable(By locator) {
        waitClickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement el = waitVisible(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitVisible(locator).getText();
    }

    protected boolean isVisible(By locator) {
        try {
            waitVisible(locator);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean waitForUrlContains(String fragment) {
        return wait.until(ExpectedConditions.urlContains(fragment));
    }

    protected boolean waitTextToBe(By locator, String text) {
        return wait.until(ExpectedConditions.textToBe(locator, text));
    }

    protected boolean waitTextContains(By locator, String fragment) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, fragment));
    }
}
