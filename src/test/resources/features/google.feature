@ui @google @regression
Feature: Google Search Functionality
  As a user
  I want to use Google search
  So that I can find information on the internet

  @smoke @critical
  Scenario: Verify Gmail link is visible
    Given I am on the Google homepage
    Then I should see the Gmail link

  @search
  Scenario: Search on Google
    Given I am on the Google homepage
    When I search for "selenium automation"
    # Add more assertions as needed 