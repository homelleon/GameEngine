package engineMain.settings;

/**
 * 
 * @author homelleon
 * @see SettingsXMLLoader
 */
public interface SettingsLoaderInterface {
	
	GameSettings loadSettings(String fileName);
	//загрузка настроек игры
}
