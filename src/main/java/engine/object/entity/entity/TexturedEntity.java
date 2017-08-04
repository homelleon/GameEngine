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

	private TexturedModel model; // текстурная модель
	private String name; // имя
	private Vector3f position; // позиция
	private Vector3f rotation; // повороты
	private float scale; // масштаб
	private float radius = 1f; // радицс
	private boolean isVisible = true; // видимый
	private boolean isChosen = false; // выбранный

	private int textureIndex = 0; // индекс текстуры
	private int typeID = EngineSettings.ENTITY_TYPE_SIMPLE; // тип объекта

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
		this.rotation = rotation;
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
	public TexturedEntity(String name, int typeID, TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
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
	public TexturedEntity(String name, int typeID, TexturedModel model, int textureIndex, Vector3f position, Vector3f rotation, float scale) {
		this.name = name;
		this.textureIndex = textureIndex;
		this.model = model;
		this.radius = model.getRawModel().getBSphere().getRadius() * scale;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	@Override
	public void setIsChosen(boolean isChosen) {
		this.isChosen = isChosen;
	}

	@Override
	public boolean getIsChosen() {
		return this.isChosen;
	}

	@Override
	public boolean getIsVisible() {
		return isVisible;
	}

	@Override
	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	@Override
	public float getTextureXOffset() {
		int column = textureIndex % model.getTexture().getNumberOfRows();
		return (float) column / (float) model.getTexture().getNumberOfRows();
	}

	@Override
	public float getTextureYOffset() {
		int row = textureIndex / model.getTexture().getNumberOfRows();
		return (float) row / (float) model.getTexture().getNumberOfRows();
	}

	@Override
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	@Override
	public void move(float forwardSpeed, float strafeSpeed) {
		float dx = (float) (forwardSpeed * Math.sin(Math.toRadians(this.rotation.getY()))
				+ (strafeSpeed * Math.sin(Math.toRadians(this.rotation.getY() + 90))));
		float dz = (float) (forwardSpeed * Math.cos(Math.toRadians(this.rotation.getY()))
				+ (strafeSpeed * Math.cos(Math.toRadians(this.rotation.getY() + 90))));
		increasePosition(dx, 0, dz);
	}

	@Override
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotation.x += dx;
		this.rotation.y += dy;
		this.rotation.z += dz;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getType() {
		return this.typeID;
	}

	@Override
	public TexturedModel getModel() {
		return model;
	}

	@Override
	public void setModel(TexturedModel model) {
		this.model = model;
	}

	@Override
	public Vector3f getPosition() {
		return position;
	}

	@Override
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	@Override
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
		
	}

	@Override
	public Vector3f getRotation() {
		return this.rotation;
	}

	@Override
	public float getScale() {
		return scale;
	}

	@Override
	public void setScale(float scale) {
		this.scale = scale;
	}

	@Override
	public float getSphereRadius() {
		return this.radius;
	}
	
	@Override
	public Entity clone(String name) {
		Entity entity = new TexturedEntity(name, this.model, this.position, this.rotation, this.scale);
		return entity;
	}


}
