package object.gui.texture.parser;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.debug.EngineDebug;
import core.settings.EngineSettings;
import object.gui.texture.GUITexture;
import renderer.loader.Loader;
import tool.xml.XMLUtils;

public class GUITextureXMLParser implements GUITextureParserInterface {
	
	private Document document;
	
	public GUITextureXMLParser(Document document) {
		this.document = document;
	}

	@Override
	public List<GUITexture> parse() {
		
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		List<GUITexture> textureList = new ArrayList<GUITexture>();
		 for (int i = 0; i < nodeList.getLength(); i++) {
	           Node node = nodeList.item(i);
	           if (XMLUtils.ifNodeIsElement(node, XMLUtils.GUI_TEXTURES)) {
	        	   textureList = createTexture(node);
	           }	        	
        }
	        if(EngineDebug.hasDebugPermission()) {
	        	System.out.println("Loading complete!");
	        }
	        
		return textureList;
	}
	
	private List<GUITexture> createTexture(Node node) {
		if(EngineDebug.hasDebugPermission()) {
			System.out.println("Loading GUI textures...");
	   	}
		List<GUITexture> textureList = new ArrayList<GUITexture>();
		
        Loader loader = Loader.getInstance();
		Node guiTextures = node;
	    NodeList guiTextureList = guiTextures.getChildNodes();
	    
	    for(int j = 0; j < guiTextureList.getLength(); j++) {
			   Node guiTextureNode = guiTextureList.item(j);	        		   
			   if(XMLUtils.ifNodeIsElement(guiTextureNode, XMLUtils.GUI_TEXTURE)) {
				   Element guiTextEl = (Element) guiTextureNode;
	               String ID = guiTextureNode.getAttributes().getNamedItem(XMLUtils.ID).getNodeValue();
	               String name = guiTextEl.getElementsByTagName(XMLUtils.NAME).item(0).getChildNodes().item(0).getNodeValue();
	               String textureName = guiTextEl.getElementsByTagName(XMLUtils.TEXTURE).item(0).getChildNodes().item(0).getNodeValue();
	               Element positionEl = (Element) guiTextEl.getElementsByTagName(XMLUtils.POSITION).item(0);
	               float x = Float.valueOf(positionEl.getElementsByTagName(XMLUtils.X).item(0).getChildNodes().item(0).getNodeValue());
	               float y = Float.valueOf(positionEl.getElementsByTagName(XMLUtils.Y).item(0).getChildNodes().item(0).getNodeValue());
	               Vector2f position = new Vector2f(x, y);
	               Element scaleEl = (Element) guiTextEl.getElementsByTagName(XMLUtils.SCALE).item(0);
	               float scaleX = Float.valueOf(scaleEl.getElementsByTagName(XMLUtils.X).item(0).getChildNodes().item(0).getNodeValue());
	               float scaleY = Float.valueOf(scaleEl.getElementsByTagName(XMLUtils.Y).item(0).getChildNodes().item(0).getNodeValue());
	               Vector2f scale = new Vector2f(scaleX, scaleY);

	               int texture = loader.loadTexture(EngineSettings.TEXTURE_INTERFACE_PATH, textureName);
	               GUITexture guiTexture = new GUITexture(name, texture, position, scale);
	               guiTexture.setIsShown(true);
	   			   textureList.add(guiTexture);
	   			   if(EngineDebug.hasDebugPermission()) {
		   				System.out.println(guiTexture.getName());
	   			   }
			   }
	    }
	    if(EngineDebug.hasDebugPermission()) {
			System.out.println("Succed!");
	   	}
		
		return textureList;
	}

}
