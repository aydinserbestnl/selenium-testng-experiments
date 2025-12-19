package com.selenium.pom.utils;

import java.util.Properties;

public class ConfigLoader {
    private static final ConfigLoader INSTANCE = new ConfigLoader();
    private final Properties properties;

    private ConfigLoader() {
        this.properties = PropertyUtils.propertyLoader("config.properties");
    }

    public static ConfigLoader getInstance() {
        return INSTANCE;
    }

    private String getRequiredProperty(String key) {
        String prop = properties.getProperty(key);
        if (prop != null && !prop.trim().isEmpty()) {
            return prop;
        }
        throw new RuntimeException(key + " not set in config file");
    }

    public String getBaseUrl() {
        return getRequiredProperty("baseUrl");
    }

    public String getUsername() {
        return getRequiredProperty("username");
    }

    public String getPassword() {
        return getRequiredProperty("password");
    }

    public String getBrowser() {
        return getRequiredProperty("browser");
    }

    public int getImplicitWaitSeconds() {
        return Integer.parseInt(getRequiredProperty("implicitWaitSeconds"));
    }

    public int getExplicitWaitSeconds() {
        return Integer.parseInt(getRequiredProperty("explicitWaitSeconds"));
    }
}
