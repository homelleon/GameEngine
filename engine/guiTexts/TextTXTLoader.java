package guiTexts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import scene.ES;

public class TextTXTLoader implements TextLoaderInterface {

	@Override
	public String loadFile(String fileName, Integer lineNumber) {
		String text = "";
		FileReader isr = null;
		File textFile = new File(ES.TEXT_PATH + fileName + ".txt");
		System.out.println(textFile.getPath());
		try {
			isr = new FileReader(textFile); 
		} catch (FileNotFoundException e) {
			System.err.println("Can't find text file with name " + fileName + 
					".txt");
		}
		BufferedReader reader = new BufferedReader(isr);
		String line;
		try {
			while((line = reader.readLine()) != null) {				
				text += line;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return text;
	}

}
