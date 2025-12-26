package com.selenium.pom.base;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class AllureTestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            Object testInstance = result.getInstance();
            WebDriver driver = extractDriver(testInstance);
            if (driver == null) return;

            byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            String className = result.getTestClass().getRealClass().getSimpleName();
            String methodName = result.getMethod().getMethodName();

            // BaseTest'te set ettiğimiz browser
            String browser = System.getProperty("browser", "UNKNOWN");

            String attachmentName =
                    className + "#" + methodName + " [" + browser.toUpperCase() + "]";

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

