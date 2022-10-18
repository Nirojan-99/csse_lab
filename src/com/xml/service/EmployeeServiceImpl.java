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
import javax.xml.xpath.XPathExpressionException;

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
			connection = DBConnectionUtil.connectDB();
			
			
		} catch (ClassNotFoundException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (SQLException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		}

	}

	public void loadEmployeeFromXML() {

		try {

			for (Map<String, String> employeeOutputMap : XSLTransformUtil.xmlPaths()) {

				Employee employee = new Employee();

				employee.setEmployeeID(employeeOutputMap.get(CommonConstants.XPATH_EMP_ID_KEY));
				employee.setFullName(employeeOutputMap.get(CommonConstants.XPATH_EMP_NAME_KEY));
				employee.setAddress(employeeOutputMap.get(CommonConstants.XPATH_EMP_ADDRESS_KEY));
				employee.setFacultyName(employeeOutputMap.get(CommonConstants.XPATH_EMP_FACULTY_KEY));
				employee.setDepartment(employeeOutputMap.get(CommonConstants.XPATH_EMP_DETP_KEY));
				employee.setDesignation(employeeOutputMap.get(CommonConstants.XPATH_EMP_DESIGNATION_KEY));

				employeeList.add(employee);

				log.info(employee.toString() + "\n");

			}
		} catch (NumberFormatException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (XPathExpressionException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (SAXException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (IOException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (ParserConfigurationException exception) {
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

			for (Employee employee : employeeList) {

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
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				log.log(Level.SEVERE, e.getMessage());
			}
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
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				log.log(Level.SEVERE, e.getMessage());
			}
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
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				log.log(Level.SEVERE, e.getMessage());
			}
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
