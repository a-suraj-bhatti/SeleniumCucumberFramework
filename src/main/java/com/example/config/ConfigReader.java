package com.example.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    
    static {
        try {
            // Load configuration from the config.properties file
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            properties = new Properties();
        }
    }
    
    public static String getBrowserName() {
        return properties.getProperty("browser.name", "chrome");
    }
    
    public static long getExplicitWaitDuration() {
        // Trim the value to remove any extra spaces before parsing.
        return Long.parseLong(properties.getProperty("explicit.wait.duration", "10").trim());
    }
    
    public static List<String> getBrowserOptions(String browser) {
        String options = properties.getProperty(browser + ".options", "");
        return options.isEmpty() ? List.of() : Arrays.asList(options.split(","));
    }
    
    public static boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("browser.headless", "false"));
    }
    
    public static String getDownloadDirectory() {
        return properties.getProperty("browser.download.directory", System.getProperty("user.home") + "/Downloads");
    }
} 