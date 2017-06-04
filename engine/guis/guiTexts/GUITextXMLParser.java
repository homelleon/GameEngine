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
import core.settings.ES;
import font.GUIText;
import font.TextMaster;
import toolbox.XMLUtils;
import toolbox.xmlLoader.XMLFileLoader;
import toolbox.xmlLoader.XMLLoaderInterface;

public class GUITextXMLParser implements GUITextParserInterface {
	
	private Document document;
	private TextMaster master;
	
	public GUITextXMLParser(Document document, TextMaster master) {
		this.document = document;
		this.master = master;
	}

	@Override
	public List<GUIText> parse() {
		
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
			   if(XMLUtils.ifNodeIsElement(guiTextNode, XMLUtils.GUI_TEXT)) {
				   Element guiTextEl = (Element) guiTextNode;
	               String ID = guiTextNode.getAttributes().getNamedItem(XMLUtils.ID).getNodeValue();
	               String name = guiTextEl.getElementsByTagName(XMLUtils.NAME).item(0).getChildNodes().item(0).getNodeValue();
	               String path = guiTextEl.getElementsByTagName(XMLUtils.PATH).item(0).getChildNodes().item(0).getNodeValue();
	               float size = Float.valueOf(guiTextEl.getElementsByTagName(XMLUtils.SIZE).item(0).getChildNodes().item(0).getNodeValue());
	               String font = guiTextEl.getElementsByTagName(XMLUtils.FONT).item(0).getChildNodes().item(0).getNodeValue();
	               Element positionEl = (Element) guiTextEl.getElementsByTagName(XMLUtils.POSITION).item(0);
	               float x = Float.valueOf(positionEl.getElementsByTagName(XMLUtils.X).item(0).getChildNodes().item(0).getNodeValue());
	               float y = Float.valueOf(positionEl.getElementsByTagName(XMLUtils.Y).item(0).getChildNodes().item(0).getNodeValue());
	               Vector2f position = new Vector2f(x, y);
	               float maxLength = Float.valueOf(guiTextEl.getElementsByTagName(XMLUtils.MAX_LENGTH).item(0).getChildNodes().item(0).getNodeValue());
	               boolean isCentered = Boolean.valueOf(guiTextEl.getElementsByTagName(XMLUtils.CENTERED).item(0).getChildNodes().item(0).getNodeValue());
	               float r = 0;
	               float g = 0;
	               float b = 0;
	               NodeList colorList = guiTextEl.getElementsByTagName(XMLUtils.COLOR).item(0).getChildNodes();
	               for(int k = 0; k < colorList.getLength(); k++) {
	            	   Node colorNode = colorList.item(k);
	            	   if (XMLUtils.ifNodeIsElement(colorNode, XMLUtils.RED)) {
	            		   r = Float.valueOf(colorNode.getChildNodes().item(0).getNodeValue());
	            	   } else if (XMLUtils.ifNodeIsElement(colorNode, XMLUtils.GREEN)) {
	            		   g = Float.valueOf(colorNode.getChildNodes().item(0).getNodeValue());
	            	   } else if (XMLUtils.ifNodeIsElement(colorNode, XMLUtils.BLUE)) {
	            		   b = Float.valueOf(colorNode.getChildNodes().item(0).getNodeValue());
	            	   }
	               	}
	               	Vector3f color = new Vector3f(r,g,b);
	               	XMLLoaderInterface xmlLoader = new XMLFileLoader(ES.TEXT_PATH + path + ES.EXTENSION_XML);	               	
	               	TextParserInterface textParser = new TextXMLParser(xmlLoader.load());
	               	String text = textParser.parse();
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
