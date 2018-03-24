package object.gui.text;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import tool.xml.XMLUtils;
import tool.xml.parser.IObjectParser;
import tool.xml.parser.XMLParser;

public class TextXMLParser extends XMLParser implements IObjectParser<String> {

	public TextXMLParser(Document document) {
		super(document);
	}

	@Override
	public String parse() {

		NodeList nodeList = this.document.getDocumentElement().getChildNodes();
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
