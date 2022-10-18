package com.xml.common;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Properties;

public class ConfigUtil {

	public static final Properties properties = new Properties();
	public static final Logger log = Logger.getLogger(ConfigUtil.class.getName());

	static {

		try {
			properties.load(QueryUtil.class.getResourceAsStream(CommonConstants.PROPERTY_FILE));

		} catch (IOException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		}
	}
}
