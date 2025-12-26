package com.selenium.pom.allure_listeners;

import com.selenium.pom.utils.ConfigLoader;
import org.testng.IExecutionListener;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class AllureEnvironmentListener implements IExecutionListener {
    @Override
    public void onExecutionStart() {
        try {
            // ✅ Maven surefire set ederse target/allure-results olur.
            // ✅ IntelliJ run’da set edilmezse aşağıdaki default devreye girer.
            String resultsDir = System.getProperty("allure.results.directory", "target/allure-results");

            Path dir = Paths.get(resultsDir);
            Files.createDirectories(dir);

            String project = System.getProperty("allure.project", "selenium-testng-experiments");

            // ✅ Browser önceliği:
            // 1) -Dbrowser
            // 2) (TestNG param burada direkt okunamaz; o yüzden param yoksa config'e düşer)
            // 3) config.properties
            String browser = System.getProperty("browser");
            if (browser == null || browser.isBlank()) {
                browser = ConfigLoader.getInstance().getBrowser();
            }

            Properties p = new Properties();
            p.setProperty("Project", project);
            p.setProperty("Runner", "TestNG");
            p.setProperty("Java", System.getProperty("java.version"));
            p.setProperty("Browser", browser);

            try (OutputStream os = Files.newOutputStream(dir.resolve("environment.properties"))) {
                p.store(os, null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
