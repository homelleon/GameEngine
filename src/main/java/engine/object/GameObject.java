package object;

/**
 * Abstract super class for game objects.
 * <br>Has name property and can return name value as a String.
 * 
 * @author homelleon
 * @version 1.0
 */
public abstract class GameObject {
	protected String name;
	
	protected GameObject(String name) {
		this.name = name;
	}
	
	/**
	 * Gets object name.
	 * 
	 * @return {@link String} value of object name 
	 */
	public String getName() {
		return this.name;
	}
}
