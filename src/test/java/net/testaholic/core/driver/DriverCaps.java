package net.testaholic.core.driver;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by williamrussell on 2/19/16.
 */
public interface DriverCaps {
        DesiredCapabilities getDesiredCapabilities();
        DriverProvider getDriverProvider();

}
