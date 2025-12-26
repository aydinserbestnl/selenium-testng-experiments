package com.selenium.pom.base;


import com.selenium.pom.allure_listeners.AllureRunContext;
import com.selenium.pom.factory.DriverManager;
import com.selenium.pom.utils.ConfigLoader;
import com.selenium.pom.utils.CookieUtils;
import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import org.openqa.selenium.*;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.List;


@Listeners({
        io.qameta.allure.testng.AllureTestNg.class,
        com.selenium.pom.allure_listeners.AllureTestListener.class,
        com.selenium.pom.allure_listeners.AllureEnvironmentListener.class
})
public class BaseTest {

    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    protected WebDriver getDriver() { return driver.get(); }

    @Parameters("browser")
    @BeforeMethod
    public void startDriver(@Optional String browser) {
        String selected = System.getProperty("browser", browser);
        if (selected == null || selected.isBlank()) {
            selected = ConfigLoader.getInstance().getBrowser();
        }
        AllureRunContext.recordBrowser(selected);

        driver.set(new DriverManager().initializeDriver(selected));
    }

    @AfterMethod(alwaysRun = true)
    public void quitDriver(ITestResult result) throws IOException {
        WebDriver d = getDriver();
        if (d != null) {
            d.quit();
            driver.remove();
        }
    }
    @Step
    public void injectCookiesToBrowser(Cookies cookies){
        List<Cookie> seleniumCookies = new CookieUtils().convertRestAssuredCookiesToSeleniumCookies(cookies);
        for(Cookie cookie: seleniumCookies){
            getDriver().manage().addCookie(cookie);
        }
    }
}