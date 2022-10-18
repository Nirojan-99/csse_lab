package com.xml.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Properties;

public class UtilConfig {

	public static final Properties properties = new Properties();
	public static final Logger log = Logger.getLogger(UtilConfig.class.getName());

	static {

		try {
			properties.load(UtilQuery.class.getResourceAsStream("../config/config.properties"));

		} catch (IOException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		}
	}
}
