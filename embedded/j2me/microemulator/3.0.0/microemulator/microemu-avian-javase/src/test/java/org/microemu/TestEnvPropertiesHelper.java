package org.microemu;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.microemu.app.Config;

public class TestEnvPropertiesHelper {

	static final Properties utProperties = TestEnvPropertiesHelper.loadProperties(); 
	
	static final String FILE_NAME = "tests.properties";
	
	/**
	 * File  ${home}/.microemulator/test.properties is used for configuration
	 * @return
	 */
	public static String getProperty(String key, String defaultValue) {
		return TestEnvPropertiesHelper.utProperties.getProperty(key, defaultValue);
	}

	private static Properties loadProperties() {
		Properties prop = new Properties();
		
		File meHomeRoot = Config.getConfigPath();
		if (meHomeRoot == null) {
			return prop;
		}
		
		File file = new File(meHomeRoot, FILE_NAME);
		if (!file.exists() || !file.canRead()) {
			return prop;
		}
		
		FileInputStream input;
        try {
            input = new FileInputStream(file);
            prop.load((InputStream) input);
        } catch (Exception e) {
            System.err.println("Error reading properties " + e.toString());
        }
		return prop;
	}
}
