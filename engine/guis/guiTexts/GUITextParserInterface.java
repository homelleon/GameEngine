package guis.guiTexts;

import java.util.List;

import org.w3c.dom.Document;

import font.GUIText;
import font.TextMaster;

/**
 * 
 * @author homelleon
 * @see GUITextXMLParser
 */
public interface GUITextParserInterface {
	
	List<GUIText> parse(Document document, TextMaster master);

}
