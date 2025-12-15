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
        this.wait = new WebDriverWait(driver,
                Duration.ofSeconds(ConfigLoader.getInstance().getExplicitWaitSeconds()));
    }

    public void load(String endpoint) {
        String sep = endpoint.startsWith("/") ? "" : "/";
        driver.get(baseUrl.endsWith("/") ? baseUrl + endpoint.replaceFirst("^/", "")
                : baseUrl + sep + endpoint);
    }
}
/*
we are assigning the parameter driver to private WebDriver driver;
 */
