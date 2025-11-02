package com.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigLoader {

    private static final Logger logger = LogManager.getLogger(ConfigLoader.class);
    private static final String CONFIG_FILE = "/config.properties";

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new RuntimeException("Configuration file '" + CONFIG_FILE + "' not found in classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            logger.error("Failed to load configuration properties", e);
            throw new RuntimeException("Failed to load configuration properties", e);
        }
    }

    private ConfigLoader() {
        // Prevent instantiation
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url").trim();
    }

    public static String getBrowser() {
        return properties.getProperty("browser", "chrome").trim().toLowerCase();
    }

    public static int getImplicitWait() {
        String value = properties.getProperty("implicit.wait", "10").trim();
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.warn("Invalid implicit.wait value; defaulting to 10 seconds");
            return 10;
        }
    }

    public static int getExplicitWait() {
        String value = properties.getProperty("explicit.wait", "15").trim();
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.warn("Invalid explicit.wait value; defaulting to 15 seconds");
            return 15;
        }
    }

    public static String getValidUsername() {
        return properties.getProperty("valid.username", "").trim();
    }

    public static String getValidPassword() {
        return properties.getProperty("valid.password", "").trim();
    }

    public static String getInvalidUsername() {
        return properties.getProperty("invalid.username", "").trim();
    }

    public static String getInvalidPassword() {
        return properties.getProperty("invalid.password", "").trim();
    }
}
