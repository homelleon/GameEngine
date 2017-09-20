package object.particle.particle;

import core.display.DisplayManager;
import core.settings.EngineSettings;
import object.camera.ICamera;
import object.particle.master.ParticleMaster;
import object.texture.particle.ParticleTexture;
import tool.math.vector.Vec2f;
import tool.math.vector.Vec3f;

public class Particle {

	private Vec3f position;
	private Vec3f velocity;
	private float gravityEffect;
	private float lifeLength;
	private float rotation;
	private float scale;

	private float elapsedTime = 0;

	private ParticleTexture texture;

	private Vec2f texOffset1 = new Vec2f();
	private Vec2f texOffset2 = new Vec2f();
	private float blend;
	private float distance;

	public Particle(ParticleTexture texture, Vec3f position, Vec3f velocity, float gravityEffect,
			float lifeLength, float rotation, float scale) {
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		this.texture = texture;
		ParticleMaster.addParticle(this);
	}

	public float getDistance() {
		return distance;
	}

	public Vec2f getTexOffset1() {
		return texOffset1;
	}

	public Vec2f getTexOffset2() {
		return texOffset2;
	}

	public float getBlend() {
		return blend;
	}

	public ParticleTexture getTexture() {
		return texture;
	}

	public Vec3f getPosition() {
		return position;
	}

	public float getRotation() {
		return rotation;
	}

	public float getScale() {
		return scale;
	}

	public boolean update(ICamera camera) {
		velocity.y += EngineSettings.GRAVITY * gravityEffect * DisplayManager.getFrameTimeSeconds();
		Vec3f change = new Vec3f(velocity);
		change.scale(DisplayManager.getFrameTimeSeconds());
		position.add(change);
		updateTextureCoordInfo();
		distance = Vec3f.sub(camera.getPosition(), position).lengthSquared();
		elapsedTime += DisplayManager.getFrameTimeSeconds();
		return elapsedTime < lifeLength;
	}

	private void updateTextureCoordInfo() {
		float lifeFactor = elapsedTime / lifeLength;
		int stageCount = texture.getNumberOfRows() * texture.getNumberOfRows();
		float atlasProgression = lifeFactor * stageCount;
		int index1 = (int) Math.floor(atlasProgression);
		int index2 = index1 < stageCount - 1 ? index1 + 1 : index1;
		this.blend = atlasProgression % 1;
		setTextureOffset(texOffset1, index1);
		setTextureOffset(texOffset2, index2);

	}

	private void setTextureOffset(Vec2f offset, int index) {
		int column = index % texture.getNumberOfRows();
		int row = index / texture.getNumberOfRows();
		offset.x = (float) column / texture.getNumberOfRows();
		offset.y = (float) row / texture.getNumberOfRows();
	}

}