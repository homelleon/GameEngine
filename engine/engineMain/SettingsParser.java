package engineMain;

import java.io.BufferedReader;

public interface SettingsParser {
	 
	GameSettings readSettings(String fileName, BufferedReader reader);
	//чтение настроек игры
}
