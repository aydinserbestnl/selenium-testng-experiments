package com.selenium.pom.factory;

import com.selenium.pom.constants.BrowserType;
import com.selenium.pom.utils.ConfigLoader;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;



public class DriverManager {

    public WebDriver initializeDriver() {
        return initializeDriver(ConfigLoader.getInstance().getBrowser());
    }

    public WebDriver initializeDriver(String browser) {

        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );

        WebDriver driver = switch (BrowserType.valueOf(browser.toUpperCase())) {

            case CHROME -> {
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless=new");
                    options.addArguments("--window-size=1920,1080");
                }
                yield new ChromeDriver(options);
            }

            case FIREFOX -> {
                FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("-headless");
                }
                yield new FirefoxDriver(options);
            }
        };

        if (!headless) {
            driver.manage().window().maximize();
        }

        int implicit = ConfigLoader.getInstance().getImplicitWaitSeconds();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicit));

        return driver;
    }
}