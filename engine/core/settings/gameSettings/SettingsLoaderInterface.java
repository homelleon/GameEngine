package core.settings.gameSettings;

/**
 * 
 * @author homelleon
 * @see SettingsXMLLoader
 */
public interface SettingsLoaderInterface {
	
	GameSettings loadSettings(String fileName);
	//загрузка настроек игры
}
