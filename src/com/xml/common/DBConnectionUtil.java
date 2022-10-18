package com.xml.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionUtil {

	public static Connection connectDB() throws ClassNotFoundException, SQLException {
		Properties properties = new Properties();

		Class.forName(ConfigUtil.properties.getProperty(CommonConstants.DRIVER_NAME));
		return DriverManager.getConnection(properties.getProperty(CommonConstants.URL),
				properties.getProperty(CommonConstants.USERNAME), properties.getProperty(CommonConstants.PASSWORD));

	}

}
