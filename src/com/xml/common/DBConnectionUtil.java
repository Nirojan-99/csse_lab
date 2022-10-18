package com.xml.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

public class DBConnectionUtil {

	public static void connectDB(Connection connection) throws ClassNotFoundException, SQLException {
		Properties properties = new Properties();

		Class.forName(ConfigUtil.properties.getProperty(CommonConstants.DRIVER_URL));
		connection = DriverManager.getConnection(properties.getProperty(CommonConstants.URL),
				properties.getProperty(CommonConstants.USERNAME), properties.getProperty(CommonConstants.PASSWORD));

	}

}
