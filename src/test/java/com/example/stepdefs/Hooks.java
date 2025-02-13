package com.example.stepdefs;

import com.example.context.TestContext;
import com.example.utils.ExtentReportManager;
import com.example.enums.TestType;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.example.driver.DriverFactory;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import java.util.Base64;
import java.util.Set;
import java.util.HashSet;

public class Hooks {
    private static final ThreadLocal<TestContext> testContext = new ThreadLocal<>();
    
    public static TestContext getTestContext() {
        return testContext.get();
    }
    
    @BeforeAll(order = 1)
    public static void beforeAllTests() {
        ExtentReportManager.initReport();
    }
    
    @AfterAll(order = 1)
    public static void afterAllTests() {
        ExtentReportManager.flushReport();
    }
    
    @Before(order = 1)
    public void setupTestContext(Scenario scenario) {
        Set<String> tags = new HashSet<>(scenario.getSourceTagNames());
        TestType testType;
        
        if (tags.contains("@ui")) {
            testType = TestType.UI;
        } else if (tags.contains("@api")) {
            testType = TestType.API;
        } else {
            throw new IllegalStateException(
                "Scenario must be tagged with either @ui or @api. Scenario: " + 
                scenario.getName()
            );
        }
        
        testContext.set(new TestContext(testType));
    }
    
    @Before(order = 2)
    public void beforeScenario(Scenario scenario) {
        ExtentReportManager.createTest(scenario.getName());
        ExtentCucumberAdapter.addTestStepLog("Starting scenario: " + scenario.getName());
        ExtentCucumberAdapter.addTestStepLog("Tags: " + scenario.getSourceTagNames());
    }
    
    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        TestContext context = testContext.get();
        if (scenario.isFailed()) {
            if (context.getTestType() == TestType.UI) {
                captureScreenshot(scenario);
            }
            ExtentReportManager.logFail(scenario.getName() + " failed");
        } else {
            ExtentReportManager.logPass(scenario.getName() + " passed");
        }
    }
    
    private void captureScreenshot(Scenario scenario) {
        try {
            final byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot");
            ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(
                "Screenshot on failure",
                new String(Base64.getEncoder().encode(screenshot))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @After(order = 0)
    public void tearDown() {
        TestContext context = testContext.get();
        if (context != null) {
            context.tearDown();
            testContext.remove();
        }
    }
} 