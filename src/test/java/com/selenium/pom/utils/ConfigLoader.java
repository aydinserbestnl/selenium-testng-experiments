package com.selenium.pom.utils;

import com.selenium.pom.constants.EnvType;

import java.util.Properties;

public class ConfigLoader {
    private static final ConfigLoader INSTANCE = new ConfigLoader();
    private final Properties properties;

    private ConfigLoader() {
        String env = System.getProperty("env", EnvType.STAGE.name());
        EnvType envType = EnvType.valueOf(env.toUpperCase());
        String file = switch (envType) {
            case STAGE -> "stg_config.properties";
            case PRODUCTION -> "prod_config.properties";
        };
        this.properties = PropertyUtils.propertyLoader(file);
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
        return properties.getProperty("browser", "chrome");
    }

    public int getImplicitWaitSeconds() {
        return Integer.parseInt(properties.getProperty("implicitWaitSeconds", "0"));
    }

    public int getExplicitWaitSeconds() {
        return Integer.parseInt(properties.getProperty("explicitWaitSeconds", "10"));
    }
}
