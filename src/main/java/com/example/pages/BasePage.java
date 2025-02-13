package com.example.pages;

import com.example.actions.UiActions;

public class BasePage {
    protected UiActions page;
    
    public BasePage(UiActions page) {
        this.page = page;
    }
    
    // Common methods that all pages might need
    protected void waitForPageLoad() {
        // Add any common page load waiting logic here if needed
    }
} 