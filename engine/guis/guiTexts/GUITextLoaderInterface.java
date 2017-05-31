package guis.guiTexts;

import java.util.List;

import font.GUIText;
import font.TextMaster;

/**
 * 
 * @author homelleon
 * @see GUITextXMLLoader
 */
public interface GUITextLoaderInterface {
	
	List<GUIText> loadFile(String fileName, TextMaster master); 
		
}
