package object.gui.text;

import java.util.List;

import object.gui.font.GUIText;
import object.gui.font.TextMaster;

/**
 * 
 * @author homelleon
 * @see GUITextXMLLoader
 */
public interface GUITextLoaderInterface {
	
	List<GUIText> loadFile(String fileName, TextMaster master); 
		
}
