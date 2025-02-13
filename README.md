# Selenium Test Automation Framework

A modern, thread-safe Selenium test automation framework with built-in parallel execution support, automatic waits, and extensive browser control capabilities.

## Features

- **Page Object Model (POM)** design pattern
- **Thread-safe** parallel execution
- **Cross-browser** support (Chrome, Firefox, Edge)
- **Configurable** through properties file
- **Extended browser control** including:
  - Shadow DOM handling
  - IFrame management
  - Window handling
- **Reporting**
  - ExtentReports HTML reports
  - TestNG XML reports

## Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/a-suraj-bhatti/SeleniumCucumberFramework.git
   cd SeleniumCucumberFramework
   ```

2. **Install Dependencies**:
   Make sure you have Maven installed. Run the following command to install the required dependencies:
   ```bash
   mvn clean install
   ```

3. **Configure WebDriver**:
   Ensure that the appropriate WebDriver binaries (e.g., ChromeDriver, GeckoDriver) are available in your system's PATH or specify their locations in the `DriverFactory` class.

## Running Tests

1. **Run All Tests**:
   You can run all tests (both UI and API) using the following command:
   ```bash
   mvn test
   ```

2. **Run Specific Tests**:
   To run only UI tests, use:
   ```bash
   mvn test -Dcucumber.filter.tags="@ui"
   ```

   To run only API tests, use:
   ```bash
   mvn test -Dcucumber.filter.tags="@api"
   ```

3. **View Reports**:
   After running the tests, you can view the ExtentReports in the `test-output/SparkReport/Spark.html` file.

## Writing Tests

1. **Feature Files**:
   - Create feature files in the `src/test/resources/features` directory.
   - Use Gherkin syntax to define scenarios. For example:
     ```gherkin
     @ui @google
     Feature: Google Search Functionality
       Scenario: Verify Gmail link is visible
         Given I am on the Google homepage
         Then I should see the Gmail link
     ```

2. **Step Definitions**:
   - Implement step definitions in the `src/test/java/com/example/stepdefs` directory.
   - Each step in the feature file should have a corresponding method annotated with Cucumber annotations (`@Given`, `@When`, `@Then`).

3. **Page Object Model**:
   - Use the Page Object Model to encapsulate the UI interactions in the `src/main/java/com/example/pages` directory.
   - Utilize the Page Factory pattern to initialize page elements, which helps in managing the elements more efficiently.

## Reporting

- The framework uses ExtentReports to generate detailed reports.
- Configure the reporting settings in `src/test/resources/extent.properties` and `src/test/resources/extent-config.xml`.
- Reports will be generated in the `test-output/SparkReport` directory after test execution.


  
  