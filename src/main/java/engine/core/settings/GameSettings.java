package core.settings;

/**
 * Uses for initialization game variables and objects.
 * <p>Can't create more then one instance.
 * 
 * @author homelleon
 *
 */
public class GameSettings {

	private static GameSettings instance;
	String modelMapName;
	String levelMapName;
	String rawMapName;

	private GameSettings() {}

	/**
	 * Gets GameSettings object instance.
	 * <br>Every time gets the same object.
	 *  
	 * @return
	 */
	public static GameSettings getInstance() {
		if (instance == null) {
			instance = new GameSettings();
		}
		return instance;
	}

	/**
	 * Sets model map name.
	 * 
	 * @param name {@link String} value of model map to store in GameSettings
	 */
	public void setModelMapName(String name) {
		this.modelMapName = name;
	}

	/**
	 * Gets stored model map name.
	 * 
	 * @return {@link String} value of stored in GameSettings model name
	 */
	public String getModelMapName() {
		return this.modelMapName;
	}

	/**
	 * Sets raw map name.
	 * 
	 * @param name {@link String} value of raw map to store in GameSettings
	 */
	public void setRawMapName(String name) {
		this.rawMapName = name;
	}
	
	/**
	 * Gets stored raw map name.
	 * 
	 * @return {@link String} value of stored in GameSettings raw name
	 */
	public String getRawMapName() {
		return this.rawMapName;
	}

	/**
	 * Sets level map name.
	 * 
	 * @param name {@link String} value of level map to store in GameSettings
	 */
	public void setLevelMapName(String name) {
		this.levelMapName = name;
	}

	/**
	 * Gets stored level map name.
	 * 
	 * @return {@link String} value of stored in GameSettings level name
	 */
	public String getLevelMapName() {
		return this.levelMapName;
	}	

}
