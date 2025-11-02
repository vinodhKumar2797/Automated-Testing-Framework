package com.example.stepdefinitions;

import com.example.pageobjects.LoginPage;
import com.example.utils.ConfigLoader;
import com.example.utils.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginSteps {

    private static final Logger logger = LogManager.getLogger(LoginSteps.class);

    private WebDriver driver;
    private LoginPage loginPage;

    @Before
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.get(ConfigLoader.getBaseUrl());
        loginPage = new LoginPage();
        logger.info("Navigated to login page");
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
        logger.info("WebDriver quit after scenario");
    }

    @Given("the user is on the login page")
    public void userIsOnLoginPage() {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("login"), "User is not on login page");
        logger.info("User confirmed on login page");
    }

    @When("the user enters valid username and password")
    public void userEntersValidUsernameAndPassword() {
        loginPage.enterUsername(ConfigLoader.getValidUsername())
                 .enterPassword(ConfigLoader.getValidPassword());
        logger.info("Entered valid username and password");
    }

    @When("the user enters username {string} and password {string}")
    public void userEntersUsernameAndPassword(String username, String password) {
        loginPage.enterUsername(username)
                 .enterPassword(password);
        logger.info("Entered username '{}' and password '{}'", username, password);
    }

    @When("clicks the login button")
    public void clicksTheLoginButton() {
        loginPage.clickLoginButton();
        logger.info("Clicked login button");
    }

    @Then("the user should be redirected to the dashboard")
    public void userShouldBeRedirectedToDashboard() {
        boolean onDashboard = loginPage.isDashboardPage();
        Assert.assertTrue(onDashboard, "User was not redirected to the dashboard");
        logger.info("User redirected to dashboard confirmed");
    }

    @Then("a welcome message should be displayed")
    public void welcomeMessageShouldBeDisplayed() {
        String welcomeMessage = loginPage.getWelcomeMessage();
        Assert.assertFalse(welcomeMessage.isEmpty(), "Welcome message is not displayed");
        logger.info("Welcome message displayed: {}", welcomeMessage);
    }

    @Then("an error message {string} should be displayed")
    public void errorMessageShouldBeDisplayed(String expectedErrorMessage) {
        String actualErrorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message does not match expected");
        logger.info("Error message validated: '{}'", actualErrorMessage);
    }
}
