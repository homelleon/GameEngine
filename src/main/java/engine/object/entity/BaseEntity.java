package object.entity;

import core.settings.EngineSettings;
import object.entity.entity.IEntity;
import primitive.model.Model;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3fF;

public abstract class BaseEntity {
	
	protected Model model; // текстурная модель
	protected String name; // имя
	protected String baseName;
	protected Vector3fF position; // позиция
	protected Vector3fF rotation; // повороты
	protected float scale = 1f; // масштаб
	protected float radius; // радицс
	protected boolean isVisible = true; // видимый
	protected boolean isChosen = false; // выбранный
	protected boolean isMoved = false;

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
	public BaseEntity(String name, int typeID, Model model, Vector3fF position, Vector3fF rotation, float scale) {
		this.name = name;
		this.typeID = typeID;
		this.model = model;
		this.radius = model.getMesh().getBSphere().getRadius() * scale;
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
	public BaseEntity(String name, int typeID, Model model, int textureIndex, Vector3fF position, Vector3fF rotation, float scale) {
		this.name = name;
		this.typeID = typeID;
		this.textureIndex = textureIndex;
		this.model = model;
		this.radius = model.getMesh().getBSphere().getRadius() * scale;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public synchronized void setChosen(boolean isChosen) {
		this.isChosen = isChosen;
	}

	public boolean isChosen() {
		return this.isChosen;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public synchronized void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public Vector2f getTextureOffset() {		
		int row = textureIndex / model.getMaterial().getDiffuseMap().getNumberOfRows();
		int column = textureIndex % model.getMaterial().getDiffuseMap().getNumberOfRows();		
		float xOffset = (float) row / (float) model.getMaterial().getDiffuseMap().getNumberOfRows();
		float yOffset = (float) column / (float) model.getMaterial().getDiffuseMap().getNumberOfRows();
		return new Vector2f(xOffset, yOffset);
	}

	public synchronized void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	public synchronized void move(float forwardSpeed, float strafeSpeed) {
		this.isMoved = false;
		if(forwardSpeed >0 || strafeSpeed > 0) {
			this.isMoved = true;
		}
		float dx = (float) (forwardSpeed * Math.sin(Math.toRadians(this.rotation.getY()))
				+ (strafeSpeed * Math.sin(Math.toRadians(this.rotation.getY() + 90))));
		float dz = (float) (forwardSpeed * Math.cos(Math.toRadians(this.rotation.getY()))
				+ (strafeSpeed * Math.cos(Math.toRadians(this.rotation.getY() + 90))));
		increasePosition(dx, 0, dz);
	}

	public synchronized void increaseRotation(float dx, float dy, float dz) {
		this.rotation.x += dx;
		this.rotation.y += dy;
		this.rotation.z += dz;
	}

	public String getName() {
		return name;
	}
	
	public String getBaseName() {
		return this.baseName;
	}
	
	public void setBaseName(String name) {
		this.baseName = name;
	}

	public int getType() {
		return this.typeID;
	}

	public Model getModel() {
		return model;
	}

	public synchronized void setModel(Model model) {
		this.model = model;
	}

	public Vector3fF getPosition() {
		return position;
	}

	public synchronized void setPosition(Vector3fF position) {
		this.position = position;
	}

	public synchronized void setRotation(Vector3fF rotation) {
		this.rotation = rotation;
		
	}

	public Vector3fF getRotation() {
		return this.rotation;
	}

	public float getScale() {
		return scale;
	}

	public synchronized void setScale(float scale) {
		this.scale = scale;
		this.radius = model.getMesh().getBSphere().getRadius() * scale;
	}

	public float getSphereRadius() {
		return this.radius;
	}
	
	public abstract IEntity clone(String name);

	public boolean isMoved() {
		return isMoved;
	}

	public void setMoved(boolean isMoved) {
		this.isMoved = isMoved;
	}

}
