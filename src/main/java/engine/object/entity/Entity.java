package object.entity;

import java.util.Collection;
import java.util.List;

import manager.entity.EntityNode;
import primitive.model.Model;
import scene.Drawable;
import shader.Shader;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

public class Entity extends Drawable<Vector3f> {
	
	protected String baseName;
	protected float radius;
	protected boolean isVisible = true;
	protected boolean isChosen = false;
	protected boolean isMoved = false;
	protected EntityNode parent = null;

	protected int textureIndex = 0;
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
	public Entity(String name, Shader shader, List<Model> models, Vector3f position, Vector3f rotation, float scale) {
		super(name, shader, models);
		this.radius = this.models.get(0).getMesh().getBoundSphere().getRadius() * scale;
		this.position = position;
		this.rotation = rotation;
		this.scale = new Vector3f(scale, scale, scale);
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
	public Entity(String name, Shader shader, Collection<Model> models, int textureIndex, Vector3f position, Vector3f rotation, Vector3f scale) {
		super(name, shader, models);
		this.textureIndex = textureIndex;
		this.radius = this.models.get(0).getMesh().getBoundSphere().getRadius() * scale.x;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	 
	 public void setParentNode(EntityNode parent) {
		 this.parent = parent;
	 }
	 
	 public EntityNode getParentNode() {
		 return parent;
	 }
	 
	 public boolean removeParentNode() {
		 if (!hasParent()) return false;
		 this.parent = null;
		 return true;
	 }
	 
	 public boolean hasParent() {
		 return (parent != null);
	 }

	public synchronized void setChosen(boolean isChosen) {
		this.isChosen = isChosen;
	}

	public boolean isChosen() {
		return isChosen;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public Vector2f getTextureOffset() {		
		int row = textureIndex / models.get(0).getMaterial().getDiffuseMap().getNumberOfRows();
		int column = textureIndex %  models.get(0).getMaterial().getDiffuseMap().getNumberOfRows();		
		float xOffset = (float) row / (float) models.get(0).getMaterial().getDiffuseMap().getNumberOfRows();
		float yOffset = (float) column / (float) models.get(0).getMaterial().getDiffuseMap().getNumberOfRows();
		return new Vector2f(xOffset, yOffset);
	}

	public void increasePosition(float dx, float dy, float dz) {
		position.x += dx;
		position.y += dy;
		position.z += dz;
	}

	public void move(float forwardSpeed, float strafeSpeed) {
		isMoved = false;
		if (forwardSpeed > 0 || strafeSpeed > 0)
			this.isMoved = true;
		
		float dx = (float) (forwardSpeed * Math.sin(Math.toRadians(rotation.getY()))
				+ (strafeSpeed * Math.sin(Math.toRadians(rotation.getY() + 90))));
		float dz = (float) (forwardSpeed * Math.cos(Math.toRadians(rotation.getY()))
				+ (strafeSpeed * Math.cos(Math.toRadians(rotation.getY() + 90))));
		increasePosition(dx, 0, dz);
	}

	public void increaseRotation(float dx, float dy, float dz) {
		rotation.x += dx;
		rotation.y += dy;
		rotation.z += dz;
	}
	
	public String getBaseName() {
		return baseName;
	}
	
	public void setBaseName(String name) {
		baseName = name;
	}
	
	@Override
	public Entity setScale(Vector3f scale) {
		radius =  models.get(0).getMesh().getBoundSphere().getRadius() * scale.x;
		return (Entity) super.setScale(scale);
	}

	public float getSphereRadius() {
		return radius;
	}
	
	public Entity clone(String name) {
		Entity entity = new Entity(name, shader, models, this.textureIndex, 
				new Vector3f(position), new Vector3f(rotation), new Vector3f(scale));
		entity.setBaseName(getName());
		return entity;
	}

	public boolean isMoved() {
		return isMoved;
	}

	public void setMoved(boolean isMoved) {
		this.isMoved = isMoved;
	}
	
	public void delete() {
		if (parent != null) {
			parent.removeEntity(getName());
			parent = null;
		}
		models.clear();
	}

}
