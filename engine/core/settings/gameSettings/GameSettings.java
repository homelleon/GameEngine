package core.settings.gameSettings;

/**
 * Uses for initialization game variables and objects.
 * <p>Singletone is used to initialize object of that class.<br>
 *  Can't create more the one instance.
 *  
 * @author homelleon
 *
 */
public class GameSettings { 
	
	private static GameSettings instance; 
	String mapName;
	String objectMapName;
	
	private GameSettings() {}
	
	static GameSettings getInstance() {
		if (instance == null) {
			instance = new GameSettings();
		}
		return instance;
	}
	
	void setMapName(String name) {
		this.mapName = name;		
	}
	
	public String getMapName() {
		return this.mapName;
	}
	
	void setObjectMapName(String name) {
		this.objectMapName = name;
	}
	
	public String getObjectMapName() {
		return this.objectMapName;
	}


}
