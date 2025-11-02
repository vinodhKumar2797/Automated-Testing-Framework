package com.example.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * The TestRunner class integrates Cucumber with TestNG.
 * It specifies the locations of feature files and step definitions,
 * as well as the output formats and tags for selective test execution.
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.example.stepdefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/html-report.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml"
        },
        monochrome = true,
        tags = "not @ignore"
)
public class TestRunner extends AbstractTestNGCucumberTests {
    // No additional code needed. Inherits run method from AbstractTestNGCucumberTests.
}
