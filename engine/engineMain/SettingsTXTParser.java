package engineMain;

import java.io.BufferedReader;
import java.io.IOException;

public class SettingsTXTParser implements SettingsParser {
 
	@Override
	public GameSettings readSettings(String fileName, BufferedReader reader) {
		GameSettings settings = GameSettings.getInstance();
		String line;
		try {
			while(true) {
				line = reader.readLine();
				if (line.startsWith("mapName= ")) {
	                    String[] currentLine = line.split(" ");	            
	                    settings.setMapName(currentLine[0]);
				}
				if (line.isEmpty()) {
					break;
				}
			}
			
		} catch (IOException e) {
			System.err.println("Error in reading Settings file!");
			e.printStackTrace();
		} 
		settings.setInitialized(true);
		return settings;
	}
	
	
}
