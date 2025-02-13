package com.example.driver;

import com.example.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import java.util.HashMap;
import java.util.Map;

/**
 * DriverFactory manages WebDriver initialization based on configuration
 * and stores the driver instance in a ThreadLocal so that each thread gets its own instance.
 */
public class DriverFactory {
    // Use ThreadLocal to ensure each thread gets its own WebDriver instance.
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        
        // Add options from config
        ConfigReader.getBrowserOptions("chrome").forEach(options::addArguments);
        
        // Set headless mode if configured
        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless=new");
        }
        
        // Configure download directory
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", ConfigReader.getDownloadDirectory());
        options.setExperimentalOption("prefs", prefs);
        
        // Enable WebSocket DevTools
        options.setCapability("webSocketUrl", true);
        
        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        
        // Add options from config
        ConfigReader.getBrowserOptions("firefox").forEach(options::addArguments);
        
        // Set headless mode if configured
        if (ConfigReader.isHeadless()) {
            options.addArguments("-headless");
        }
        
        // Enable WebSocket DevTools
        options.setCapability("webSocketUrl", true);
        
        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        
        // Add options from config
        ConfigReader.getBrowserOptions("edge").forEach(options::addArguments);
        
        // Set headless mode if configured
        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless=new");
        }
        
        // Enable WebSocket DevTools
        options.setCapability("webSocketUrl", true);
        
        return options;
    }

    /**
     * Initializes the WebDriver instance based on the browser specified in the config.
     */
    public static void initDriver() {
        String browser = ConfigReader.getBrowserName().toLowerCase();
        WebDriver drv = null;
        
        switch (browser) {
            case "firefox":
                drv = new FirefoxDriver(getFirefoxOptions());
                break;
            case "edge":
                drv = new EdgeDriver(getEdgeOptions());
                break;
            case "chrome":
            default:
                drv = new ChromeDriver(getChromeOptions());
                break;
        }
        
        driver.set(drv);
    }

    /**
     * Retrieves the current thread's WebDriver instance.
     *
     * @return the WebDriver instance for the current thread
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Quits the WebDriver instance for the current thread and then removes it from the ThreadLocal.
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
} 