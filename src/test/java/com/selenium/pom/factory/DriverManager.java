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

        boolean headless = resolveHeadless();

        WebDriver driver = switch (BrowserType.valueOf(browser.toUpperCase())) {
            case CHROME -> {
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    // Chrome 109+ için headless=new daha stabil
                    options.addArguments("--headless=new");
                    options.addArguments("--window-size=1920,1080");
                }
                yield new ChromeDriver(options);
            }
            case FIREFOX -> {
                FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("-headless");
                    options.addArguments("--width=1920");
                    options.addArguments("--height=1080");
                }
                yield new FirefoxDriver(options);
            }
        };

        // Headless iken maximize bazen hata veriyor → sadece headed iken maximize
        if (!headless) {
            driver.manage().window().maximize();
        }

        int implicit = ConfigLoader.getInstance().getImplicitWaitSeconds();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicit));
        return driver;
    }

    private boolean resolveHeadless() {
        // Öncelik: -Dheadless=...
        String sys = System.getProperty("headless");
        if (sys != null && !sys.isBlank()) {
            return parseTruth(sys);
        }

        // İstersen Jenkins env var ile de çalışsın diye (opsiyonel)
        String env = System.getenv("HEADLESS");
        if (env != null && !env.isBlank()) {
            return parseTruth(env);
        }

        // Default: false (istersen true yapabiliriz)
        return false;
    }

    private boolean parseTruth(String value) {
        String v = value.trim().toLowerCase();
        return v.equals("true") || v.equals("1") || v.equals("yes") || v.equals("y") || v.equals("on");
    }
}