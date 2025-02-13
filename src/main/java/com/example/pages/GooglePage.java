package com.example.pages;

import org.openqa.selenium.By;
import com.example.actions.UiActions;

public class GooglePage extends BasePage {
    // Page URL
    private static final String PAGE_URL = "https://google.com";
    
    // Locators
    private static final By GMAIL_LINK = By.xpath("//a[normalize-space()='Gail']");
    private static final By SEARCH_BOX = By.name("q");
    private static final By SEARCH_BUTTON = By.name("btnK");
    
    public GooglePage(UiActions page) {
        super(page);
    }
    
    // Navigation
    public GooglePage open() {
        page.navigateTo(PAGE_URL);
        return this;
    }
    
    // Actions
    public boolean isGmailLinkVisible() {
        return page.isDisplayed(GMAIL_LINK);
    }
    
    public GooglePage searchFor(String searchText) {
        page.fill(SEARCH_BOX, searchText);
        page.click(SEARCH_BUTTON);
        return this;
    }
} 