package com.xml.main;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.xml.common.XSLTransformUtil;
import com.xml.service.EmployeeServiceImpl;

import java.util.logging.Logger;
import java.util.logging.Level;

/*
* ExecuteMain class
*
* entry point of the application
*
*/

public class ExecuteMain {

	/**
	 * @param args
	 */
	public static final Logger log = Logger.getLogger(ExecuteMain.class.getName());

	public static void main(String[] args) {

		EmployeeServiceImpl employeeService = EmployeeServiceImpl.getInstance();

		try {

			XSLTransformUtil.requestTransform();

			employeeService.retriveEmployee();

			employeeService.getEmployeeByID("EMP10004");
			employeeService.deleteEmployee("EMP10001");

		} catch (TransformerException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (TransformerFactoryConfigurationError exception) {
			log.log(Level.SEVERE, exception.getMessage());
		}

	}

}
