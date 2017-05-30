package engineMain;

import org.w3c.dom.Document;

public interface SettingsParserInterface {
	 
	GameSettings readSettings(Document document);
	//чтение настроек игры
}
