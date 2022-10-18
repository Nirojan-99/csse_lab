package com.xml.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class UtilTransform extends UtilConfig {

	private static final ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

	private static Map<String, String> map = null;

	private static final String REQUEST_XML_PATH = UtilConfig.properties.getProperty("REQUEST_XML_PATH");
	private static final String MODIFIED_XSL_PATH = UtilConfig.properties.getProperty("MODIFIED_XSL_PATH");
	private static final String RESPONSE_XML_PATH = UtilConfig.properties.getProperty("RESPONSE_XML_PATH");

	public static void requestTransform()
			throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError {

		Source requestXML = new StreamSource(new File(REQUEST_XML_PATH));
		Source modifiedXSL = new StreamSource(new File(MODIFIED_XSL_PATH));
		Result responseXML = new StreamResult(new File(RESPONSE_XML_PATH));

		TransformerFactory.newInstance().newTransformer(modifiedXSL).transform(requestXML, responseXML);
	}

	public static ArrayList<Map<String, String>> xmlPaths() throws SAXException, IOException,
			ParserConfigurationException, NumberFormatException, XPathExpressionException {

		Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(RESPONSE_XML_PATH);

		XPath x = XPathFactory.newInstance().newXPath();
		int n = Integer.parseInt((String) x.compile("count(//Employees/Employee)").evaluate(d, XPathConstants.STRING));
		for (int i = 1; i <= n; i++) {
			map = new HashMap<String, String>();
			map.put("XpathEmployeeIDKey", (String) x.compile("//Employees/Employee[" + i + "]/EmployeeID/text()")
					.evaluate(d, XPathConstants.STRING));
			map.put("XpathEmployeeNameKey",
					(String) x.compile("//Employees/Employee[" + i + "]/EmployeeFullName/text()").evaluate(d,
							XPathConstants.STRING));
			map.put("XpathEmployeeAddressKey",
					(String) x.compile("//Employees/Employee[" + i + "]/EmployeeFullAddress/text()").evaluate(d,
							XPathConstants.STRING));
			map.put("XpathFacultyNameKey", (String) x.compile("//Employees/Employee[" + i + "]/FacultyName/text()")
					.evaluate(d, XPathConstants.STRING));
			map.put("XpathDepartmentKey", (String) x.compile("//Employees/Employee[" + i + "]/Department/text()")
					.evaluate(d, XPathConstants.STRING));
			map.put("XpathDesignationKey", (String) x.compile("//Employees/Employee[" + i + "]/Designation/text()")
					.evaluate(d, XPathConstants.STRING));
			list.add(map);
		}
		return list;
	}
}
