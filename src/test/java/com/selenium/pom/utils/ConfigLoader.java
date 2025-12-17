package com.selenium.pom.utils;

import com.selenium.pom.constants.EnvType;

import java.util.Properties;

public class ConfigLoader {
    private static final ConfigLoader INSTANCE = new ConfigLoader();
    private final Properties properties;

    private ConfigLoader() {
        this.properties = PropertyUtils.propertyLoader("config.properties");
    }

    public static ConfigLoader getInstance() { return INSTANCE; }

    public String getBaseUrl() {
        String prop = properties.getProperty("baseUrl");
        if (prop != null) return prop;
        throw new RuntimeException("baseUrl not set in config file");
    }

    public String getUsername() {
        String prop = properties.getProperty("username");
        if (prop != null) return prop;
        throw new RuntimeException("username not set in config file");
    }

    public String getPassword() {
        String prop = properties.getProperty("password");
        if (prop != null) return prop;
        throw new RuntimeException("password not set in config file");
    }

    public String getBrowser() {
        String prop = properties.getProperty("browser");
        if (prop != null) return prop;
        throw new RuntimeException("browser not set in config file");
    }

    public int getImplicitWaitSeconds() {
        String prop = properties.getProperty("implicitWaitSeconds");
        if (prop != null) return Integer.parseInt(prop);
        throw new RuntimeException("implicitWaitSeconds not set in config file");
    }

    public int getExplicitWaitSeconds() {
        String prop = properties.getProperty("explicitWaitSeconds");
        if (prop != null) return Integer.parseInt(prop);
        throw new RuntimeException("explicitWaitSeconds not set in config file");
    }
}
