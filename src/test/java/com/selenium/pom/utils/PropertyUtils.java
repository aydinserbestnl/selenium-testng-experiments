package com.selenium.pom.utils;

import java.io.*;
import java.util.Properties;

public class PropertyUtils {
    public static Properties propertyLoader(String classpathFile) {
        Properties props = new Properties();
        try (InputStream is = PropertyUtils.class.getClassLoader().getResourceAsStream(classpathFile)) {
            if (is == null) throw new RuntimeException("Properties file not found: " + classpathFile);
            props.load(is);
            return props;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + classpathFile, e);
        }
    }
}
