package engineMain;

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
	
	String getMapName() {
		return this.mapName;
	}
	
	void setObjectMapName(String name) {
		this.objectMapName = name;
	}
	
	String getObjectMapName() {
		return this.objectMapName;
	}


}
