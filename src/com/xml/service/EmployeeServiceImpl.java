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

/*
* EmployeeServiceImpl class
* This class for implement  GET EmpService, CREATE employee table, ADD employee, GET employee, DELETE employee methods
* 
*
*/

public class EmployeeServiceImpl extends AbstractService {

	private final ArrayList<Employee> employeeList = new ArrayList<Employee>();

	private static Connection connection;

	private static Statement statement;

	private final Logger log = Logger.getLogger(EmployeeServiceImpl.class.getName());

	private static EmployeeServiceImpl instance = new EmployeeServiceImpl();

	private PreparedStatement preparedStatement;

	private EmployeeServiceImpl() {

	}

	/**
	 * This method to get instance of this class
	 * 
	 * @return EmployeeService instance
	 */

	public static EmployeeServiceImpl getInstance() {
		return instance;
	}

	/**
	 * This method to get connection for database
	 * 
	 * @throws SQLException           -Thrown when database access error occurs or
	 *                                this method is called on a closed connection
	 * @throws ClassNotFoundException -Thrown when an application tries to load in a
	 *                                class through its string name using
	 */

	public void getEmployeeService() {
		try {
			connection = DBConnectionUtil.connectDB();

		} catch (ClassNotFoundException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		} catch (SQLException exception) {
			log.log(Level.SEVERE, exception.getMessage());
		}

	}

	/**
	 * This read xml file and load data into ArrayList of Employee object
	 * 
	 * @throws NumberFormatException        -Thrown to indicate that the application
	 *                                      has attempted to convert a string to one
	 *                                      of the numeric types
	 * @throws XPathExpressionException     -indicate an error in an XPath
	 *                                      expression.
	 * @throws SQLException                 -Thrown when database access error
	 *                                      occurs or this method is called on a
	 *                                      closed connection
	 * @throws SAXException                 -Encapsulate a general SAX error or
	 *                                      warning
	 * @throws IOException                  -Exception produced by failed or
	 *                                      interrupted I/O operations
	 *
	 * @throws ParserConfigurationException -Indicate a serious configuration error
	 */

	@Override
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

	/**
	 * This method create employee table in database
	 * 
	 * @throws SQLException                 -Thrown when database access error
	 *                                      occurs or this method is called on a
	 *                                      closed connection
	 * @throws SAXException                 -Encapsulate a general SAX error or
	 *                                      warning
	 * @throws IOException                  -Exception produced by failed or
	 *                                      interrupted I/O operations
	 *
	 * @throws ParserConfigurationException -Indicate a serious configuration error
	 */

	@Override
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

	/**
	 * This method ADD employee
	 * 
	 * @throws SQLException                 -Thrown when database access error
	 *                                      occurs or this method is called on a
	 *                                      closed connection
	 * @throws SAXException                 -Encapsulate a general SAX error or
	 *                                      warning
	 * @throws IOException                  -Exception produced by failed or
	 *                                      interrupted I/O operations
	 *
	 * @throws ParserConfigurationException -Indicate a serious configuration error
	 */

	@Override
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

	/**
	 * This method GET a employee details
	 * 
	 * @param eid ID of employee to get details
	 * @throws SQLException                 -Thrown when database access error
	 *                                      occurs or this method is called on a
	 *                                      closed connection
	 * @throws SAXException                 -Encapsulate a general SAX error or
	 *                                      warning
	 * @throws IOException                  -Exception produced by failed or
	 *                                      interrupted I/O operations
	 *
	 * @throws ParserConfigurationException -Indicate a serious configuration error
	 */

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

	/**
	 * This method DELETE a employee
	 * 
	 * @param eid ID of employee to delete
	 * @throws SQLException                 -Thrown when database access error
	 *                                      occurs or this method is called on a
	 *                                      closed connection
	 * @throws SAXException                 -Encapsulate a general SAX error or
	 *                                      warning
	 * @throws IOException                  -Exception produced by failed or
	 *                                      interrupted I/O operations
	 *
	 * @throws ParserConfigurationException -Indicate a serious configuration error
	 */

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

	/**
	 * This method DISPLAY all employees
	 * 
	 * @throws SQLException                 -Thrown when database access error
	 *                                      occurs or this method is called on a
	 *                                      closed connection
	 * @throws SAXException                 -Encapsulate a general SAX error or
	 *                                      warning
	 * @throws IOException                  -Exception produced by failed or
	 *                                      interrupted I/O operations
	 *
	 * @throws ParserConfigurationException -Indicate a serious configuration error
	 */

	@Override
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

	/**
	 * This method PRINT all employee details
	 * 
	 * @param employeeList ArrayList<Employee> Array of employee list to print
	 */

	public void outputEmployee(ArrayList<Employee> employeeList) {
		DisplayUtil.displayEmployee(employeeList);
	}

}
