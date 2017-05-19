package engineMain;

import java.io.BufferedReader;

public interface SettingsParserInterface {
	 
	GameSettings readSettings(String fileName, BufferedReader reader);
	//чтение настроек игры
}
