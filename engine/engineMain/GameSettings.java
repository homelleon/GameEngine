package engineMain;

public class GameSettings {
	
	private static GameSettings instance; 
	
	String mapName;
	private boolean isInitialized;
	
	private GameSettings() {}
	
	public static GameSettings getInstance() {
		if (instance == null) {
		     instance = new GameSettings();
		   }
		return instance;
	}
	
	public void setInitialized(boolean isInitialized) {
		this.isInitialized = isInitialized;
	}


	public String getMapName() {
		if(this.isInitialized) {
			return mapName;
		} else
			return null;
	}

	public void setMapName(String name) {
		mapName = name;		
	}

}
