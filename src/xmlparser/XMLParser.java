package xmlparser;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

import cellsociety.CellSociety;

/**
 * This class handles parsing of the XML files that set up the type of simulation.
 * @author dylanpowers
 *
 */
public class XMLParser {
	private static DocumentBuilder DOCUMENT_BUILDER;
	// message that we want to display when the user picks a file in the incorrect format
	public static final String ERROR_MESSAGE = "XML file does not properly represent %s";
	// name of the root attribute that denotes the type of file we want to parse
	private static String DATA_TYPE_ATTRIBUTE;
	
	public XMLParser(String dataType) {
		DOCUMENT_BUILDER = getDocumentBuilder();
		DATA_TYPE_ATTRIBUTE = dataType;
	}
	
	/**
	 * Get the grid properties for this simulation. This does not include initial probabilities.
	 * @param XMLFile the XMLFile to be read in
	 * @return a Map object containing information about this simulation
	 */
	public Map<String, String> getGridProperties(File XMLFile) {
		Element root = getRootElement(XMLFile);
		// check that we are dealing with a CA file
		if (!isCAFile(root, CellSociety.DATA_TYPE)) {
			throw new XMLException(ERROR_MESSAGE, CellSociety.DATA_TYPE);
		}
		// read data in in the correct order
		Map<String, String> gridProperties = new HashMap<String, String>();
		for (String field : SimulationBuilder.DATA_FIELDS) {
			// this is handled elsewhere since it generates a map of its own
			if (field.equals(SimulationBuilder.INITIAL_DATA_FIELD)) break;
			String value = getTextValue(root, field);
			gridProperties.put(field,  value);
		}
		return gridProperties;
	}
	
	/**
	 * Get the initial states probabilities for all of the cells in the grid.
	 * @param XMLFile the XMLFile to be read in
	 * @return a Map object containing information about the initial states of cells for this simulation
	 */
	public Map<String, double[]> getInitialStates(File XMLFile) {
		Element root = getRootElement(XMLFile);
		Map<String, double[]> initialStatesMap = new HashMap<String, double[]>();
		// check that we are dealing with a CA file
		if (!isCAFile(root, CellSociety.DATA_TYPE)) {
			throw new XMLException(ERROR_MESSAGE, CellSociety.DATA_TYPE);
		}
		// read only the data from the "initialStates" field
		NodeList nList = root.getElementsByTagName(SimulationBuilder.INITIAL_DATA_FIELD);
		for (int i = 0; i < nList.getLength(); i++) {
			initialStatesMap.put(nList.item(i).getAttributes().getNamedItem("type").getNodeValue(), 
					Stream.of(nList.item(i).getTextContent().split("\\s+")).mapToDouble(Double::parseDouble).toArray());
			System.out.println(initialStatesMap.get(nList.item(i).getAttributes().getNamedItem("type").getNodeValue())[0]);
					
		}
		return initialStatesMap;
		
	}
	
	private Element getRootElement(File XMLFile) {
		try {
			DOCUMENT_BUILDER.reset();
			Document XMLDoc = DOCUMENT_BUILDER.parse(XMLFile);
			return XMLDoc.getDocumentElement();
		} catch (SAXException | IOException e) {
			// throw XML exception with same cause
			throw new XMLException(e);
		}
	}
	
	
	/**
	 * Check if the given XML file is valid.
	 * @param root the root of the document 
	 * @param type the type of simulation that we expect
	 * @return true if the XML file contains a valid CA simulation
	 */
	private boolean isCAFile(Element root, String type) {
		return root.getAttribute(DATA_TYPE_ATTRIBUTE).equals(type);
	}
	
	/**
	 * Get the text value contained within an XML element.
	 * @param e the element to be queried
	 * @param tag the tag to be searched
	 * @return the text value of this certain element
	 */
	private String getTextValue(Element e, String tag) {
		NodeList nList = e.getElementsByTagName(tag);
		if (nList != null && nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        } else {
        		throw new XMLException(ERROR_MESSAGE, CellSociety.DATA_TYPE);
        }
	}
	
	// generate the document builder for parsing the document
	private DocumentBuilder getDocumentBuilder() {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// throw XMLException with same cause
			throw new XMLException(e);
		}
	}
}
