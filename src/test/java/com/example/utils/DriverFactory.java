package com.example.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public final class DriverFactory {

    private static final Logger logger = LogManager.getLogger(DriverFactory.class);

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverFactory() {
        // Prevent instantiation
    }

    /**
     * Initializes and returns the WebDriver instance for the current thread.
     * If already initialized, returns the existing instance.
     *
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            driverThreadLocal.set(createDriver());
        }
        return driverThreadLocal.get();
    }

    /**
     * Creates a new WebDriver based on the configured browser.
     *
     * @return WebDriver instance
     */
    private static WebDriver createDriver() {
        String browser = ConfigLoader.getBrowser();
        WebDriver driver;
        switch (browser) {
            case "firefox":
                logger.info("Initializing Firefox WebDriver");
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--disable-infobars");
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
            default:
                logger.info("Initializing Chrome WebDriver");
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-infobars");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--window-size=1920,1080");
                driver = new ChromeDriver(chromeOptions);
                break;
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigLoader.getImplicitWait()));
        driver.manage().window().maximize();
        logger.info("WebDriver initialized successfully");
        return driver;
    }

    /**
     * Cleans up the WebDriver instance for the current thread.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
                logger.info("WebDriver quit successfully");
            } catch (Exception e) {
                logger.warn("Error occurred while quitting WebDriver: {}", e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }
}
