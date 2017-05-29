package guiTexts;

import java.util.List;

import org.w3c.dom.Document;

import fontMeshCreator.GUIText;
import fontRendering.TextMaster;

/**
 * 
 * @author homelleon
 * @see GUITextXMLParser
 */
public interface GUITextParserInterface {
	
	List<GUIText> parse(Document document, TextMaster master);

}
