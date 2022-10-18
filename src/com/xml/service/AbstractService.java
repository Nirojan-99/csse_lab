package com.xml.service;


public abstract class AbstractService {
	
	abstract void loadEmployeeFromXML();
	
	abstract void employeeTableCreate();
	
	abstract void addEmployees();	
	
	abstract void displayEmployee();	

	public final void retriveEmployee() {
		this.loadEmployeeFromXML();
		this.employeeTableCreate();
		this.addEmployees();
		this.displayEmployee();
	}

}
