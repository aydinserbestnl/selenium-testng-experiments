package com.selenium.pom.base;

import com.selenium.pom.factory.DriverManager;
import com.selenium.pom.utils.ConfigLoader;
import com.selenium.pom.utils.CookieUtils;
import io.restassured.http.Cookies;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.List;

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
        driver.set(new DriverManager().initializeDriver(selected));
       // System.out.println("current thread: " + Thread.currentThread().threadId() +
         //       ", DRIVER = " + getDriver());
    }

    @AfterMethod(alwaysRun = true)
    public void quitDriver() {
        //System.out.println("current thread: " + Thread.currentThread().threadId() +
        //        ", DRIVER = " + getDriver());
        WebDriver d = getDriver();
        if (d != null) {
            d.quit();
            driver.remove();
        }
    }
    public void injectCookiesToBrowser(Cookies cookies){
        List<Cookie> seleniumCookies = new CookieUtils().convertRestAssuredCookiesToSeleniumCookies(cookies);
        for(Cookie cookie: seleniumCookies){
            System.out.println(cookie.toString());
            getDriver().manage().addCookie(cookie);
        }
    }
}

