package object.particle;

import java.util.Random;

import org.lwjgl.util.vector.Vector4f;

import core.display.DisplayManager;
import object.particle.particle.Particle;
import object.texture.particle.ParticleTexture;
import tool.math.Matrix4f;
import tool.math.vector.Vector3f;

public class ParticleSystem implements IParticleSystem {

	private String name;

	private float pps, averageSpeed, gravityComplient, averageLifeLength, averageScale;

	private float speedError, lifeError, scaleError = 0;
	private boolean randomRotation = false;
	private Vector3f direction;
	private Vector3f position;
	private float directionDeviation = 0;

	private ParticleTexture texture;

	private Random random = new Random();

	public ParticleSystem(String name, ParticleTexture texture, float pps, float speed, float gravityComplient,
			float lifeLength, float scale) {
		this.name = name;
		this.pps = pps;
		this.averageSpeed = speed;
		this.gravityComplient = gravityComplient;
		this.averageLifeLength = lifeLength;
		this.averageScale = scale;
		this.texture = texture;
		this.position = new Vector3f(0, 0, 0);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setDirection(Vector3f direction, float deviation) {
		this.direction = new Vector3f(direction);
		this.directionDeviation = (float) (deviation * Math.PI);
	}

	public void randomizeRotation() {
		randomRotation = true;
	}

	@Override
	public void setSpeedError(float error) {
		this.speedError = error * averageSpeed;
	}

	@Override
	public void setLifeError(float error) {
		this.lifeError = error * averageLifeLength;
	}

	@Override
	public void setScaleError(float error) {
		this.scaleError = error * averageScale;
	}

	@Override
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	@Override
	public Vector3f getPosition() {
		return position;
	}

	@Override
	public void generateParticles() {
		float delta = DisplayManager.getFrameTimeSeconds();
		float particlesToCreate = pps * delta;
		int count = (int) Math.floor(particlesToCreate);
		float partialParticle = particlesToCreate % 1;
		for (int i = 0; i < count; i++) {
			emitParticle(position);
		}
		if (Math.random() < partialParticle) {
			emitParticle(position);
		}
	}

	private void emitParticle(Vector3f center) {
		Vector3f velocity = (direction != null) ? 
				generateRandomUnitVecWithinCone(direction, directionDeviation) :
				generateRandomUnitVec();
		velocity.normalize();
		velocity.scale(generateValue(averageSpeed, speedError));
		float scale = generateValue(averageScale, scaleError);
		float lifeLength = generateValue(averageLifeLength, lifeError);
		new Particle(texture, new Vector3f(center), velocity, gravityComplient, lifeLength, generateRotation(), scale);
	}

	private float generateValue(float average, float errorMargin) {
		float offset = (random.nextFloat() - 0.5f) * 2f * errorMargin;
		return average + offset;
	}

	private float generateRotation() {
		return randomRotation ? random.nextFloat() * 360f : 0;
	}

	private static Vector3f generateRandomUnitVecWithinCone(Vector3f coneDirection, float angle) {
		float cosAngle = (float) Math.cos(angle);
		Random random = new Random();
		float theta = (float) (random.nextFloat() * 2f * Math.PI);
		float z = cosAngle + (random.nextFloat() * (1 - cosAngle));
		float rootOneMinusZSquared = (float) Math.sqrt(1 - z * z);
		float x = (float) (rootOneMinusZSquared * Math.cos(theta));
		float y = (float) (rootOneMinusZSquared * Math.sin(theta));

		Vector4f direction = new Vector4f(x, y, z, 1);
		if (coneDirection.x != 0 || coneDirection.y != 0 || (coneDirection.z != 1 && coneDirection.z != -1)) {
			Vector3f rotateAxis = Vector3f.cross(coneDirection, new Vector3f(0, 0, 1));
			rotateAxis.normalize();
			float rotateAngle = (float) Math.acos(Vector3f.dot(coneDirection, new Vector3f(0, 0, 1)));
			Matrix4f rotationMatrix = new Matrix4f();
			rotationMatrix.rotate(-rotateAngle, rotateAxis);
			direction = rotationMatrix.transform(direction);
		} else if (coneDirection.z == -1) {
			direction.z *= -1;
		}
		return new Vector3f(direction);
	}

	private Vector3f generateRandomUnitVec() {
		float theta = (float) (random.nextFloat() * 2f * Math.PI);
		float z = (random.nextFloat() * 2) - 1;
		float rootOneMinusZSquared = (float) Math.sqrt(1 - z * z);
		float x = (float) (rootOneMinusZSquared * Math.cos(theta));
		float y = (float) (rootOneMinusZSquared * Math.sin(theta));
		return new Vector3f(x, y, z);
	}

}