package engineMain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import maps.MapParser;
import maps.MapReadable;
import scene.EngineSettings;

public class SettingsTXTLoader implements SettingsLoader {
 
	@Override
	public GameSettings loadSettings(String fileName) {
		FileReader isr = null;
		//EngineSettings.GAME_SETTINGS_PATH
        File mapFile = new File(EngineSettings.GAME_SETTINGS_PATH + fileName + ".txt");
		 try {
	            isr = new FileReader(fileName);
	        } catch (FileNotFoundException e) {
	            System.err.println("File not found in res; don't use any extention");
	        }
		BufferedReader reader = new BufferedReader(isr);
		SettingsParser parser = new SettingsTXTParser();
		return parser.readSettings(fileName, reader);   
	}

}
