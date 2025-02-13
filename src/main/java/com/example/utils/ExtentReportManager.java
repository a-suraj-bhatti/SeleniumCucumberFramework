package com.example.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    
    public static void initReport() {
        if (extent == null) {
            extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter("test-output/extent-report.html");
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("Test Execution Report");
            extent.attachReporter(spark);
        }
    }
    
    public static void createTest(String testName) {
        test.set(extent.createTest(testName));
    }
    
    public static void logPass(String message) {
        test.get().pass(message);
    }
    
    public static void logFail(String message) {
        test.get().fail(message);
    }
    
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
} 