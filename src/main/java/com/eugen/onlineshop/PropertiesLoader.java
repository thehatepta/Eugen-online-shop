package com.eugen.onlineshop;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public Properties load(String path) {
        Properties properties = new Properties();
        try (InputStream is = PropertiesLoader.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                return properties;
            }
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Can't load properties file: " + path, e);
        }
        return properties;
    }

}
