@api @users
Feature: User API Tests
  As an API user
  I want to test user endpoints
  So that I can verify API functionality

  @smoke
  Scenario: Get user details
    Given I have a valid user ID
    When I send GET request to "/users/{id}"
    Then the response status code should be 200 