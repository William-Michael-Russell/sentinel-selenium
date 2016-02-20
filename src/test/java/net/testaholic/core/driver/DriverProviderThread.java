package net.testaholic.core.driver;


import net.testaholic.core.config.SeleniumConfig;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by williamrussell on 2/18/16.
 */
public class DriverProviderThread {

    private WebDriver webdriver;
    private SeleniumConfig config = SeleniumConfig.getInstance();


    public WebDriver getDriver() throws Exception {
        if (null == webdriver) {
            webdriver = getDriverInstance();
        }
        return webdriver;
    }


    private WebDriver getDriverInstance() {
        switch (config.getDefinedCapabilities().getDriverProvider()) {
            case IE:
                webdriver = DriverProvider.IE.getDriverObject();
                break;
            case FIREFOX:
                webdriver = DriverProvider.FIREFOX.getDriverObject();
                break;
            case CHROME:
                webdriver = DriverProvider.CHROME.getDriverObject();
                break;
            case SAFARI:
                webdriver = DriverProvider.SAFARI.getDriverObject();
                break;
            case ANDROID:
                webdriver = DriverProvider.ANDROID.getDriverObject();
                break;
            case IOS:
                webdriver = DriverProvider.IOS.getDriverObject();
                break;
            default:
                System.out.println("driver was not specified, defaulting to firefox.");
                webdriver = DriverProvider.FIREFOX.getDriverObject();
        }
        webdriver.manage().timeouts().implicitlyWait(config.getWebDriverWait(), TimeUnit.SECONDS);
        if(config.isUseRemoteWebDriver()){
            return getRemoveWebDriver();
        }
        else {
            return webdriver;
        }
    }



    private WebDriver getRemoveWebDriver() {

        DesiredCapabilities desiredCapabilities = SeleniumConfig.getInstance().getDefinedCapabilities().getDesiredCapabilities();

        String gridURL = System.getProperty("gridURL");
        if (gridURL == null) {
            throw new NullPointerException("remote web driver enabled, but the grilURL was null or not provided.");
        } else {
            URL seleniumGridURL = null;
            try {
                seleniumGridURL = new URL(gridURL);
            } catch (MalformedURLException e) {
                System.out.println("grid url was malformed");
                e.printStackTrace();
            }

            if (null != config.getDesiredPlatform() && !config.getDesiredPlatform().isEmpty()) {
                desiredCapabilities.setPlatform(Platform.valueOf(config.getDesiredPlatform().toUpperCase()));
            }

            if (null != config.getDesiredBrowserVersion() && !config.getDesiredBrowserVersion().isEmpty()) {
                desiredCapabilities.setVersion(config.getDesiredBrowserVersion().toUpperCase());
            }

            webdriver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
            return webdriver;
        }
    }

    public void quitDriver() {
        if (null != webdriver) {
            webdriver.quit();
        }
    }
}
