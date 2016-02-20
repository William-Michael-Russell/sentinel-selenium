package net.testaholic.core.templates;

import net.testaholic.core.driver.DriverProviderThread;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestTemplateCore {

    private static List<DriverProviderThread> driverProviderThreadPool = Collections.synchronizedList(new ArrayList<>());
    private static ThreadLocal<DriverProviderThread> driverThread;

    @BeforeSuite
    public static void startWebDriverThreads() {
        driverThread = new ThreadLocal<DriverProviderThread>() {
            @Override
            protected DriverProviderThread initialValue() {
                DriverProviderThread webDriverThread = new DriverProviderThread();
                driverProviderThreadPool.add(webDriverThread);
                return webDriverThread;
            }
        };
    }

    public static WebDriver getDriver() throws Exception {
        return driverThread.get().getDriver();
    }

    @AfterMethod
    public static void clearCookies() throws Exception {
        getDriver().manage().deleteAllCookies();
    }

    @AfterSuite
    public static void closeDriverObjects() {
        for (DriverProviderThread driverProviderThread : driverProviderThreadPool) {
            driverProviderThread.quitDriver();
        }
    }
}