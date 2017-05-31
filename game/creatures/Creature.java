package creatures;

import org.lwjgl.util.vector.Vector3f;

import bodies.BodyInterface;
import objects.entities.Entity;
import objects.models.TexturedModel;

public abstract class Creature extends Entity implements EntityDamagable, EntityWithPhysics {
	
	protected int helth;
	protected BodyInterface body;

	public Creature(String name, TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale) {
		super(name, model, position, rotX, rotY, rotZ, scale);
		
	}
	
	public int getHelth() { 
		return helth;
	}
	
	public void setHelth(int value) {
		this.helth = value;
	}
	
	public void getDamage(float value) {}
	
	public void createBody() {};
	
	public BodyInterface getBody() {
		return body;
	}
	
}
