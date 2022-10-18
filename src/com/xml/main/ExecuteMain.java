package com.xml.main;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import com.xml.service.EmployeeService;
import com.xml.util.UtilTransform;
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

		EmployeeService employeeService = EmployeeService.getInstance();

		try {

			UtilTransform.requestTransform();

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
