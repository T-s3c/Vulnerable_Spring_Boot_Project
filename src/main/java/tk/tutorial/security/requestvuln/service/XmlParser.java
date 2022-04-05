package tk.tutorial.security.requestvuln.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

@Slf4j
@NoArgsConstructor
public class XmlParser {
	public String evalXmlParserImpl(File xmlFile) throws IOException, ParserConfigurationException {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			return doc.getDocumentElement().getTextContent();
		} catch (SAXException e) {
			log.debug(e.toString());
		}
		return null;
	}
}
