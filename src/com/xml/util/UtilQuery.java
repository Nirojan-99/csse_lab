package com.xml.util;

import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.TransformerException;
import java.io.File;
import org.xml.sax.SAXException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import javax.xml.transform.TransformerConfigurationException;

public class UtilQuery extends UtilConfig {

	private static final String QUERY_FILE = "src/com/xml/source/EmployeeQuery.xml";
	private static final String QUERY_TAG = "query";
	private static final String ATTRIBUTE_ID = "id";

	public static String Q(String id) throws SAXException, IOException, ParserConfigurationException {

		NodeList nodeList;
		Element element = null;

		nodeList = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(QUERY_FILE))
				.getElementsByTagName(QUERY_TAG);

		for (int x = 0; x < nodeList.getLength(); x++) {
			
			element = (Element) nodeList.item(x);
			if (element.getAttribute(ATTRIBUTE_ID).equals(id))
				break;
			
		}
		
		return element.getTextContent().trim();
	}
}