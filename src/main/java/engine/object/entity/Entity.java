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
		 return this.parent;
	 }
	 
	 public boolean removeParentNode() {
		 if (hasParent()) {
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
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	public void move(float forwardSpeed, float strafeSpeed) {
		this.isMoved = false;
		if(forwardSpeed > 0 || strafeSpeed > 0) {
			this.isMoved = true;
		}
		float dx = (float) (forwardSpeed * Math.sin(Math.toRadians(((Vector3f)this.rotation).getY()))
				+ (strafeSpeed * Math.sin(Math.toRadians(((Vector3f) this.rotation).getY() + 90))));
		float dz = (float) (forwardSpeed * Math.cos(Math.toRadians(((Vector3f) this.rotation).getY()))
				+ (strafeSpeed * Math.cos(Math.toRadians(((Vector3f) this.rotation).getY() + 90))));
		increasePosition(dx, 0, dz);
	}

	public void increaseRotation(float dx, float dy, float dz) {
		this.rotation.x += dx;
		this.rotation.y += dy;
		this.rotation.z += dz;
	}
	
	public String getBaseName() {
		return this.baseName;
	}
	
	public void setBaseName(String name) {
		this.baseName = name;
	}
	
	@Override
	public Entity setScale(Vector3f scale) {
		this.radius =  models.get(0).getMesh().getBoundSphere().getRadius() * scale.x;
		return (Entity) super.setScale(scale);
	}

	public float getSphereRadius() {
		return this.radius;
	}
	
	public Entity clone(String name) {
		Entity entity = new Entity(name, shader, models, this.textureIndex, new Vector3f((Vector3f) this.position), new Vector3f(this.rotation), new Vector3f(this.scale));
		entity.setBaseName(this.getName());
		return entity;
	}

	public boolean isMoved() {
		return isMoved;
	}

	public void setMoved(boolean isMoved) {
		this.isMoved = isMoved;
	}
	
	public void delete() {
		if (this.parent != null) {
			this.parent.removeEntity(this.getName());
			this.parent = null;
		}
		this.models.clear();
	}

}
