package texts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import scene.ES;

public class GUITextTXTLoader implements GUITextLoader {
	
	public List<GUIText> loadFile(String fileName, TextMaster master) {
		FileReader isr = null;
		File textFile = new File(ES.INTERFACE_PATH + fileName + ".txt");
		
		try {
			isr = new FileReader(textFile); 
		} catch (FileNotFoundException e) {
			System.err.println("Can't find text file with name " + fileName + 
					".txt");
		}
		BufferedReader reader = new BufferedReader(isr);
        GUITextParser txtParser = new GUITextTXTParser();
        
		return txtParser.parse(reader, master);
	}

}
