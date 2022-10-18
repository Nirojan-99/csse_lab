package com.xml.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.Properties;


public class UtilC {

	public static final Properties properties = new Properties();

	static {
		
		try {
			
			properties.load(UtilQ.class.getResourceAsStream("../config/config.properties"));
			
		} catch (IOException e) {
			//TODO
		}
	}
}

