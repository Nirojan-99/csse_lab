package com.xml.common;

import java.util.ArrayList;
import com.xml.model.Employee;

public class DisplayUtil {

	public static void displayEmployee(ArrayList<Employee> employeeList) {
		System.out.println("Employee ID" + "\t\t" + "Full Name" + "\t\t" + "Address" + "\t\t" + "Faculty Name" + "\t\t"
				+ "Department" + "\t\t" + "Designation" + "\n");
		System.out.println(
				"================================================================================================================");
		for (Employee employee:employeeList) {

			System.out.println(employee.getEmployeeID() + "\t" + employee.getFullName() + "\t\t" + employee.getAddress() + "\t"
					+ employee.getFacultyName() + "\t" + employee.getDepartment() + "\t" + employee.getDesignation() + "\n");
			System.out.println(
					"----------------------------------------------------------------------------------------------------------------");
		}
	}

}
