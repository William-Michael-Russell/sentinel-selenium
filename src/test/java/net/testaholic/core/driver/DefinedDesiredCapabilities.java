package net.testaholic.core.driver;

//https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/

import net.testaholic.core.config.SeleniumConfig;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import static org.openqa.selenium.Proxy.ProxyType.MANUAL;
import static org.openqa.selenium.remote.CapabilityType.PROXY;

public enum DefinedDesiredCapabilities implements DriverCaps{

    ANDROID_NEXUS_7 {
        public DesiredCapabilities getDesiredCapabilities() {

            DesiredCapabilities capabilities = DesiredCapabilities.android();
            capabilities.setCapability("appiumVersion", "1.4.16");
            capabilities.setCapability("deviceName", "Google Nexus 7 HD Emulator");
            capabilities.setCapability("deviceOrientation", "portrait");
            capabilities.setCapability("browserName", "Browser");
            capabilities.setCapability("platformVersion", "4.4");
            capabilities.setCapability("platformName", "Android");
            return addProxySettings(capabilities);
        }

        public DriverProvider getDriverProvider(){
            return DriverProvider.ANDROID;
        }
    },

    IOS_9_1 {
        public DesiredCapabilities getDesiredCapabilities() {

            DesiredCapabilities capabilities = DesiredCapabilities.android();
            capabilities.setCapability("appiumVersion", "1.4.16");
            capabilities.setCapability("deviceName", "iPhone Simulator");
            capabilities.setCapability("deviceOrientation", "portrait");
            capabilities.setCapability("browserName", "Browser");
            capabilities.setCapability("platformVersion", "9.1");
            capabilities.setCapability("platformName", "iOS");

            return addProxySettings(capabilities);
        }

        public DriverProvider getDriverProvider(){
            return DriverProvider.IOS;
        }
    },

    FIREFOX {
        public DesiredCapabilities getDesiredCapabilities() {
            SeleniumConfig config = SeleniumConfig.getInstance();
            FirefoxProfile firefoxProfile = new FirefoxProfile();

            if (config.isUnstableFF()) {
                // http://stackoverflow.com/questions/20954605/firefoxdriver-webdriver-load-strategy-unstable-findelements-getting-elements-fro
                firefoxProfile.setPreference("webdriver.load.strategy", "unstable");
            }


            if (config.isEnableFirebug()) {
                String firebug = "Firebug" + File.separator + "firebug-2.0.14.xpi";
                try {
                    firefoxProfile.addExtension(new File(getClass().getClassLoader().getResource(firebug).getFile()));
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }
            }

            if (config.isEnableJSConsole()) {
                String jsErrorCollector = "Firebug" + File.separator + "JSErrorCollector.xpi";
                try {
                    firefoxProfile.addExtension(new File(getClass().getClassLoader().getResource(jsErrorCollector).getFile()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (config.isDisableFlash()) {
                firefoxProfile.setPreference("plugin.state.flash", 0);
            }

            firefoxProfile.setPreference("reader.parse-on-load.enabled", false);
            firefoxProfile.setAcceptUntrustedCertificates(true);

            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
            return addProxySettings(capabilities);
        }

        public DriverProvider getDriverProvider(){
            return DriverProvider.FIREFOX;
        }
    },

    CHROME {
        public DesiredCapabilities getDesiredCapabilities() {
            SeleniumConfig config = SeleniumConfig.getInstance();

            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
            HashMap<String, String> chromePreferences = new HashMap<>();
            chromePreferences.put("profile.password_manager_enabled", "false");
            capabilities.setCapability("chrome.prefs", chromePreferences);

            ChromeOptions chromeOptions = new ChromeOptions();
            if (StringUtils.isNotBlank(config.getChromeSwitches())) {
                chromeOptions.addArguments(config.getChromeSwitches());
            }
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            capabilities.setCapability(CapabilityType.LOGGING_PREFS, setBrowserLogging(Level.INFO));
            return addProxySettings(capabilities);
        }

        public DriverProvider getDriverProvider(){
            return DriverProvider.CHROME;
        }
    },

    IE {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
            capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
            capabilities.setCapability("requireWindowFocus", true);
            return addProxySettings(capabilities);
        }

        public DriverProvider getDriverProvider(){
            return DriverProvider.IE;
        }
    },

    SAFARI {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.safari();
            capabilities.setCapability("safari.cleanSession", true);
            return addProxySettings(capabilities);
        }
        public DriverProvider getDriverProvider(){
            return DriverProvider.SAFARI;
        }
    },

    OPERA {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.operaBlink();
            return addProxySettings(capabilities);
        }

        public DriverProvider getDriverProvider(){
            return DriverProvider.OPERA;
        }
    },
    PHANTOMJS {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
            final List<String> cliArguments = new ArrayList<>();
            cliArguments.add("--web-security=false");
            cliArguments.add("--ssl-protocol=any");
            cliArguments.add("--ignore-ssl-errors=true");
            capabilities.setCapability("phantomjs.cli.args", applyPhantomJSProxySettings(cliArguments, createProxy()));
            capabilities.setCapability("takesScreenshot", true);

            return capabilities;
        }
        public DriverProvider getDriverProvider(){
            return DriverProvider.PHANTOMJS;
        }
    };


    protected DesiredCapabilities addProxySettings(DesiredCapabilities capabilities) {
        SeleniumConfig config = SeleniumConfig.getInstance();
        if (config.isProxyEnabled()) {
            capabilities.setCapability(PROXY, createProxy());
        }

        return capabilities;
    }

    protected List<String> applyPhantomJSProxySettings(List<String> cliArguments, Proxy proxySettings) {
        if (null == proxySettings) {
            cliArguments.add("--proxy-type=none");
        } else {
            cliArguments.add("--proxy-type=http");
            cliArguments.add("--proxy=" + proxySettings.getHttpProxy());
        }
        return cliArguments;
    }


    protected Proxy createProxy() {
        SeleniumConfig config = SeleniumConfig.getInstance();
        Proxy proxy = new Proxy();
        proxy.setProxyType(MANUAL);
        proxy.setHttpProxy(config.getProxyDetails());
        proxy.setSslProxy(config.getProxyDetails());
        return proxy;
    }

    protected LoggingPreferences setBrowserLogging(Level logLevel) {
        LoggingPreferences loggingprefs = new LoggingPreferences();
        loggingprefs.enable(LogType.BROWSER, logLevel);
        return loggingprefs;
    }
}