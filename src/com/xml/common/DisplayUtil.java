package com.xml.common;

import java.util.ArrayList;
import com.xml.model.Employee;

/*
* DisplayUtil class used to print employee  data 
*/

public class DisplayUtil {

	/**
	 * This method PRINT all employee details which are in provided employeeList
	 * 
	 * @param employeeList ArrayList<Employee> Array of employee list to print
	 */

	public static void displayEmployee(ArrayList<Employee> employeeList) {
		System.out.println("Employee ID" + "\t\t" + "Full Name" + "\t\t" + "Address" + "\t\t" + "Faculty Name" + "\t\t"
				+ "Department" + "\t\t" + "Designation" + "\n");
		System.out.println(
				"================================================================================================================");
		for (Employee employee : employeeList) {

			System.out.println(employee.getEmployeeID() + "\t" + employee.getFullName() + "\t\t" + employee.getAddress()
					+ "\t" + employee.getFacultyName() + "\t" + employee.getDepartment() + "\t"
					+ employee.getDesignation() + "\n");
			System.out.println(
					"----------------------------------------------------------------------------------------------------------------");
		}
	}

}
