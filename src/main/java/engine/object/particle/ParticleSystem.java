package object.particle;

import java.util.Random;

import org.lwjgl.util.vector.Vector4f;

import core.display.DisplayManager;
import object.particle.particle.Particle;
import object.texture.particle.ParticleMaterial;
import tool.math.VMatrix4f;
import tool.math.vector.Vector3fF;

public class ParticleSystem implements IParticleSystem {

	private String name;

	private float pps, averageSpeed, gravityComplient, averageLifeLength, averageScale;

	private float speedError, lifeError, scaleError = 0;
	private boolean randomRotation = false;
	private Vector3fF direction;
	private Vector3fF position;
	private float directionDeviation = 0;

	private ParticleMaterial texture;

	private Random random = new Random();

	public ParticleSystem(String name, ParticleMaterial texture, float pps, float speed, float gravityComplient,
			float lifeLength, float scale) {
		this.name = name;
		this.pps = pps;
		this.averageSpeed = speed;
		this.gravityComplient = gravityComplient;
		this.averageLifeLength = lifeLength;
		this.averageScale = scale;
		this.texture = texture;
		this.position = new Vector3fF(0, 0, 0);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setDirection(Vector3fF direction, float deviation) {
		this.direction = new Vector3fF(direction);
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
	public void setPosition(Vector3fF position) {
		this.position = position;
	}

	@Override
	public Vector3fF getPosition() {
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

	private void emitParticle(Vector3fF center) {
		Vector3fF velocity = (direction != null) ? 
				generateRandomUnitVecWithinCone(direction, directionDeviation) :
				generateRandomUnitVec();
		velocity.normalize();
		velocity.scale(generateValue(averageSpeed, speedError));
		float scale = generateValue(averageScale, scaleError);
		float lifeLength = generateValue(averageLifeLength, lifeError);
		new Particle(texture, new Vector3fF(center), velocity, gravityComplient, lifeLength, generateRotation(), scale);
	}

	private float generateValue(float average, float errorMargin) {
		float offset = (random.nextFloat() - 0.5f) * 2f * errorMargin;
		return average + offset;
	}

	private float generateRotation() {
		return randomRotation ? random.nextFloat() * 360f : 0;
	}

	private static Vector3fF generateRandomUnitVecWithinCone(Vector3fF coneDirection, float angle) {
		float cosAngle = (float) Math.cos(angle);
		Random random = new Random();
		float theta = (float) (random.nextFloat() * 2f * Math.PI);
		float z = cosAngle + (random.nextFloat() * (1 - cosAngle));
		float rootOneMinusZSquared = (float) Math.sqrt(1 - z * z);
		float x = (float) (rootOneMinusZSquared * Math.cos(theta));
		float y = (float) (rootOneMinusZSquared * Math.sin(theta));

		Vector4f direction = new Vector4f(x, y, z, 1);
		if (coneDirection.x != 0 || coneDirection.y != 0 || (coneDirection.z != 1 && coneDirection.z != -1)) {
			Vector3fF rotateAxis = Vector3fF.cross(coneDirection, new Vector3fF(0, 0, 1));
			rotateAxis.normalize();
			float rotateAngle = (float) Math.acos(Vector3fF.dot(coneDirection, new Vector3fF(0, 0, 1)));
			VMatrix4f rotationMatrix = new VMatrix4f();
			rotationMatrix.rotate(-rotateAngle, rotateAxis);
			direction = rotationMatrix.transform(direction);
		} else if (coneDirection.z == -1) {
			direction.z *= -1;
		}
		return new Vector3fF(direction);
	}

	private Vector3fF generateRandomUnitVec() {
		float theta = (float) (random.nextFloat() * 2f * Math.PI);
		float z = (random.nextFloat() * 2) - 1;
		float rootOneMinusZSquared = (float) Math.sqrt(1 - z * z);
		float x = (float) (rootOneMinusZSquared * Math.cos(theta));
		float y = (float) (rootOneMinusZSquared * Math.sin(theta));
		return new Vector3fF(x, y, z);
	}

}