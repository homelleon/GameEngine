package guiTexts;

import java.util.List;

import fontMeshCreator.GUIText;
import fontRendering.TextMaster;

/**
 * 
 * @author homelleon
 * @see GUITextXMLLoader
 */
public interface GUITextLoaderInterface {
	
	List<GUIText> loadFile(String fileName, TextMaster master); 
		
}
