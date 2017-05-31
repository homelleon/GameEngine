package objects.gui.guiTexts;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import toolbox.XMLUtils;

public class TextXMLParser implements TextParserInterface {

	@Override
	public String readText(Document document) {
		
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
