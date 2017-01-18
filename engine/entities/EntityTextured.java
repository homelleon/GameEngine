package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import scene.ES;

public class EntityTextured implements Entity {
	
	private TexturedModel model;
	private String name;
	private Vector3f position;
	private float rotX,rotY,rotZ;
	private float scale;
	private float radius = 1f;
	private boolean isDetail = false;
	private boolean isVisible = true;
	private boolean isChosen = false;
	
	private int textureIndex = 0;
	private int typeID = EntityManager.ENTITY_TYPE_SIMPLE;
	
	public EntityTextured(String name, TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.name = name;
		this.model = model;
		this.radius = model.getRawModel().getRadius() * scale;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale; 
	}
	
	public EntityTextured(String name, int typeID, TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.name = name;
		this.model = model;
		this.radius = model.getRawModel().getRadius() * scale;
		this.typeID = typeID;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale; 
	}
	
	public EntityTextured(String name, int typeID, TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.name = name;
		this.textureIndex = textureIndex;
		this.model = model;
		this.radius = model.getRawModel().getRadius() * scale;
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
