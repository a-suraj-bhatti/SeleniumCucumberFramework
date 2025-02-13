package com.example.context;

import com.example.actions.UiActions;
import com.example.driver.DriverFactory;
import com.example.pages.PageFactory;
import com.example.enums.TestType;

public class TestContext {
    private UiActions page;
    private PageFactory pages;
    private final TestType testType;
    
    public TestContext(TestType testType) {
        this.testType = testType;
        if (testType == TestType.UI) {
            initializeUIComponents();
        }
    }
    
    private void initializeUIComponents() {
        DriverFactory.initDriver();
        page = new UiActions(DriverFactory.getDriver());
        pages = new PageFactory(page);
    }
    
    public UiActions getPage() {
        checkUIContext();
        return page;
    }
    
    public PageFactory getPages() {
        checkUIContext();
        return pages;
    }
    
    private void checkUIContext() {
        if (testType != TestType.UI) {
            throw new IllegalStateException("Attempting to use UI components in a non-UI test");
        }
    }
    
    public void tearDown() {
        if (testType == TestType.UI) {
            DriverFactory.quitDriver();
        }
    }
    
    public TestType getTestType() {
        return testType;
    }
} 