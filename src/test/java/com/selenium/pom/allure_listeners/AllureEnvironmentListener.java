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
        // Run başında bir önceki run'ın bilgisini temizle
        AllureRunContext.clear();
    }

    @Override
    public void onExecutionFinish() {
        try {
            String resultsDir = System.getProperty("allure.results.directory", "target/allure-results");
            Path dir = Paths.get(resultsDir);
            Files.createDirectories(dir);

            String project = System.getProperty("allure.project", "selenium-testng-experiments");

            // Headless (öncelik: -Dheadless -> ENV HEADLESS -> default false)
            String headless = System.getProperty("headless");
            if (headless == null || headless.isBlank()) {
                headless = System.getenv("HEADLESS");
            }
            if (headless == null || headless.isBlank()) {
                headless = "false";
            }

            Properties p = new Properties();
            p.setProperty("Project", project);
            p.setProperty("Runner", "TestNG");
            p.setProperty("Java", System.getProperty("java.version"));

            // ✅ Tek browser yerine: bu run’da kullanılan browser’ların özeti
            p.setProperty("Browsers", AllureRunContext.getBrowsersCsvOrUnknown());

            // ✅ Headless bilgisi
            p.setProperty("Headless", headless.toUpperCase());

            try (OutputStream os = Files.newOutputStream(dir.resolve("environment.properties"))) {
                p.store(os, null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }}
