package net.testaholic.utils;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.io.*;
import java.util.Properties;

/**
 * Created by wrussell on 2/2/16.
 */
public class PropertiesReader {

    public static Properties loadProperties(String loadProperties) {
        Properties prop = new Properties();
        InputStream in = PropertiesReader.class.getClassLoader().getResourceAsStream(loadProperties);
        try {
            prop.load(in);
            in.close();
        } catch (IOException ioe) {

        }
        return prop;
    }
}
