package texts;

import java.io.BufferedReader;
import java.util.List;

import fontMeshCreator.GuiText;
import fontRendering.TextMaster;

public interface GUITextParser {
	
	List<GuiText> parse(BufferedReader reader, TextMaster master);

}
