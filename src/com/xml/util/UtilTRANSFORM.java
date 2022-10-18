package com.xml.util;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathFactory;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import org.w3c.dom.Document;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;

public class UtilTransform extends UtilConfig {

	private static final ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

	private static Map<String, String> map = null;

	private static final String REQUEST_XML_PATH = "src/com/xml/source/EmployeeRequest.xml";
	private static final String MODIFIED_XSL_PATH = "src/com/xml/source/Employee-modified.xsl";
	private static final String RESPONSE_XML_PATH = "src/com/xml/source/EmployeeResponse.xml";

	public static void requestTransform() throws Exception {

		Source requestXML = new StreamSource(new File(REQUEST_XML_PATH));
		Source modifiedXSL = new StreamSource(new File(MODIFIED_XSL_PATH));
		Result responseXML = new StreamResult(new File(RESPONSE_XML_PATH));

		TransformerFactory.newInstance().newTransformer(modifiedXSL).transform(requestXML, responseXML);
	}

	public static ArrayList<Map<String, String>> xmlPaths() throws Exception {

		Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse("src/com/xml/source/EmployeeResponse.xml");

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
