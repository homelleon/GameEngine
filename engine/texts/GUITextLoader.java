package texts;

import java.util.List;

import fontMeshCreator.GUIText;
import fontRendering.TextMaster;

public interface GUITextLoader {
	
	List<GUIText> loadFile(String fileName, TextMaster master); 
		
}
