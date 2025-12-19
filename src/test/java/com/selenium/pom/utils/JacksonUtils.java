package com.selenium.pom.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class JacksonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JacksonUtils() {}

    public static <T> T deserializeJson(String resourceName, Class<T> clazz) {
        try (InputStream is = JacksonUtils.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new IllegalArgumentException("Resource not found: " + resourceName);
            }
            return MAPPER.readValue(is, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON: " + resourceName, e);
        }
    }
}

