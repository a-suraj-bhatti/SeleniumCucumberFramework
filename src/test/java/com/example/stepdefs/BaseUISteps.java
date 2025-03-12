package com.example.stepdefs;

import com.example.pages.PageFactory;
import com.example.enums.TestType;
import com.example.context.TestContext;
import com.example.actions.UiActions;

public class BaseUISteps {
    protected final PageFactory pages;
    protected final UiActions uiActions;
    
    public BaseUISteps() {
        TestContext testContext = Hooks.getTestContext();
        if (testContext.getTestType() != TestType.UI) {
            throw new IllegalStateException("UI Steps cannot be used with non-UI tests");
        }
        this.pages = testContext.getPages();
        this.uiActions = testContext.getPage();
    }
} 