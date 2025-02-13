package com.example.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public class GoogleSteps extends BaseUISteps {
    
    @Given("I am on the Google homepage")
    public void iAmOnGoogleHomepage() {
        ExtentCucumberAdapter.addTestStepLog("Navigating to Google homepage");
        pages.googlePage().open();
    }
    
    @When("I search for {string}")
    public void iSearchFor(String searchText) {
        ExtentCucumberAdapter.addTestStepLog("Searching for: " + searchText);
        pages.googlePage().searchFor(searchText);
    }
    
    @Then("I should see the Gmail link")
    public void iShouldSeeGmailLink() {
        Assert.assertTrue(pages.googlePage().isGmailLinkVisible(), 
            "Gmail link should be visible on Google homepage");
    }
} 