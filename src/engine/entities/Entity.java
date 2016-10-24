package engine.entities;

import org.lwjgl.util.vector.Vector3f;

import engine.models.TexturedModel;

public class Entity {
	
	private TexturedModel model;
	private String name;
	private Vector3f position;
	private float rotX,rotY,rotZ;
	private float scale;
	private boolean isDetail = false;
	
	private int textureIndex = 0;
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.name = "NoName";
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public Entity(String name, TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.name = name;
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public void setIsDetail(boolean value) {
		this.isDetail = value;
	}
	
	public boolean isDetail() {
		return isDetail;
	}
	
	public float getTextureXOffset() {
		int column = textureIndex % model.getTexture().getNumberOfRows();
		return (float)column/(float)model.getTexture().getNumberOfRows();
	}
	
	public float getTextureYOffset() {
		int row = textureIndex/model.getTexture().getNumberOfRows();
		return (float)row/(float)model.getTexture().getNumberOfRows();
	}
	
	public Entity(TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.textureIndex = textureIndex;
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x+=dx;
		this.position.y+=dy;
		this.position.z+=dz;
	}
	
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}
	
	public String getName() {
		return name;
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
	
	

}
