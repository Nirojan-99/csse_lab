package com.xml.common;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
* Use to perform query connection
*
* @throws  SAXException
* 				-Encapsulate a general SAX error or warning
* @throws IOException
* 				-Exception produced by failed or interrupted I/O operations
*
* @throws ParserConfigurationException
* 				-Indicate a serious configuration error
*
*/

public class QueryUtil extends ConfigUtil {

	public static String Query(String id) throws SAXException, IOException, ParserConfigurationException {

		NodeList nodeList;
		Element element = null;

		nodeList = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new File(QueryUtil.properties.getProperty(CommonConstants.QUERY_FILE_PATH)))
				.getElementsByTagName(CommonConstants.TAG_NAME);

		for (int x = 0; x < nodeList.getLength(); x++) {

			element = (Element) nodeList.item(x);
			if (element.getAttribute(CommonConstants.ATTRIBUTE_ID).equals(id))
				break;

		}

		return element.getTextContent().trim();

	}
}