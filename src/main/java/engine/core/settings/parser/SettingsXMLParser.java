package core.settings.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.settings.GameSettings;
import tool.xml.XMLUtils;
import tool.xml.parser.IObjectParser;
import tool.xml.parser.XMLParser;

public class SettingsXMLParser extends XMLParser implements IObjectParser<GameSettings> {

	public SettingsXMLParser(Document document) {
		super(document);
	}

	@Override
	public GameSettings parse() {
		GameSettings settings = GameSettings.getInstance();
		NodeList nodeList = this.document.getDocumentElement().getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(XMLUtils.ifNodeIsElement(node, XMLUtils.MAPS)) {
				NodeList settingsNodeList = node.getChildNodes(); 
				for(int j = 0; j < settingsNodeList.getLength(); j++) {
					Node mapNode = node.getChildNodes().item(j);
					if (XMLUtils.ifNodeIsElement(mapNode, XMLUtils.LEVEL_MAP)) {
						readMapSettings(mapNode, settings);
					} else if (XMLUtils.ifNodeIsElement(mapNode, XMLUtils.MODEL_MAP)) {
						readObjectMapSettings(mapNode, settings);
					} else if (XMLUtils.ifNodeIsElement(mapNode, XMLUtils.RAW_MAP)) {
						readRawMapSettings(mapNode, settings);
					}
				}
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
	
	private void readRawMapSettings(Node node, GameSettings settings) {
		Element rawMap = (Element) node;
		String name = XMLUtils.getTagValue(rawMap, XMLUtils.NAME);
		settings.setRawMapName(name);
	}

}
