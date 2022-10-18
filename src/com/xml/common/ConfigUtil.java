package com.xml.common;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Properties;

/*
* Use to load the data from properties
*
* @throws IOException
* 			-Exception produced by failed or interrupted I/O operations
*
*/

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
