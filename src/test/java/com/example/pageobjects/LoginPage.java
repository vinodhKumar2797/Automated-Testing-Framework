package com.example.pageobjects;

import com.example.utils.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("loginBtn");
    private final By errorMessageDiv = By.cssSelector("div.error-message");
    private final By welcomeMessageDiv = By.cssSelector("div.welcome-message");

    public LoginPage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        // Wait for the username field to be visible as page load check
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
            logger.info("Login page loaded successfully");
        } catch (TimeoutException e) {
            logger.error("Login page did not load properly: username input not visible", e);
            throw e;
        }
    }

    public LoginPage enterUsername(String username) {
        try {
            WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(usernameInput));
            usernameField.clear();
            usernameField.sendKeys(username);
            logger.info("Entered username");
        } catch (TimeoutException | NoSuchElementException e) {
            logger.error("Username input field not found or not interactable", e);
            throw e;
        }
        return this;
    }

    public LoginPage enterPassword(String password) {
        try {
            WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
            passwordField.clear();
            passwordField.sendKeys(password);
            logger.info("Entered password");
        } catch (TimeoutException | NoSuchElementException e) {
            logger.error("Password input field not found or not interactable", e);
            throw e;
        }
        return this;
    }

    public void clickLoginButton() {
        try {
            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            loginBtn.click();
            logger.info("Clicked login button");
        } catch (TimeoutException | NoSuchElementException e) {
            logger.error("Login button not found or not clickable", e);
            throw e;
        }
    }

    /**
     * Waits for and returns the error message text displayed on failed login.
     *
     * @return error message string or empty if not found
     */
    public String getErrorMessage() {
        try {
            WebElement errorDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageDiv));
            String errorText = errorDiv.getText().trim();
            logger.info("Error message found: {}", errorText);
            return errorText;
        } catch (TimeoutException e) {
            logger.warn("No error message displayed");
            return "";
        }
    }

    /**
     * Waits for and returns the welcome message text after successful login.
     *
     * @return welcome message string or empty if not found
     */
    public String getWelcomeMessage() {
        try {
            WebElement welcomeDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessageDiv));
            String welcomeText = welcomeDiv.getText().trim();
            logger.info("Welcome message found: {}", welcomeText);
            return welcomeText;
        } catch (TimeoutException e) {
            logger.warn("No welcome message displayed");
            return "";
        }
    }

    /**
     * Verifies if the current page is the dashboard page by checking the presence of the welcome message.
     *
     * @return true if dashboard page detected; false otherwise
     */
    public boolean isDashboardPage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessageDiv));
            logger.info("Dashboard page detected by presence of welcome message");
            return true;
        } catch (TimeoutException e) {
            logger.warn("Dashboard page not detected");
            return false;
        }
    }
}
