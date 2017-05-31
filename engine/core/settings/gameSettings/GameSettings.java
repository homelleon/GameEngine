package core.settings.gameSettings;

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
