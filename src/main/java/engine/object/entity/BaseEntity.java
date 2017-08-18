package object.entity;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import object.entity.entity.IEntity;
import object.model.textured.TexturedModel;

public abstract class BaseEntity {
	
	protected TexturedModel model; // текстурная модель
	protected String name; // имя
	protected Vector3f position; // позиция
	protected Vector3f rotation; // повороты
	protected float scale = 1f; // масштаб
	protected float radius; // радицс
	protected boolean isVisible = true; // видимый
	protected boolean isChosen = false; // выбранный

	protected int textureIndex = 0; // индекс текстуры
	protected int typeID = EngineSettings.ENTITY_TYPE_SIMPLE; // тип объекта

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
	public BaseEntity(String name, int typeID, TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
		this.name = name;
		this.model = model;
		this.radius = model.getRawModel().getBSphere().getRadius() * scale;
		this.typeID = typeID;
		this.position = position;
		this.rotation = rotation;
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
	public BaseEntity(String name, int typeID, TexturedModel model, int textureIndex, Vector3f position, Vector3f rotation, float scale) {
		this.name = name;
		this.textureIndex = textureIndex;
		this.model = model;
		this.radius = model.getRawModel().getBSphere().getRadius() * scale;
		this.position = position;
		this.rotation = rotation;
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
	
	public Vector2f getTextureOffset() {		
		int row = textureIndex / model.getTexture().getNumberOfRows();
		int column = textureIndex % model.getTexture().getNumberOfRows();		
		float xOffset = (float) row / (float) model.getTexture().getNumberOfRows();
		float yOffset = (float) column / (float) model.getTexture().getNumberOfRows();
		return new Vector2f(xOffset, yOffset);
	}

	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	public void move(float forwardSpeed, float strafeSpeed) {
		float dx = (float) (forwardSpeed * Math.sin(Math.toRadians(this.rotation.getY()))
				+ (strafeSpeed * Math.sin(Math.toRadians(this.rotation.getY() + 90))));
		float dz = (float) (forwardSpeed * Math.cos(Math.toRadians(this.rotation.getY()))
				+ (strafeSpeed * Math.cos(Math.toRadians(this.rotation.getY() + 90))));
		increasePosition(dx, 0, dz);
	}

	public void increaseRotation(float dx, float dy, float dz) {
		this.rotation.x += dx;
		this.rotation.y += dy;
		this.rotation.z += dz;
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

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
		
	}

	public Vector3f getRotation() {
		return this.rotation;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
		this.radius = model.getRawModel().getBSphere().getRadius() * scale;
	}

	public float getSphereRadius() {
		return this.radius;
	}
	
	public abstract IEntity clone(String name);

}
