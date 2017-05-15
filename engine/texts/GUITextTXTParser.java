package texts;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import fontMeshCreator.GUIText;
import fontRendering.TextMaster;

public class GUITextTXTParser implements GUITextParser {

	@Override
	public List<GUIText> parse(BufferedReader reader, TextMaster master) {
		String line;
		List<GUIText> textList = new ArrayList<GUIText>();
		
		/* preaparing lists of interface texts parameters */
		List<String> nameList = new ArrayList<String>();
		List<String> pathList = new ArrayList<String>();
		List<String> fontList = new ArrayList<String>();
		List<Float> sizeList = new ArrayList<Float>();
		List<Vector2f> positionList = new ArrayList<Vector2f>();
		List<Float> maxLengthList = new ArrayList<Float>();
		List<Boolean> centeredList = new ArrayList<Boolean>();
		
		/* parser data */		
		try {
			while(reader.readLine() != null) {
				line = reader.readLine();
				
				if(line.startsWith("<txt> ")) {
					String[] currentLine = line.split(" ");
					
					nameList.add(String.valueOf(currentLine[1]));
					pathList.add(String.valueOf(currentLine[2]));
					fontList.add(String.valueOf(currentLine[3]));
					float x = Float.valueOf(currentLine[4]);
					float y = Float.valueOf(currentLine[5]);
					positionList.add(new Vector2f(x, y));
					sizeList.add(Float.valueOf(currentLine[6]));
					maxLengthList.add(Float.valueOf(currentLine[7]));
					centeredList.add(Boolean.valueOf(currentLine[8]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		TextLoader txtLoader = new TextTXTLoader();
		for(int i = 0; i < nameList.size(); i++) {			
			String text = txtLoader.loadFile(pathList.get(i));
			System.out.println("text " + i);
			GUIText guiText = new GUIText(nameList.get(i), text, 4, 
					master.getFont(), positionList.get(i), 
					maxLengthList.get(i), centeredList.get(i));
			textList.add(guiText);			
		}
		
		return textList;
	}

}
