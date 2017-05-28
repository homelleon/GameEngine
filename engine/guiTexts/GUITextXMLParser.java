package guiTexts;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import scene.ES;
import toolbox.EngineUtils;

public class GUITextXMLParser implements GUITextParserInterface {

	@Override
	public List<GUIText> parse(BufferedReader reader, TextMaster master) {
		String line;
		List<GUIText> textList = new ArrayList<GUIText>();
		
		/* preaparing lists of interface texts parameters */
		List<String> nameList = new ArrayList<String>();
		List<String> pathList = new ArrayList<String>();
		List<Integer> lineList = new ArrayList<Integer>();
		List<String> fontList = new ArrayList<String>();
		List<Float> sizeList = new ArrayList<Float>();
		List<Vector2f> positionList = new ArrayList<Vector2f>();
		List<Float> maxLengthList = new ArrayList<Float>();
		List<Boolean> centeredList = new ArrayList<Boolean>();
		List<Vector3f> colorList = new ArrayList<Vector3f>();
		
		/* parser data */
		try {
			while((line = reader.readLine()) != null) {
				
				if(line.startsWith(ES.XML_TEXT_BEGIN) && line.endsWith(ES.XML_TEXT_END)) {
					line = EngineUtils.pullLineFromWords(line, ES.XML_TEXT_BEGIN, ES.XML_TEXT_END);					
					String[] currentLine = line.split(ES.XML_SEPARATOR);
					
					nameList.add(String.valueOf(currentLine[0]));
					pathList.add(String.valueOf(currentLine[1]));
					lineList.add(Integer.valueOf(currentLine[2]));
					sizeList.add(Float.valueOf(currentLine[3]));
					fontList.add(String.valueOf(currentLine[4]));
					float x = Float.valueOf(currentLine[5]);
					float y = Float.valueOf(currentLine[6]);
					positionList.add(new Vector2f(x, y));					
					maxLengthList.add(Float.valueOf(currentLine[7]));
					centeredList.add(Boolean.valueOf(currentLine[8]));
					float r = 0;
					float g = 0;
					float b = 0;
					/* check if there are color values in the line */
					if(currentLine.length > 9) {
						r = Float.valueOf(currentLine[9]);
						g = Float.valueOf(currentLine[10]);
						b = Float.valueOf(currentLine[11]);
					}
					colorList.add(new Vector3f(r,g,b));
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		TextLoaderInterface txtLoader = new TextTXTLoader();
		for(int i = 0; i < nameList.size(); i++) {
			String text = txtLoader.loadFile(pathList.get(i), lineList.get(i));
			GUIText guiText = new GUIText(nameList.get(i), text, sizeList.get(i), 
					master.getFont(), positionList.get(i), 
					maxLengthList.get(i), centeredList.get(i));
			guiText.setColour(colorList.get(i));
			textList.add(guiText);			
		}
		
		return textList;
	}

}
