package com.xml.service;


public abstract class AbstractService {
	
	abstract void employeeFromXML();
	
	abstract void employeeTableCreate();
	
	abstract void addEmployees();	
	
	abstract void displayEmployee();	

	public final void retriveEmployee() {
		this.employeeFromXML();
		this.employeeTableCreate();
		this.addEmployees();
		this.displayEmployee();
	}

}
