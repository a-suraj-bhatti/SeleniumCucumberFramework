package com.example.actions;

import com.example.config.ConfigReader;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;
import java.util.ArrayList;
import java.util.Set;

/**
 * A helper class for common UI interactions that include built-in auto-wait functionality,
 * similar to what Playwright provides.
 */
public class UiActions {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;
    
    /**
     * Constructs a new UiActions helper.
     *
     * @param driver the WebDriver instance to be used
     */
    public UiActions(WebDriver driver) {
        this.driver = driver;
        // Instantiate explicit wait duration from config properties
        long waitDuration = ConfigReader.getExplicitWaitDuration();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));
        this.actions = new Actions(driver);
    }
    
    /**
     * Clicks on an element after waiting for it to be clickable.
     *
     * @param locator the locator of the element to click
     */
    public void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }
    
    /**
     * Fills an input field by clearing any existing text then sending the specified keys.
     *
     * @param locator the locator of the input field
     * @param text the text to send
     */
    public void fill(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Retrieves the visible text of an element.
     *
     * @param locator the locator of the element
     * @return the text content of the element
     */
    public String getText(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getText();
    }
    
    /**
     * Hovers over an element.
     *
     * @param locator the locator of the element to hover on
     */
    public void hover(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        actions.moveToElement(element).perform();
    }
    
    /**
     * Scrolls the page until the element is in view.
     *
     * @param locator the locator of the element to scroll into view
     */
    public void scrollIntoView(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    /**
     * Double-clicks on an element after waiting for it to be clickable.
     *
     * @param locator the locator of the element to double-click
     */
    public void doubleClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        actions.doubleClick(element).perform();
    }
    
    /**
     * Right-clicks (context click) on an element.
     *
     * @param locator the locator of the element for the context click
     */
    public void rightClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        actions.contextClick(element).perform();
    }
    
    /**
     * Clears the input field.
     *
     * @param locator the locator of the input field to clear
     */
    public void clear(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
    }
    
    // ===== Navigation Methods =====
    
    /**
     * Navigates to the specified URL and waits for the page to load.
     *
     * @param url the URL to navigate to
     */
    public void navigateTo(String url) {
        driver.navigate().to(url);
        waitForPageLoad();
    }
    
    /**
     * Refreshes the current page and waits for it to load.
     */
    public void refresh() {
        driver.navigate().refresh();
        waitForPageLoad();
    }
    
    /**
     * Navigates back in browser history and waits for the page to load.
     */
    public void goBack() {
        driver.navigate().back();
        waitForPageLoad();
    }
    
    /**
     * Navigates forward in browser history and waits for the page to load.
     */
    public void goForward() {
        driver.navigate().forward();
        waitForPageLoad();
    }
    
    /**
     * Waits for the page to load completely by checking the document.readyState.
     */
    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
    }
    
    // ===== Validation Methods =====
    
    /**
     * Checks if an element is displayed on the page.
     *
     * @param locator the locator of the element
     * @return true if the element is displayed, false otherwise
     */
    public boolean isDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if an element exists in the DOM.
     *
     * @param locator the locator of the element
     * @return true if the element exists, false otherwise
     */
    public boolean exists(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Checks if an element is enabled.
     *
     * @param locator the locator of the element
     * @return true if the element is enabled, false otherwise
     */
    public boolean isEnabled(By locator) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator)).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if an element is selected (for checkboxes, radio buttons, etc.).
     *
     * @param locator the locator of the element
     * @return true if the element is selected, false otherwise
     */
    public boolean isSelected(By locator) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator)).isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the value of a specified attribute.
     *
     * @param locator the locator of the element
     * @param attribute the name of the attribute
     * @return the attribute value or empty string if not found
     */
    public String getAttribute(By locator, String attribute) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator))
                    .getAttribute(attribute);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Checks if an element contains specific text.
     *
     * @param locator the locator of the element
     * @param text the text to check for
     * @return true if the element contains the text, false otherwise
     */
    public boolean containsText(By locator, String text) {
        try {
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Waits for an element to be present and returns its CSS value.
     *
     * @param locator the locator of the element
     * @param cssProperty the name of the CSS property
     * @return the value of the CSS property
     */
    public String getCssValue(By locator, String cssProperty) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator))
                .getCssValue(cssProperty);
    }
    
    // Additional UI actions can be added as needed (e.g., drag and drop, send special keys, etc.)

    // ===== Window Handling Methods =====
    
    /**
     * Switches to the last opened window
     */
    public void switchToLastWindow() {
        Set<String> windowHandles = driver.getWindowHandles();
        ArrayList<String> handles = new ArrayList<>(windowHandles);
        driver.switchTo().window(handles.get(handles.size() - 1));
    }

    /**
     * Switches to a specific window by handle
     * @param windowHandle the handle of the window to switch to
     */
    public void switchToWindow(String windowHandle) {
        driver.switchTo().window(windowHandle);
    }

    /**
     * Gets all window handles
     * @return Set of window handles
     */
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    // ===== Frame Handling Methods =====

    /**
     * Switches to an iframe using a locator
     * @param frameLocator the locator of the iframe
     */
    public void switchToFrame(By frameLocator) {
        WebElement frameElement = wait.until(ExpectedConditions.presenceOfElementLocated(frameLocator));
        driver.switchTo().frame(frameElement);
    }

    /**
     * Switches to an iframe using index
     * @param frameIndex the index of the iframe
     */
    public void switchToFrame(int frameIndex) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
    }

    /**
     * Switches back to the default content (main document)
     */
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    // ===== Shadow DOM Methods =====

    /**
     * Finds an element within a shadow root
     * @param hostLocator the locator of the shadow host element
     * @param shadowLocator the locator within the shadow DOM
     * @return WebElement from within the shadow DOM
     */
    public WebElement findInShadowRoot(By hostLocator, By shadowLocator) {
        WebElement shadowHost = wait.until(ExpectedConditions.presenceOfElementLocated(hostLocator));
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        return shadowRoot.findElement(shadowLocator);
    }

    /**
     * Clicks an element within a shadow root
     * @param hostLocator the locator of the shadow host element
     * @param shadowLocator the locator within the shadow DOM
     */
    public void clickInShadowRoot(By hostLocator, By shadowLocator) {
        findInShadowRoot(hostLocator, shadowLocator).click();
    }

    /**
     * Fills a text field within a shadow root
     * @param hostLocator the locator of the shadow host element
     * @param shadowLocator the locator within the shadow DOM
     * @param text the text to enter
     */
    public void fillInShadowRoot(By hostLocator, By shadowLocator, String text) {
        WebElement element = findInShadowRoot(hostLocator, shadowLocator);
        element.clear();
        element.sendKeys(text);
    }
} 