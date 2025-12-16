package com.selenium.pom.base;

import com.selenium.pom.utils.ConfigLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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
        String normalizedEndpoint = endpoint.startsWith("/") ? endpoint.substring(1) : endpoint;
        String separator = baseUrl.endsWith("/") ? "" : "/";
        driver.get(baseUrl + separator + normalizedEndpoint);
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
}
/*
we are assigning the parameter driver to private WebDriver driver;
 */
