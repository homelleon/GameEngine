package core.settings.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.settings.GameSettings;
import tool.xml.XMLUtils;
import tool.xml.parser.ObjectParserInterface;
import tool.xml.parser.XMLParser;

public class SettingsXMLParser extends XMLParser implements ObjectParserInterface<GameSettings> {

	public SettingsXMLParser(Document document) {
		super(document);
	}

	@Override
	public GameSettings parse() {
		GameSettings settings = GameSettings.getInstance();
		NodeList nodeList = this.document.getDocumentElement().getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (XMLUtils.ifNodeIsElement(node, XMLUtils.MAP)) {
				readMapSettings(node, settings);
			} else if (XMLUtils.ifNodeIsElement(node, XMLUtils.OBJECT_MAP)) {
				readObjectMapSettings(node, settings);
			}
		}
		return settings;
	}

	private void readMapSettings(Node node, GameSettings settings) {
		Element map = (Element) node;
		String name = XMLUtils.getTagValue(map, XMLUtils.NAME);
		settings.setMapName(name);
	}

	private void readObjectMapSettings(Node node, GameSettings settings) {
		Element objectMap = (Element) node;
		String name = XMLUtils.getTagValue(objectMap, XMLUtils.NAME);
		settings.setObjectMapName(name);
	}

}
