package objects.gui.guiTexts;

import java.util.List;

import font.fontMeshCreator.GUIText;
import font.fontRendering.TextMaster;

/**
 * 
 * @author homelleon
 * @see GUITextXMLLoader
 */
public interface GUITextLoaderInterface {
	
	List<GUIText> loadFile(String fileName, TextMaster master); 
		
}
