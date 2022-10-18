package com.xml.common;

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

public class XSLTransformUtil extends ConfigUtil {

	private static final ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

	private static Map<String, String> xpathOutputMap = null;

	public static void requestTransform()
			throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError {

		Source requestXML = new StreamSource(
				new File(ConfigUtil.properties.getProperty(CommonConstants.REQUEST_XML_PATH)));
		Source modifiedXSL = new StreamSource(
				new File(ConfigUtil.properties.getProperty(CommonConstants.MODIFIED_XSL_PATH)));
		Result responseXML = new StreamResult(
				new File(ConfigUtil.properties.getProperty(CommonConstants.RESPONSE_XML_PATH)));

		TransformerFactory.newInstance().newTransformer(modifiedXSL).transform(requestXML, responseXML);
	}

	public static ArrayList<Map<String, String>> xmlPaths() throws SAXException, IOException,
			ParserConfigurationException, NumberFormatException, XPathExpressionException {

		Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(ConfigUtil.properties.getProperty(CommonConstants.RESPONSE_XML_PATH));

		XPath x = XPathFactory.newInstance().newXPath();
		
		int count = Integer.parseInt((String) x.compile("count(//Employees/Employee)").evaluate(d, XPathConstants.STRING));
		
		for (int i = 0; i < count; i++) {
			
			xpathOutputMap = new HashMap<String, String>();
			
			xpathOutputMap.put(CommonConstants.XPATH_EMP_ID_KEY, (String) x.compile("//Employees/Employee[" + i + "]/EmployeeID/text()")
					.evaluate(d, XPathConstants.STRING));
			xpathOutputMap.put(CommonConstants.XPATH_EMP_NAME_KEY,
					(String) x.compile("//Employees/Employee[" + i + "]/EmployeeFullName/text()").evaluate(d,
							XPathConstants.STRING));
			xpathOutputMap.put(CommonConstants.XPATH_EMP_ADDRESS_KEY,
					(String) x.compile("//Employees/Employee[" + i + "]/EmployeeFullAddress/text()").evaluate(d,
							XPathConstants.STRING));
			xpathOutputMap.put(CommonConstants.XPATH_EMP_FACULTY_KEY, (String) x
					.compile("//Employees/Employee[" + i + "]/FacultyName/text()").evaluate(d, XPathConstants.STRING));
			xpathOutputMap.put(CommonConstants.XPATH_EMP_DETP_KEY, (String) x
					.compile("//Employees/Employee[" + i + "]/Department/text()").evaluate(d, XPathConstants.STRING));
			xpathOutputMap.put(CommonConstants.XPATH_EMP_DESIGNATION_KEY, (String) x
					.compile("//Employees/Employee[" + i + "]/Designation/text()").evaluate(d, XPathConstants.STRING));
			list.add(xpathOutputMap);
		}
		return list;
	}
}
