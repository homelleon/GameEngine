package guiTexts;

import java.io.BufferedReader;
import java.util.List;

import fontMeshCreator.GUIText;
import fontRendering.TextMaster;

/**
 * 
 * @author homelleon
 * @see GUITextXMLParser
 */
public interface GUITextParserInterface {
	
	List<GUIText> parse(BufferedReader reader, TextMaster master);

}
