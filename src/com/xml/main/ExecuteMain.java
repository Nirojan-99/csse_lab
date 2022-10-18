package com.xml.main;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.xml.service.EmployeeService;
import com.xml.util.UtilTransform;

public class ExecuteMain {

//	/**
//	 * @param args
//	 */
	public static void main(String[] args) {

		EmployeeService employeeService = new EmployeeService();
		
		try {
			
			UtilTransform.requestTransform();
			employeeService.employeeFromXML();
			employeeService.employeeTableCreate();
			employeeService.addEmployees();
			employeeService.displayEmployee();
			
//			employeeService.eMPLOYEEGETBYID("EMP10004");
//			employeeService.EMPLOYEEDELETE("EMP10001");
			
		} catch (Exception e) {
			//TODO
			System.out.println(e);
		}

	}

}
