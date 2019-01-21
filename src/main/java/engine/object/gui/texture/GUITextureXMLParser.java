package object.gui.texture;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.EngineDebug;
import tool.math.vector.Vector2f;
import tool.xml.XMLUtils;
import tool.xml.parser.IListParser;
import tool.xml.parser.XMLParser;

public class GUITextureXMLParser extends XMLParser implements IListParser<GUITexture> {

	public GUITextureXMLParser(Document document) {
		super(document);
	}

	@Override
	public List<GUITexture> parse() {

		NodeList nodeList = document.getDocumentElement().getChildNodes();
		List<GUITexture> textureList = new ArrayList<GUITexture>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);			
			if (XMLUtils.ifNodeIsElement(node, XMLUtils.GUI_TEXTURES))
				textureList = createTexture(node);			
		}
		return textureList;
	}

	private List<GUITexture> createTexture(Node node) {
		if (EngineDebug.hasDebugPermission())
			EngineDebug.println("Loading GUI textures...", 1);
		
		List<GUITexture> textureList = new ArrayList<GUITexture>();
		Node guiTextures = node;
		NodeList guiTextureList = guiTextures.getChildNodes();
		int count = 0;
		for (int j = 0; j < guiTextureList.getLength(); j++) {
			Node guiTextureNode = guiTextureList.item(j);
			if (XMLUtils.ifNodeIsElement(guiTextureNode, XMLUtils.GUI_TEXTURE)) {
				Element guiTextEl = (Element) guiTextureNode;
				String id = XMLUtils.getAttributeValue(guiTextureNode, XMLUtils.ID);
				String name = XMLUtils.getTagValue(guiTextEl, XMLUtils.NAME);
				String textureName = XMLUtils.getTagValue(guiTextEl, XMLUtils.TEXTURE);
				Element positionEl = XMLUtils.getChildElementByTag(guiTextEl, XMLUtils.POSITION);
				float x = Float.valueOf(XMLUtils.getTagValue(positionEl, XMLUtils.X));
				float y = Float.valueOf(XMLUtils.getTagValue(positionEl, XMLUtils.Y));
				Vector2f position = new Vector2f(x, y);
				Element scaleEl = XMLUtils.getChildElementByTag(guiTextEl, XMLUtils.SCALE);
				float scaleX = Float.valueOf(XMLUtils.getTagValue(scaleEl, XMLUtils.X));
				float scaleY = Float.valueOf(XMLUtils.getTagValue(scaleEl, XMLUtils.Y));
				Vector2f scale = new Vector2f(scaleX, scaleY);
				
				count++;
				
				if (EngineDebug.hasDebugPermission()) {
					if (count != Integer.valueOf(id))
						EngineDebug.printError("error id order!");
				}
				
				GUITexture guiTexture =	new GUITextureBuilder()
					   .setTextureName(textureName)
					   .setPosition(position)
					   .setScale(scale)
					   .build(name);
				
				textureList.add(guiTexture);
				if (EngineDebug.hasDebugPermission())
					EngineDebug.println(guiTexture.getName(), 2);
			}
		}
		
		if (EngineDebug.hasDebugPermission())
			EngineDebug.println("Succed!", 1);
		
		return textureList;
	}

}