package object.entity.entity;

import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import object.model.TexturedModel;

/*
 * EntityTextured - объект с текстурой
 * 03.02.17
 * ------------
 */
/**
 * Textured type of in-game entity.
 *  
 * @author homelleon
 *
 */

public class TexturedEntity implements Entity {
	
	private TexturedModel model; //текстурная модель
	private String name; //имя
	private Vector3f position; //позиция
	private float rotX,rotY,rotZ; //повороты
	private float scale; //масштаб
	private float radius = 1f; //радицс
	private boolean isVisible = true; //видимый
	private boolean isChosen = false; //выбранный
	
	private int textureIndex = 0; //индекс текстуры
	private int typeID = EngineSettings.ENTITY_TYPE_SIMPLE; //тип объекта
	
	/**
	 * 
	 * @param name
	 * @param model
	 * @param position
	 * @param rotX
	 * @param rotY
	 * @param rotZ
	 * @param scale
	 */
	public TexturedEntity(String name, TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
		this.name = name;
		this.model = model;
		this.radius = model.getRawModel().getBSphere().getRadius() * scale;
		this.position = position;
		this.rotX = rotation.x;
		this.rotY = rotation.y;
		this.rotZ = rotation.z;
		this.scale = scale; 
	}
	
	/**
	 * 
	 * @param name
	 * @param typeID
	 * @param model
	 * @param position
	 * @param rotX
	 * @param rotY
	 * @param rotZ
	 * @param scale
	 */
	public TexturedEntity(String name, int typeID, TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.name = name;
		this.model = model;
		this.radius = model.getRawModel().getBSphere().getRadius() * scale;
		this.typeID = typeID;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale; 
	}
	
	/**
	 * 
	 * @param name
	 * @param typeID
	 * @param model
	 * @param textureIndex
	 * @param position
	 * @param rotX
	 * @param rotY
	 * @param rotZ
	 * @param scale
	 */
	public TexturedEntity(String name, int typeID, TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.name = name;
		this.textureIndex = textureIndex;
		this.model = model;
		this.radius = model.getRawModel().getBSphere().getRadius() * scale;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
		
	public void setIsChosen(boolean isChosen) {
		this.isChosen = isChosen;
	}
	
	public boolean getIsChosen() {
		return this.isChosen;
	}
	
	public boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public float getTextureXOffset() {
		int column = textureIndex % model.getTexture().getNumberOfRows();
		return (float)column/(float)model.getTexture().getNumberOfRows();
	}
	
	public float getTextureYOffset() {
		int row = textureIndex/model.getTexture().getNumberOfRows();
		return (float)row/(float)model.getTexture().getNumberOfRows();
	}	

	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}
	
	public void move(float forwardSpeed, float strafeSpeed) {
		float dx = (float) (forwardSpeed * Math.sin(Math.toRadians(getRotY())) 
				+ (strafeSpeed * Math.sin(Math.toRadians(getRotY() + 90))));
		float dz = (float) (forwardSpeed * Math.cos(Math.toRadians(getRotY())) 
				+ (strafeSpeed * Math.cos(Math.toRadians(getRotY() + 90))));
		increasePosition(dx, 0, dz);
	}
	
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}
	
	public String getName() {
		return name;
	}
	
	public int getType() {
		return this.typeID;
	}	
	
	public TexturedModel getModel() {
		return model;
	}
	
	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public float getRotX() {
		return rotX;
	}
	
	public void setRotX(float rotX) {
		this.rotX = rotX;
	}
	
	public float getRotY() {
		return rotY;
	}
	
	public void setRotY(float rotY) {
		this.rotY = rotY;
	}
	
	public float getRotZ() {
		return rotZ;
	}
	
	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public float getSphereRadius() {
		return this.radius;
	}

}
