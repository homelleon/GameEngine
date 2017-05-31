package objects.animation;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import objects.animatedModel.Joint;
import objects.openglObjects.Vao;
import objects.textures.Texture;

public class AnimatedEntity {
	
	private final Vao model;
	private final Texture texture;
	
	private final Joint rootJoint;
	private final int jointCount;
	
	private final Animator animator;
	
	public AnimantedEntity(Vao model, Texture texture, Joint rootJoint, int jointCount) {
		this.model = model;
		this.texture = texture;
		this.rootJoint = rootJoint;
		this.jointCount = jointCount;
		this.animator = new Animator(this);
		rootJoint.calcInverseBindTransform(
				new Matrix4f().rotate((float) Math.toRadians(-90), new Vector3f(1, 0, 0)));
	}
	
	public Vao getModel() {
		return this.model;
	}
	
	public Texture getTexture() {
		return this.texture;
	}
	
	public Joint getRootJoint() {
		return this.rootJoint;
	}
	
	public void delete() {
		model.delete();
		texture.delete();
	}
	
	public void doAnimation(Animation animation) {
		animator.doAnimation(animation);
	}
	
	public void update() {
		animator.update();
	}

}
