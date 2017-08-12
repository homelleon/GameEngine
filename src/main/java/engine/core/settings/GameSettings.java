package core.settings;

/**
 * Uses for initialization game variables and objects.
 * <p>
 * Singletone is used to initialize object of that class.<br>
 * Can't create more the one instance.
 * 
 * @author homelleon
 *
 */
public class GameSettings {

	private static GameSettings instance;
	String modelMapName;
	String objectMapName;
	String rawMapName;

	private GameSettings() {
	}

	public static GameSettings getInstance() {
		if (instance == null) {
			instance = new GameSettings();
		}
		return instance;
	}

	public void setMapName(String name) {
		this.modelMapName = name;
	}

	public String getModelMapName() {
		return this.modelMapName;
	}
	
	public String getRawMapName() {
		return this.rawMapName;
	}

	public void setObjectMapName(String name) {
		this.objectMapName = name;
	}

	public String getObjectMapName() {
		return this.objectMapName;
	}
	
	public void setRawMapName(String name) {
		this.rawMapName = name;
	}

}
