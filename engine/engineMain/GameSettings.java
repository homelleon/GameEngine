package engineMain;

public class GameSettings { 
	
	private static GameSettings instance; 
	
	String mapName;
	
	private GameSettings() {}
	
	static GameSettings getInstance() {
		if (instance == null) {
		     instance = new GameSettings();
		   }
		return instance;
	}
	
	String getMapName() {
		return this.mapName;
	}

	void setMapName(String name) {
		mapName = name;		
	}

}
