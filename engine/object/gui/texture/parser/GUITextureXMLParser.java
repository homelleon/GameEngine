package object.gui.texture.parser;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.debug.EngineDebug;
import object.gui.texture.GUITexture;
import object.gui.texture.GUITextureBuilder;
import object.gui.texture.GUITextureBuilderInterface;
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
		Node guiTextures = node;
	    NodeList guiTextureList = guiTextures.getChildNodes();
	    int count = 0;
	    for(int j = 0; j < guiTextureList.getLength(); j++) {
			   Node guiTextureNode = guiTextureList.item(j);	        		   
			   if(XMLUtils.ifNodeIsElement(guiTextureNode, XMLUtils.GUI_TEXTURE)) {
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
	               if(EngineDebug.hasDebugPermission()) {
		               if(count!= Integer.valueOf(id)) {
		            	   System.err.println("error id order!");
		               }
	               }
	               GUITextureBuilderInterface builder = new GUITextureBuilder();
	               builder.setName(name).setTextureName(textureName)
   						  .setPosition(position).setScale(scale);
	               GUITexture guiTexture = builder.getTexture();
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
