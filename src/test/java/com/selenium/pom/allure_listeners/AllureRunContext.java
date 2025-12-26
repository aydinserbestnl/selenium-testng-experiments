package com.selenium.pom.allure_listeners;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class AllureRunContext {
    private static final Set<String> BROWSERS = new ConcurrentSkipListSet<>();

    private AllureRunContext() {}

    public static void recordBrowser(String browser) {
        if (browser == null) return;
        String b = browser.trim();
        if (!b.isEmpty()) BROWSERS.add(b.toUpperCase());
    }

    public static String getBrowsersCsvOrUnknown() {
        if (BROWSERS.isEmpty()) return "UNKNOWN";
        return String.join(", ", BROWSERS);
    }

    public static void clear() {
        BROWSERS.clear();
    }
}
