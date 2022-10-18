package com.xml.service;

import com.xml.common.CommonConstants;
import com.xml.common.DBConnectionUtil;
import com.xml.common.DisplayUtil;
import com.xml.common.QueryUtil;
import com.xml.common.XSLTransformUtil;
import com.xml.model.Employee;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class EmployeeServiceImpl extends AbstractService {

	private final ArrayList<Employee> employeeList = new ArrayList<Employee>();

	private static Connection connection;

	private static Statement statement;

	private static final Logger log = Logger.getLogger(EmployeeServiceImpl.class.getName());

	private static EmployeeServiceImpl instance = new EmployeeServiceImpl();
	
	private PreparedStatement preparedStatement;

	private EmployeeServiceImpl() {

	}

	public static EmployeeServiceImpl getInstance() {
		return instance;
	}

	public void getEmpService() {
		try {
			DBConnectionUtil.connectDB(connection);
		} catch (ClassNotFoundException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (SQLException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		}

	}

	public void employeeFromXML() {

		try {

			int employeeCount = XSLTransformUtil.xmlPaths().size();

			for (int i = 0; i < employeeCount; i++) {

				Map<String, String> singleEmployee = XSLTransformUtil.xmlPaths().get(i);

				Employee employee = new Employee();

				employee.setEmployeeID(singleEmployee.get(CommonConstants.XML_EMP_ID));
				employee.setFullName(singleEmployee.get(CommonConstants.XML_EMP_NAME));
				employee.setAddress(singleEmployee.get(CommonConstants.XML_EMP_ADDRESS));
				employee.setFacultyName(singleEmployee.get(CommonConstants.XML_EMP_FACULTY));
				employee.setDepartment(singleEmployee.get(CommonConstants.XML_EMP_DETP));
				employee.setDesignation(singleEmployee.get(CommonConstants.XML_EMP_DESIGNATION));

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
				statement.executeUpdate(QueryUtil.Query(CommonConstants.QUERY_TWO));
			} catch (Exception exception) {
				log.log(Level.SEVERE, exception.getMessage());
			}
			statement.executeUpdate(QueryUtil.Query(CommonConstants.QUERY_ONE));

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
			preparedStatement = connection.prepareStatement(QueryUtil.Query(CommonConstants.QUERY_THREE));

			connection.setAutoCommit(false);

			for (int i = 0; i < employeeList.size(); i++) {

				Employee employee = employeeList.get(i);
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, employee.getEmployeeID());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_TWO, employee.getFullName());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_THREE, employee.getAddress());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_FOUR, employee.getFacultyName());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_FIVE, employee.getDepartment());
				preparedStatement.setString(CommonConstants.COLUMN_INDEX_SIX, employee.getDesignation());

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

			preparedStatement = connection.prepareStatement(QueryUtil.Query(CommonConstants.QUERY_FOUR));
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, eid);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				employee.setEmployeeID(resultSet.getString(CommonConstants.COLUMN_INDEX_ONE));
				employee.setFullName(resultSet.getString(CommonConstants.COLUMN_INDEX_TWO));
				employee.setAddress(resultSet.getString(CommonConstants.COLUMN_INDEX_THREE));
				employee.setFacultyName(resultSet.getString(CommonConstants.COLUMN_INDEX_FOUR));
				employee.setDepartment(resultSet.getString(CommonConstants.COLUMN_INDEX_FIVE));
				employee.setDesignation(resultSet.getString(CommonConstants.COLUMN_INDEX_SIX));
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

			preparedStatement = connection.prepareStatement(QueryUtil.Query(CommonConstants.QUERY_SIX));
			preparedStatement.setString(CommonConstants.COLUMN_INDEX_ONE, eid);
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
			preparedStatement = connection.prepareStatement(QueryUtil.Query(CommonConstants.QUERY_FIVE));
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Employee employee = new Employee();

				employee.setEmployeeID(resultSet.getString(CommonConstants.COLUMN_INDEX_ONE));
				employee.setFullName(resultSet.getString(CommonConstants.COLUMN_INDEX_TWO));
				employee.setAddress(resultSet.getString(CommonConstants.COLUMN_INDEX_THREE));
				employee.setFacultyName(resultSet.getString(CommonConstants.COLUMN_INDEX_FOUR));
				employee.setDepartment(resultSet.getString(CommonConstants.COLUMN_INDEX_FIVE));
				employee.setDesignation(resultSet.getString(CommonConstants.COLUMN_INDEX_SIX));

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
		DisplayUtil.displayEmployee(employeeList);
	}

}
