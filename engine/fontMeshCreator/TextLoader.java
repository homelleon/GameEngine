package fontMeshCreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import scene.ES;

public class TextLoader {
	
	public static String readText(String fileName, int stringFrom, int stringTo) {
		String text = null;
		FileReader isr = null;
		File textFile = new File(ES.TEXT_PATH + "fileName"+".txt");
		
		try {
			isr = new FileReader(textFile); 
		} catch (FileNotFoundException e1) {
			System.err.println("Can't find text file with name "+fileName+".txt");
		}
		String line = null;
		BufferedReader reader = new BufferedReader(isr);
		
		try {
			line = reader.readLine();
			System.out.println(line);
			
			if(line.startsWith("1:")) {
				text = line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}

}
