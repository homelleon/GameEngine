package texts;

import java.io.BufferedReader;
import java.util.List;

import fontMeshCreator.GUIText;
import fontRendering.TextMaster;

public interface GUITextParser {
	
	List<GUIText> parse(BufferedReader reader, TextMaster master);

}
