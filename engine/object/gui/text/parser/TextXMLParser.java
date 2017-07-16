package object.gui.text.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import tool.xml.XMLUtils;

public class TextXMLParser implements TextParserInterface {
	
	private Document document;
	
	public TextXMLParser(Document document) {
		this.document = document;
	}

	@Override
	public String parse() {
		
		NodeList nodeList = document.getDocumentElement().getChildNodes(); 
		String text = "";
		for (int i = 0; i < nodeList.getLength(); i++) {
	           Node node = nodeList.item(i);
	           if (XMLUtils.ifNodeIsElement(node, XMLUtils.PARAGRAPH)) {
	        	   String paragraph = node.getChildNodes().item(0).getNodeValue();
	        	   text += paragraph;
	           }	        	
	        }
		return text;
	}

}
