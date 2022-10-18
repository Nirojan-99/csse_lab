package com.xml.service;

import com.xml.model.Employee;
import com.xml.util.UtilQuery;
import com.xml.util.UtilTransform;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import java.util.Properties;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class EmployeeService {

	private final ArrayList<Employee> employeeList = new ArrayList<Employee>();

	private static Connection connection;

	private static Statement statement;

	private PreparedStatement preparedStatement;

	private static final Logger log = Logger.getLogger(EmployeeService.class.getName());

	private static final String XML_EMP_ID = "XpathEmployeeIDKey";
	private static final String XML_EMP_NAME = "XpathEmployeeNameKey";
	private static final String XML_EMP_ADDRESS = "XpathEmployeeAddressKey";
	private static final String XML_EMP_FACULTY = "XpathFacultyNameKey";
	private static final String XML_EMP_DETP = "XpathDepartmentKey";
	private static final String XML_EMP_DESIGNATION = "XpathDesignationKey";

	private static EmployeeService instance = new EmployeeService();

	private EmployeeService() {
	}

	public static EmployeeService getInstance() {
		return instance;
	}

	public void getEmpService() {

		Properties properties = new Properties();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"),
					properties.getProperty("password"));
		} catch (ClassNotFoundException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (SQLException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		}
	}

	public void employeeFromXML() {

		try {

			int employeeCount = UtilTransform.xmlPaths().size();

			for (int i = 0; i < employeeCount; i++) {

				Map<String, String> singleEmployee = UtilTransform.xmlPaths().get(i);

				Employee employee = new Employee();

				employee.setEmployeeID(singleEmployee.get(XML_EMP_ID));
				employee.setFullName(singleEmployee.get(XML_EMP_NAME));
				employee.setAddress(singleEmployee.get(XML_EMP_ADDRESS));
				employee.setFacultyName(singleEmployee.get(XML_EMP_FACULTY));
				employee.setDepartment(singleEmployee.get(XML_EMP_DETP));
				employee.setDesignation(singleEmployee.get(XML_EMP_DESIGNATION));

				employeeList.add(employee);

				System.out.println(employee.toString() + "\n");

			}
		} catch (Exception exception) {
			log.log(Level.SEVERE, exception.getMessage());
		}
	}

	public void employeeTableCreate() {

		try {
			statement = connection.createStatement();

			try {
				statement.executeUpdate(UtilQuery.Query("q2"));
			} catch (Exception exception) {
				log.log(Level.SEVERE, exception.getMessage());
			}
			statement.executeUpdate(UtilQuery.Query("q1"));

		} catch (SQLException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (SAXException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (IOException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (ParserConfigurationException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		}

	}

	public void addEmployees() {

		try {
			preparedStatement = connection.prepareStatement(UtilQuery.Query("q3"));

			connection.setAutoCommit(false);

			for (int i = 0; i < employeeList.size(); i++) {

				Employee employee = employeeList.get(i);
				preparedStatement.setString(1, employee.getEmployeeID());
				preparedStatement.setString(2, employee.getFullName());
				preparedStatement.setString(3, employee.getAddress());
				preparedStatement.setString(4, employee.getFacultyName());
				preparedStatement.setString(5, employee.getDepartment());
				preparedStatement.setString(6, employee.getDesignation());

				preparedStatement.addBatch();

			}

			preparedStatement.executeBatch();
			connection.commit();

		} catch (SQLException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (SAXException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (IOException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (ParserConfigurationException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		}
	}

	public void getEmployeeByID(String eid) {

		Employee employee = new Employee();

		try {

			preparedStatement = connection.prepareStatement(UtilQuery.Query("q4"));
			preparedStatement.setString(1, eid);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				employee.setEmployeeID(resultSet.getString(1));
				employee.setFullName(resultSet.getString(2));
				employee.setAddress(resultSet.getString(3));
				employee.setFacultyName(resultSet.getString(4));
				employee.setDepartment(resultSet.getString(5));
				employee.setDesignation(resultSet.getString(6));
			}

			ArrayList<Employee> employeeList = new ArrayList<Employee>();
			employeeList.add(employee);

			outputEmployee(employeeList);

		} catch (SQLException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (SAXException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (ParserConfigurationException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (IOException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		}
	}

	public void deleteEmployee(String eid) {

		try {

			preparedStatement = connection.prepareStatement(UtilQuery.Query("q6"));
			preparedStatement.setString(1, eid);
			preparedStatement.executeUpdate();

		} catch (SQLException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (SAXException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (IOException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (ParserConfigurationException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		}
	}

	public void displayEmployee() {

		ArrayList<Employee> employeeList = new ArrayList<Employee>();

		try {
			preparedStatement = connection.prepareStatement(UtilQuery.Query("q5"));
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Employee employee = new Employee();

				employee.setEmployeeID(resultSet.getString(1));
				employee.setFullName(resultSet.getString(2));
				employee.setAddress(resultSet.getString(3));
				employee.setFacultyName(resultSet.getString(4));
				employee.setDepartment(resultSet.getString(5));
				employee.setDesignation(resultSet.getString(6));

				employeeList.add(employee);
			}
		} catch (SQLException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (SAXException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (IOException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (ParserConfigurationException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		}

		outputEmployee(employeeList);
	}

	public void outputEmployee(ArrayList<Employee> employeeList) {

		System.out.println("Employee ID" + "\t\t" + "Full Name" + "\t\t" + "Address" + "\t\t" + "Faculty Name" + "\t\t"
				+ "Department" + "\t\t" + "Designation" + "\n");
		System.out.println(
				"================================================================================================================");
		for (int i = 0; i < employeeList.size(); i++) {

			Employee e = employeeList.get(i);
			System.out.println(e.getEmployeeID() + "\t" + e.getFullName() + "\t\t" + e.getAddress() + "\t"
					+ e.getFacultyName() + "\t" + e.getDepartment() + "\t" + e.getDesignation() + "\n");
			System.out.println(
					"----------------------------------------------------------------------------------------------------------------");
		}

	}

	/*
	 * Template method to get employee from XML and store it in database and display
	 */
	public void retriveEmployee() {
		this.employeeFromXML();
		this.employeeTableCreate();
		this.addEmployees();
		this.displayEmployee();
	}
}
