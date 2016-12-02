package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public interface Entity {
	
	public void setIsDetail(boolean value);	
	public boolean isDetail();
	
	public boolean isVisible();
	public void setVisible(boolean isVisible);

	public boolean isRendered();
	public void setRendered(boolean isRendered);

	public float getTextureXOffset();	
	public float getTextureYOffset();
	

	
	public void increasePosition(float dx, float dy, float dz);
	
	public void increaseRotation(float dx, float dy, float dz);
	
	public String getName();
	public int getType();
	
	public TexturedModel getModel();
	public void setModel(TexturedModel model);
	
	public Vector3f getPosition();
	public void setPosition(Vector3f position);
	
	public float getRotX();
	public void setRotX(float rotX);
	public float getRotY();
	public void setRotY(float rotY);
	public float getRotZ();
	public void setRotZ(float rotZ);
	public float getScale();
	public void setScale(float scale);
	
	
}
