package com.xml.service;

import org.xml.sax.SAXException;

import com.xml.model.Employee;
import com.xml.util.UtilC;
import com.xml.util.UtilQ;
import com.xml.util.UtilTRANSFORM;
import com.xml.util.UtilQ;
import com.xml.util.UtilTRANSFORM;

import java.sql.Connection;
import java.util.logging.Logger;
import java.sql.DriverManager;
import javax.xml.parsers.ParserConfigurationException;
import java.sql.PreparedStatement;
import javax.xml.xpath.XPathExpressionException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.sql.Statement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

public class EmployeeService {

	private final ArrayList<Employee> employeeList = new ArrayList<Employee>();

	private static Connection connection;

	private static Statement statement;

	private PreparedStatement preparedStatement;

	public void getEmpService() {

		Properties properties = new Properties();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"),
					properties.getProperty("password"));
		} catch (Exception e) {
			// TODO
		}
	}

	public void employeeFromXML() {

		try {

			int employeeCount = UtilTRANSFORM.XMLXPATHS().size();

			for (int i = 0; i < employeeCount; i++) {

				Map<String, String> singleEmployee = UtilTRANSFORM.XMLXPATHS().get(i);

				Employee employee = new Employee();

				employee.setEmployeeID(singleEmployee.get("XpathEmployeeIDKey"));
				employee.setFullName(singleEmployee.get("XpathEmployeeNameKey"));
				employee.setAddress(singleEmployee.get("XpathEmployeeAddressKey"));
				employee.setFacultyName(singleEmployee.get("XpathFacultyNameKey"));
				employee.setDepartment(singleEmployee.get("XpathDepartmentKey"));
				employee.setDesignation(singleEmployee.get("XpathDesignationKey"));

				employeeList.add(employee);

				System.out.println(employee.toString() + "\n");

			}
		} catch (Exception e) {
			// TODO
		}
	}

	public void employeeTableCreate() {

		try {
			statement = connection.createStatement();

			try {
				statement.executeUpdate(UtilQ.Q("q2"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			statement.executeUpdate(UtilQ.Q("q1"));

		} catch (SQLException exception) {
			// TODO
		} catch (SAXException e) {
			// TODO
		} catch (IOException e) {
			// TODO
		} catch (ParserConfigurationException e) {
			// TODO
		}

	}

	public void addEmployees() {

		try {
			preparedStatement = connection.prepareStatement(UtilQ.Q("q3"));

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

		} catch (SQLException | SAXException | IOException | ParserConfigurationException exception) {
			// TODO
		}
	}

	public void getEmployeeByID(String eid) {

		Employee employee = new Employee();

		try {

			preparedStatement = connection.prepareStatement(UtilQ.Q("q4"));
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

		} catch (SQLException | SAXException | IOException | ParserConfigurationException exception) {
			// TODO
		}
	}

	public void deleteEmployee(String eid) {

		try {

			preparedStatement = connection.prepareStatement(UtilQ.Q("q6"));
			preparedStatement.setString(1, eid);
			preparedStatement.executeUpdate();

		} catch (SQLException | SAXException | IOException | ParserConfigurationException exception) {
//			e.printStackTrace();
			// TODO
		}
	}

	public void displayEmployee() {

		ArrayList<Employee> employeeList = new ArrayList<Employee>();

		try {
			preparedStatement = connection.prepareStatement(UtilQ.Q("q5"));
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
		} catch (SQLException | SAXException | IOException | ParserConfigurationException exception) {
			// TODO
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
}
