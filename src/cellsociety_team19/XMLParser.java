package cellsociety_team19;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

/**
 * This class handles parsing of the XML files that set up the type of simulation.
 * @author dylanpowers
 *
 */
public class XMLParser {

	private static DocumentBuilder DOCUMENT_BUILDER;
	
	public XMLParser() {
		DOCUMENT_BUILDER = getDocumentBuilder();
	}
	
	private Element getRootElement(File XMLFile) {
		try {
			DOCUMENT_BUILDER.reset();
			Document XMLDoc = DOCUMENT_BUILDER.parse(XMLFile);
			return XMLDoc.getDocumentElement();
		}
		catch (IOException e) {
			// TODO implement better exception here
			
		}
	}
	
	private DocumentBuilder getDocumentBuilder() {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO create an exception
			return null;
		}
	}
}
