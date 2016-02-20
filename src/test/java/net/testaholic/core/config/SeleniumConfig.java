package net.testaholic.core.config;

import net.testaholic.core.driver.DefinedDesiredCapabilities;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.firefox.FirefoxProfile;

import static net.testaholic.core.driver.DefinedDesiredCapabilities.valueOf;

/**
 * Created by williamrussell on 2/19/16.
 *
 * All configuration settings are in this singleton class.
 * The majority of settings can be overridden from the command line, for example:
 * -Dbrowser=Firefox
 *
 */
public class SeleniumConfig {

    private static SeleniumConfig _INSTANCE;

    //SELENIUM

    private Integer webDriverWait = Integer.getInteger("waitTime", 10);
    private DefinedDesiredCapabilities definedCapabilities = DefinedDesiredCapabilities.CHROME;
    private final String browser = System.getProperty("browser", definedCapabilities.toString()).toUpperCase();


    //FIREFOX Specific
    private final boolean unstableFF = Boolean.getBoolean("unstableFF") ? false : false; // set both to true to avoid system params
    private final boolean enableFirebug = Boolean.getBoolean("enableFirebug") ? false : false; // set both to true to avoid system params

    //CHROME
    // http://peter.sh/experiments/chromium-command-line-switches/
    private final String chromeSwitches = System.getProperty("chromeSwitches");


    private final boolean useRemoteWebDriver = Boolean.getBoolean("remoteDriver");
    private final boolean proxyEnabled = Boolean.getBoolean("proxyEnabled");
    private final String proxyHostname = System.getProperty("proxyHost");
    private final Integer proxyPort = Integer.getInteger("proxyPort");
    private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);
    private FirefoxProfile firefoxProfile = new FirefoxProfile();

    private boolean disableFlash = Boolean.getBoolean("disableFlash");
    private boolean enableJSConsole = Boolean.getBoolean("enableJSConsole");


    //Appium
    private String appiumIp = "http://127.0.0.1:4723/wd/hub";


    //Remote Web Driver (Grid/Sauce)
    private String desiredBrowserVersion = System.getProperty("desiredBrowserVersion");
    private String desiredPlatform = System.getProperty("desiredPlatform");

    public static SeleniumConfig getInstance() {
        if (_INSTANCE == null) {
            _INSTANCE = new SeleniumConfig();
        }
        return _INSTANCE;
    }

    public Integer getWebDriverWait() {
        return webDriverWait;
    }

    /**
     * Determines capabilities from System.getProperty(browser);
     *
     * @return will return browser matched capabilities, otherwise will return default capabilities
     */
    public DefinedDesiredCapabilities getDefinedCapabilities() {
        try {
            if(!browser.isEmpty() && StringUtils.isNotBlank(browser)){
                definedCapabilities = valueOf(browser);
            }
        } catch (IllegalArgumentException ignored ) {
            System.err.println("Unknown Capabilities, defaulting to '" + definedCapabilities + "'...");
        } catch (NullPointerException ignored) {
            System.err.println("No Capabilities specified, defaulting to '" + definedCapabilities + "'...");
        }
        return definedCapabilities;
    }

    public boolean isUnstableFF() {
        return unstableFF;
    }

    public boolean isEnableFirebug() {
        return enableFirebug;
    }

    public boolean isEnableJSConsole() {
        return enableJSConsole;
    }

    public String getBrowser() {
        return browser;
    }

    public boolean isUseRemoteWebDriver() {
        return useRemoteWebDriver;
    }

    public boolean isProxyEnabled() {
        return proxyEnabled;
    }

    public String getProxyHostname() {
        return proxyHostname;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public String getProxyDetails() {
        return proxyDetails;
    }

    public FirefoxProfile getFirefoxProfile() {
        return firefoxProfile;
    }

    public boolean isDisableFlash() {
        return disableFlash;
    }

    public String getAppiumIp() {
        return appiumIp;
    }

    public String getDesiredBrowserVersion() {
        return desiredBrowserVersion;
    }

    public String getDesiredPlatform() {
        return desiredPlatform;
    }

    public String getChromeSwitches() {
        return chromeSwitches;
    }
}
