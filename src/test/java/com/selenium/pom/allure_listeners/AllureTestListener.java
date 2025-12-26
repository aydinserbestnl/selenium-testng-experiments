package com.selenium.pom.allure_listeners;

import com.selenium.pom.utils.ConfigLoader;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;

public class AllureTestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            WebDriver driver = extractDriver(result.getInstance());
            if (driver == null) return;

            String browser = resolveBrowser(result);

            String className = result.getTestClass().getRealClass().getSimpleName();
            String methodName = result.getMethod().getMethodName();

            String attachmentName =
                    className + "#" + methodName + " [" + browser.toUpperCase() + "]";

            byte[] bytes =
                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(
                    attachmentName,
                    "image/png",
                    new ByteArrayInputStream(bytes),
                    ".png"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String resolveBrowser(ITestResult result) {
        // 1️⃣ TestNG xml <parameter>
        try {
            String fromXml =
                    result.getTestContext().getCurrentXmlTest().getParameter("browser");
            if (fromXml != null && !fromXml.isBlank()) return fromXml;
        } catch (Exception ignored) {}

        // 2️⃣ CLI -Dbrowser
        String fromCli = System.getProperty("browser");
        if (fromCli != null && !fromCli.isBlank()) return fromCli;

        // 3️⃣ config.properties
        return ConfigLoader.getInstance().getBrowser();
    }

    private WebDriver extractDriver(Object testInstance) {
        try {
            Class<?> c = testInstance.getClass();
            while (c != null) {
                try {
                    Method m = c.getDeclaredMethod("getDriver");
                    m.setAccessible(true);
                    return (WebDriver) m.invoke(testInstance);
                } catch (NoSuchMethodException ignored) {
                    c = c.getSuperclass();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

