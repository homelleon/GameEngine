package engineMain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import scene.ES;

public class SettingsTXTLoader implements SettingsLoader {
 
	//загрузка настроек игры
	@Override
	public GameSettings loadSettings(String fileName) {
		FileReader isr = null;
        File setFile = new File(ES.GAME_SETTINGS_PATH + fileName + ".txt");
		 try {
	            isr = new FileReader(setFile);
	        } catch (FileNotFoundException e) {
	            System.err.println("File not found in res; don't use any extention");
	        }
		BufferedReader reader = new BufferedReader(isr);
		SettingsParser parser = new SettingsTXTParser();
		return parser.readSettings(fileName, reader);   
	}

}
