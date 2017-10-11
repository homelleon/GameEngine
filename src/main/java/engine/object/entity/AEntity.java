package object.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import core.settings.EngineSettings;
import manager.octree.EntityNode;
import object.GameObject;
import object.entity.entity.IEntity;
import primitive.model.Model;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

public abstract class AEntity extends GameObject {
	
	protected List<Model> models = new ArrayList<Model>(); // текстурная модель
	protected String baseName;
	protected Vector3f position; // позиция
	protected Vector3f rotation; // повороты
	protected float scale = 1f; // масштаб
	protected float radius; // радицс
	protected boolean isVisible = true; // видимый
	protected boolean isChosen = false; // выбранный
	protected boolean isMoved = false;
	protected EntityNode parent = null;

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
	public AEntity(String name, int typeID, Collection<Model> modelList, Vector3f position, Vector3f rotation, float scale) {
		super(name);
		this.typeID = typeID;
		this.models.addAll(modelList);
		this.radius = this.models.get(0).getMesh().getBSphere().getRadius() * scale;
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
	public AEntity(String name, int typeID, Collection<Model> modelList, int textureIndex, Vector3f position, Vector3f rotation, float scale) {
		super(name);
		this.typeID = typeID;
		this.textureIndex = textureIndex;
		this.models.addAll(modelList);
		this.radius = this.models.get(0).getMesh().getBSphere().getRadius() * scale;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	 
	 public void setParentNode(EntityNode parent) {
		 this.parent = parent;
	 }
	 
	 public EntityNode getParentNode() {
		 return this.parent;
	 }
	 
	 public boolean removeParentNode() {
		 if(hasParent()) {
			 this.parent = null;
			 return true;
		 } else {
			 return false;
		 }
	 }
	 
	 public boolean hasParent() {
		 return this.parent == null ? false : true;
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
		int row = textureIndex / models.get(0).getMaterial().getDiffuseMap().getNumberOfRows();
		int column = textureIndex %  models.get(0).getMaterial().getDiffuseMap().getNumberOfRows();		
		float xOffset = (float) row / (float) models.get(0).getMaterial().getDiffuseMap().getNumberOfRows();
		float yOffset = (float) column / (float) models.get(0).getMaterial().getDiffuseMap().getNumberOfRows();
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
		this.rotation.x += dx / scale;
		this.rotation.y += dy / scale;
		this.rotation.z += dz / scale;
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

	public List<Model> getModels() {
		return models;
	}

	public void addModel(Model model) {
		this.models.add(model);
	}

	public Vector3f getPosition() {
		return position;
	}

	public synchronized void setPosition(Vector3f position) {
		this.position = position;
	}

	public synchronized void setRotation(Vector3f rotation) {
		this.rotation = rotation;
		
	}

	public Vector3f getRotation() {
		return this.rotation;
	}

	public float getScale() {
		return scale;
	}

	public synchronized void setScale(float scale) {
		this.scale = scale;
		this.radius =  models.get(0).getMesh().getBSphere().getRadius() * scale;
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
	
	public void delete() {
		if(this.parent != null) {
			this.parent.removeEntity(this.getName());
			this.parent = null;
		}
		this.models.clear();
	}

}
