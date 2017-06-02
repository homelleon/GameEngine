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
	
	/*
	 * GameSettings - настройки игры
	 * 03.02.17
	 * --------------------
	 */
	String mapName;
	String objectMapName;
	
	private GameSettings() {}
	
	static GameSettings getInstance() {
		if (instance == null) {
		     instance = new GameSettings();
		   } else {
			   throw new SecurityException("Can't use more than one instance!");
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
