package object.particle;

import core.display.DisplayManager;
import core.settings.EngineSettings;
import object.camera.Camera;
import primitive.texture.particle.ParticleMaterial;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

public class Particle {

	private Vector3f position;
	private Vector3f velocity;
	private float gravityEffect;
	private float lifeLength;
	private float rotation;
	private float scale;

	private float elapsedTime = 0;

	private ParticleMaterial material;

	private Vector2f texOffset1 = new Vector2f();
	private Vector2f texOffset2 = new Vector2f();
	private float blend;
	private float distance;

	public Particle(ParticleMaterial material, Vector3f position, Vector3f velocity, float gravityEffect,
			float lifeLength, float rotation, float scale) {
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		this.material = material;
		ParticleMaster.addParticle(this);
	}

	public float getDistance() {
		return distance;
	}

	public Vector2f getTexOffset1() {
		return texOffset1;
	}

	public Vector2f getTexOffset2() {
		return texOffset2;
	}

	public float getBlend() {
		return blend;
	}

	public ParticleMaterial getTexture() {
		return material;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getRotation() {
		return rotation;
	}

	public float getScale() {
		return scale;
	}

	public boolean update(Camera camera) {
		velocity.y += EngineSettings.GRAVITY * gravityEffect * DisplayManager.getFrameTimeSeconds();
		Vector3f change = new Vector3f(velocity);
		change.scale(DisplayManager.getFrameTimeSeconds());
		position.add(change);
		updateTextureCoordInfo();
		distance = Vector3f.sub(camera.getPosition(), position).lengthSquared();
		elapsedTime += DisplayManager.getFrameTimeSeconds();
		return elapsedTime < lifeLength;
	}

	private void updateTextureCoordInfo() {
		float lifeFactor = elapsedTime / lifeLength;
		int stageCount = material.getTexture().getNumberOfRows() * material.getTexture().getNumberOfRows();
		float atlasProgression = lifeFactor * stageCount;
		int index1 = (int) Math.floor(atlasProgression);
		int index2 = index1 < stageCount - 1 ? index1 + 1 : index1;
		this.blend = atlasProgression % 1;
		setTextureOffset(texOffset1, index1);
		setTextureOffset(texOffset2, index2);

	}

	private void setTextureOffset(Vector2f offset, int index) {
		int column = index % material.getTexture().getNumberOfRows();
		int row = index / material.getTexture().getNumberOfRows();
		offset.x = (float) column / material.getTexture().getNumberOfRows();
		offset.y = (float) row / material.getTexture().getNumberOfRows();
	}

}