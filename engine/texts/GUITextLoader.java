package texts;

import java.util.List;

import fontMeshCreator.GUIText;
import fontRendering.TextMaster;

/**
 * 
 * @author homelleon
 * @see GUITextTXTLoader
 */
public interface GUITextLoader {
	
	List<GUIText> loadFile(String fileName, TextMaster master); 
		
}
