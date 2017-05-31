package guis.guiTexts;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.debug.EngineDebug;
import font.GUIText;
import font.TextMaster;
import toolbox.XMLUtils;

public class GUITextXMLParser implements GUITextParserInterface {

	@Override
	public List<GUIText> parse(Document document, TextMaster master) {
		
		NodeList nodeList = document.getDocumentElement().getChildNodes(); 
		List<GUIText> textList = new ArrayList<GUIText>();
        
        for (int i = 0; i < nodeList.getLength(); i++) {
           Node node = nodeList.item(i);
           if (XMLUtils.ifNodeIsElement(node, XMLUtils.GUI_TEXTS)) {
        	   textList = createText(node, master);
           }	        	
        }
        if(EngineDebug.hasDebugPermission()) {
        	System.out.println("Loading complete!");
        }
        
		return textList;
	}
	
	private List<GUIText> createText(Node node, TextMaster master) {
		if(EngineDebug.hasDebugPermission()) {
			System.out.println("Loading texts...");
	   	}
		
	    List<GUIText> textList = new ArrayList<GUIText>();
		Node guiTexts = node;
	    NodeList guiTextList = guiTexts.getChildNodes();
	    
	    for(int j = 0; j < guiTextList.getLength(); j++) {
			   Node guiTextNode = guiTextList.item(j);	        		   
			   if (XMLUtils.ifNodeIsElement(guiTextNode, "GUIText")) {
				   Element guiTextEl = (Element) guiTextNode;
	               String ID = guiTextNode.getAttributes().getNamedItem("id").getNodeValue();
	               String name = guiTextEl.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
	               String path = guiTextEl.getElementsByTagName("path").item(0).getChildNodes().item(0).getNodeValue();
	               int line = Integer.valueOf(guiTextEl.getElementsByTagName("line").item(0).getChildNodes().item(0).getNodeValue());
	               float size = Float.valueOf(guiTextEl.getElementsByTagName("size").item(0).getChildNodes().item(0).getNodeValue());
	               String font = guiTextEl.getElementsByTagName("font").item(0).getChildNodes().item(0).getNodeValue();
	               float x = Float.valueOf(guiTextEl.getElementsByTagName("x").item(0).getChildNodes().item(0).getNodeValue());
	               float y = Float.valueOf(guiTextEl.getElementsByTagName("y").item(0).getChildNodes().item(0).getNodeValue());
	               Vector2f position = new Vector2f(x, y);
	               float maxLength = Float.valueOf(guiTextEl.getElementsByTagName("max_length").item(0).getChildNodes().item(0).getNodeValue());
	               boolean isCentered = Boolean.valueOf(guiTextEl.getElementsByTagName("centered").item(0).getChildNodes().item(0).getNodeValue());
	               float r = 0;
	               float g = 0;
	               float b = 0;
	               NodeList colorList = guiTextEl.getElementsByTagName("color").item(0).getChildNodes();
	               for(int k = 0; k < colorList.getLength(); k++) {
	            	   Node colorNode = colorList.item(k);
	            	   if (XMLUtils.ifNodeIsElement(colorNode, "r")) {
	            		   r = Float.valueOf(colorNode.getChildNodes().item(0).getNodeValue());
	            	   } else if (XMLUtils.ifNodeIsElement(colorNode, "g")) {
	            		   g = Float.valueOf(colorNode.getChildNodes().item(0).getNodeValue());
	            	   } else if (XMLUtils.ifNodeIsElement(colorNode, "b")) {
	            		   b = Float.valueOf(colorNode.getChildNodes().item(0).getNodeValue());
	            	   }	            	   
	               	}
	               	Vector3f color = new Vector3f(r,g,b);
	               	TextLoaderInterface txtLoader = new TextXMLLoader();
	               	String text = txtLoader.loadFile(path, line);
	               	GUIText guiText = new GUIText(name, text, size,
	            		   master.getFont(), position, maxLength, isCentered);
	   			   	guiText.setColour(color);
	   			   	textList.add(guiText);
		   			if(EngineDebug.hasDebugPermission()) {
		   				System.out.println(guiText.getName());
		   		   	}
			   }
	    }
	    if(EngineDebug.hasDebugPermission()) {
			System.out.println("Succed!");
	   	}
		
		return textList;
	}

}
