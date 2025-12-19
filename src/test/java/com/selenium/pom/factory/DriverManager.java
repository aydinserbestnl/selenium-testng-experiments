package com.selenium.pom.factory;

import com.selenium.pom.constants.BrowserType;
import com.selenium.pom.utils.ConfigLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;



public class DriverManager {
    public WebDriver initializeDriver() {
        return initializeDriver(ConfigLoader.getInstance().getBrowser());
    }

    public WebDriver initializeDriver(String browser) {
        WebDriver driver = switch (BrowserType.valueOf(browser.toUpperCase())) {
            case CHROME -> new ChromeDriver();
            case FIREFOX -> new FirefoxDriver();
        };

        driver.manage().window().maximize();
        int implicit = ConfigLoader.getInstance().getImplicitWaitSeconds();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicit));
        return driver;
    }
}
