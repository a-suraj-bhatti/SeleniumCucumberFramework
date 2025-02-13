package com.example.pages;

import com.example.actions.UiActions;

public class PageFactory {
    private final UiActions page;
    
    public PageFactory(UiActions page) {
        this.page = page;
    }
    
    public GooglePage googlePage() {
        return new GooglePage(page);
    }
    
    // Add more page methods as needed
    // public LoginPage loginPage() { return new LoginPage(ui); }
    // public HomePage homePage() { return new HomePage(ui); }
} 