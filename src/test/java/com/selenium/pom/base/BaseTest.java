package com.selenium.pom.base;

import com.selenium.pom.factory.DriverManager;
import com.selenium.pom.utils.ConfigLoader;
import com.selenium.pom.utils.CookieUtils;
import io.restassured.http.Cookies;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.SimpleShootingStrategy;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
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
    @Parameters("browser")
    @AfterMethod()
    public void quitDriver(@Optional String browser, ITestResult result) throws IOException {
        //System.out.println("current thread: " + Thread.currentThread().threadId() +
        //        ", DRIVER = " + getDriver());
        String browserName = System.getProperty("browser", browser);
        if (browserName == null || browserName.isBlank()) {
            browserName = ConfigLoader.getInstance().getBrowser(); // chrome
        }

        if (result.getStatus() == ITestResult.FAILURE) {
            File destFile = new File("scr" + File.separator + browserName + File.separator +
                    result.getTestClass().getRealClass().getSimpleName() + "_" +
                    result.getMethod().getMethodName() + ".png");
            destFile.getParentFile().mkdirs();
            takeScreenshot(destFile);
            //takeFullPageScreenshotUsingAShot(destFile);
        }
        WebDriver d = getDriver();
        if (d != null) { d.quit(); driver.remove(); }
    }
    private void takeScreenshot(File destFile) throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
        File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, destFile);
    }
    private void takeFullPageScreenshotUsingAShot(File destFile)  {
        Screenshot screenshot = new AShot()
                .shootingStrategy(
                        ShootingStrategies.viewportPasting(
                                ShootingStrategies.scaling(1.0f), 200
                        )
                )
                .takeScreenshot(getDriver());

        try {
            ImageIO.write(screenshot.getImage(), "PNG", destFile);
        } catch (IOException e) {
            e.printStackTrace();
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

