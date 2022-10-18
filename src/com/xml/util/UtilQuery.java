package com.xml.util;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactoryConfigurationError;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UtilQuery extends UtilConfig {

	private static final String QUERY_TAG = "query";
	private static final String ATTRIBUTE_ID = "id";

	public static String Query(String id) throws SAXException, IOException, ParserConfigurationException {

		NodeList nodeList;
		Element element = null;

		nodeList = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new File(UtilQuery.properties.getProperty("QUERY_FILE"))).getElementsByTagName(QUERY_TAG);

		for (int x = 0; x < nodeList.getLength(); x++) {

			element = (Element) nodeList.item(x);
			if (element.getAttribute(ATTRIBUTE_ID).equals(id))
				break;

		}

		return element.getTextContent().trim();

	}
}