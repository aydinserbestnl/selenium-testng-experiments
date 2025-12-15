package com.selenium.pom.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class JacksonUtils {

    public static <T> T deserializeJson(String fileName, Class<T> clazz) throws IOException {
        try (InputStream is = JacksonUtils.class.getClassLoader().getResourceAsStream(fileName)) {
            Objects.requireNonNull(is, "file not found: " + fileName);
            return new ObjectMapper().readValue(is, clazz);
        }
    }
}
