package net.testaholic.core.driver;

//https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import net.testaholic.core.config.SeleniumConfig;
import net.testaholic.core.utils.OperatingSystem;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.openqa.selenium.remote.CapabilityType.PROXY;

public enum DriverProvider implements DriverType {


    ANDROID {
        public WebDriver getDriverObject() {
            return new AndroidDriver<AndroidElement>(SeleniumConfig.getInstance().getDefinedCapabilities().getDesiredCapabilities());
        }
    },

    IOS {
        public WebDriver getDriverObject() {
            return new IOSDriver<IOSElement>(SeleniumConfig.getInstance().getDefinedCapabilities().getDesiredCapabilities());
        }
    },

    FIREFOX {
        public WebDriver getDriverObject() {
             validateFireFoxEnvironment();
            return new EventFiringWebDriver(new FirefoxDriver(SeleniumConfig.getInstance().getDefinedCapabilities().getDesiredCapabilities()));
        }
    },

    CHROME {
        public WebDriver getDriverObject() {
            String chromeBinaryPath = "";
            if (OperatingSystem.isWindowsOperatingSystem()) {
                switch (OperatingSystem.getSystemArchitecture()){
                    case X64:
                        chromeBinaryPath = "/chromedriver_win64/chromedriver.exe";
                        break;
                    case X86:
                        chromeBinaryPath = "/chromedriver_win32/chromedriver.exe";
                        break;
                    case X86_64:
                        chromeBinaryPath = "/chromedriver_win64/chromedriver.exe";
                        break;
                    default:
                        System.err.println("Could not determine architecture, defaulting to 32 bit driver.");
                        chromeBinaryPath = "/chromedriver_win32/chromedriver.exe";
                }

            } else if (OperatingSystem.isMacOperatingSystem()) {
                chromeBinaryPath = "/chromedriver_mac32/chromedriver";
                File chromedriver = new File(ClassLoader.getSystemResource("ChromeDriver" + chromeBinaryPath).getPath());

                // set application user permissions to 455
                chromedriver.setExecutable(true);
            } else if (OperatingSystem.isLinuxOperatingSystem()) {


                    switch (OperatingSystem.getSystemArchitecture()){
                        case X64:
                            chromeBinaryPath = "/chromedriver_linux64/chromedriver.exe";
                            break;
                        case X86:
                            chromeBinaryPath = "/chromedriver_linux32/chromedriver.exe";
                            break;
                        case X86_64:
                            chromeBinaryPath = "/chromedriver_linux64/chromedriver.exe";
                            break;
                        default:
                            System.err.println("Could not determine architecture, defaulting to 32 bit driver.");
                            chromeBinaryPath = "/chromedriver_linux32/chromedriver.exe";
                    }

                File chromedriver = new File(ClassLoader.getSystemResource("ChromeDriver" + chromeBinaryPath).getPath());

                // set application user permissions to 455
                chromedriver.setExecutable(true);
            }
            System.setProperty("webdriver.chrome.driver", new File(ClassLoader.getSystemResource("ChromeDriver" + chromeBinaryPath).getPath()).getPath());
            return new EventFiringWebDriver(new ChromeDriver(SeleniumConfig.getInstance().getDefinedCapabilities().getDesiredCapabilities()));
        }
    },
    IE {
        public WebDriver getDriverObject() {
            if(OperatingSystem.isLinuxOperatingSystem() || OperatingSystem.isMacOperatingSystem()){
                throw new RuntimeException("Unable to execute IE on linux or OSX");
            }
            File file = new File(ClassLoader.getSystemResource("IEDriver" + File.separator + "IEDriverServer.exe").getPath());
            System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
            return new EventFiringWebDriver(new InternetExplorerDriver(SeleniumConfig.getInstance().getDefinedCapabilities().getDesiredCapabilities()));
        }
    },

    SAFARI {
        public WebDriver getDriverObject() {
            return new EventFiringWebDriver(new SafariDriver(SeleniumConfig.getInstance().getDefinedCapabilities().getDesiredCapabilities()));
        }

    },
    OPERA {
        public WebDriver getDriverObject() {
            return new OperaDriver(SeleniumConfig.getInstance().getDefinedCapabilities().getDesiredCapabilities());
        }
    },
    PHANTOMJS {
        public WebDriver getDriverObject() {
            return new PhantomJSDriver(SeleniumConfig.getInstance().getDefinedCapabilities().getDesiredCapabilities());
        }
    };

    protected DesiredCapabilities addProxySettings(DesiredCapabilities capabilities, Proxy proxySettings) {
        if (null != proxySettings) {
            capabilities.setCapability(PROXY, proxySettings);
        }

        return capabilities;
    }

    //More options at
    //http://phantomjs.org/api/command-line.html
    protected List<String> applyPhantomJSProxySettings(List<String> phantArgs, Proxy proxySettings) {
        if (null == proxySettings) {
            phantArgs.add("--proxy-type=none");
        } else {
            phantArgs.add("--proxy-type=http");
            phantArgs.add("--proxy=" + proxySettings.getHttpProxy());
        }
        return phantArgs;
    }



    protected void validateFireFoxEnvironment(){
        // Windows 8 requires to set webdriver.firefox.bin system variable
        // to path where executive file of FF is placed
        if (OperatingSystem.isWindowsOperatingSystem()) {
            System.setProperty("webdriver.firefox.bin", "c:" + File.separator + "Program Files (x86)" + File.separator + "Mozilla Firefox" + File.separator + "Firefox.exe");
        }
        // Check if user who is running tests has write access in ~/.mozilla
        // dir and home dir
        if (OperatingSystem.isLinuxOperatingSystem()) {
            File homePath = new File(System.getenv("HOME") + File.separator);
            File mozillaPath = new File(homePath + File.separator + ".mozilla");
            File tmpFile;
            if (mozillaPath.exists()) {
                try {
                    tmpFile = File.createTempFile("webdriver", null, mozillaPath);
                } catch (IOException ex) {
                    throw new RuntimeException("Can't create file in path: %s".replace("%s", mozillaPath.getAbsolutePath()));
                }
            } else {
                try {
                    tmpFile = File.createTempFile("webdriver", null, homePath);
                } catch (IOException ex) {
                    throw new RuntimeException("Can't create file in path: %s".replace("%s", homePath.getAbsolutePath()));
                }
            }
            tmpFile.delete();
        }
    }

}