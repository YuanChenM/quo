/**
 * ConfigManager.java
 * 
 * @screen core
 * 
 */
package com.quotation.core.util;

import com.quotation.core.bean.MailConfig;

import java.util.Properties;

/**
 * The configuration files util.
 * 
 * 
 */
public class ConfigUtil {

    /** the configurations */
    private static Properties configs;

    private static MailConfig mailConfig;

    static {
        configs = new Properties();
        mailConfig = new MailConfig();
    }

    /**
     * Get all configurations.
     * 
     * @return all configurations
     */
    public static Properties get() {
        return configs;
    }

    /**
     * Get the configuration value.
     * 
     * @param key the configuration key
     * @return the configuration value
     */
    public static String get(String key) {
        return configs.getProperty(key, "");
    }

    /**
     * Add the configuration value.
     * 
     * @param key the configuration key
     * @param value the configuration value
     */
    public static void add(String key, String value) {
        configs.setProperty(key, value);
    }
    
    /**
     * Get Mail configurations.
     * 
     * @return Mail configurations
     */
    public static MailConfig getMailConfig() {
        return mailConfig;
    }
}
