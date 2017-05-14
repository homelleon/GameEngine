package texts;

import java.util.List;

import fontMeshCreator.GuiText;
import fontRendering.TextMaster;

public interface GUITextLoader {
	
	List<GuiText> loadFile(String fileName, TextMaster master); 
		
}
