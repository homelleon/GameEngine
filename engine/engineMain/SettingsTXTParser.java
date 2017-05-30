package engineMain;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import toolbox.XMLUtils;

public class SettingsTXTParser implements SettingsParserInterface {
 
	@Override
	public GameSettings readSettings(Document document) {
		GameSettings settings = GameSettings.getInstance();		
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		
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
		NodeList mapList = node.getChildNodes();
		for(int j = 0; j < mapList.getLength(); j ++) {
			Node map = mapList.item(j);
			if (XMLUtils.ifNodeIsElement(map, XMLUtils.NAME)) {
				String name = map.getChildNodes().item(0).getNodeValue();
				settings.setMapName(name);
			}
		}
	}
	
	private void readObjectMapSettings(Node node, GameSettings settings) {
		NodeList objectMapList = node.getChildNodes();
		for(int j = 0; j < objectMapList.getLength(); j ++) {
			Node objectMap = objectMapList.item(j);
			if (XMLUtils.ifNodeIsElement(objectMap, XMLUtils.NAME)) {
				String name = objectMap.getChildNodes().item(0).getNodeValue();
				settings.setObjectMapName(name);
			}
		}
	}
	
	
}
