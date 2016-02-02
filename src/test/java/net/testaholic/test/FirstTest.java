package net.testaholic.test;

import net.testaholic.config.ConfigEnum;
import net.testaholic.config.SeleniumConfig;
import net.testaholic.config.SeleniumConfigProperties;
import org.testng.annotations.Test;

/**
 * Created by wrussell on 2/2/16.
 */
public class FirstTest {

    @Test
    public void testingConfig(){

        SeleniumConfig config = SeleniumConfigProperties.getInstance().getSeleniumConfig();
        if(config.getAwesome().equalsIgnoreCase(ConfigEnum.AWESOME.getValue())){
            System.out.println("lol");
        };
        System.out.println(config.getAwesome());
        if(config.ishouldbeabool()){
            System.out.println("worked...");
        }
    }
}
