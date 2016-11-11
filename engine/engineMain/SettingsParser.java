package engineMain;

import java.io.BufferedReader;

public interface SettingsParser {
	 
	public GameSettings readSettings(String fileName, BufferedReader reader);

}
