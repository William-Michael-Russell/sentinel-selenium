package net.testaholic.config;

import net.testaholic.utils.PropertiesReader;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

/**
 * Created by wrussell on 2/2/16.
 */
public class SeleniumConfigProperties {

    private static SeleniumConfigProperties _INSTANCE;
    private static SeleniumConfig seleniumConfig;

    private SeleniumConfigProperties() {

    }

    public SeleniumConfig getSeleniumConfig(){
        return seleniumConfig;
    }

    public static SeleniumConfigProperties getInstance() {
        if (_INSTANCE == null) {
            _INSTANCE = new SeleniumConfigProperties();
            //only load properties once.
            seleniumConfig = loadSeleniumProperties();
        }
        return _INSTANCE;
    }

    private static SeleniumConfig loadSeleniumProperties() {
        Mapper mapper = new DozerBeanMapper();
        return mapper.map(PropertiesReader.loadProperties("selenium.properties"), SeleniumConfig.class);
    }
}
