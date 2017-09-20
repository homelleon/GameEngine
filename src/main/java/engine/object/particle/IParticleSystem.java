package object.particle;

import object.Nameable;
import tool.math.vector.Vec3f;

public interface IParticleSystem extends Nameable {

	
	/**
	 * @param direction
	 *            - The average direction in which particles are emitted.
	 * @param deviation
	 *            - A value between 0 and 1 indicating how far from the chosen
	 *            direction particles can deviate.
	 */
	public void setDirection(Vec3f direction, float deviation);

	public void randomizeRotation();

	/**
	 * @param error
	 *            - A number between 0 and 1, where 0 means no error margin.
	 */
	public void setSpeedError(float error);

	/**
	 * @param error
	 *            - A number between 0 and 1, where 0 means no error margin.
	 */
	public void setLifeError(float error);

	/**
	 * @param error
	 *            - A number between 0 and 1, where 0 means no error margin.
	 */
	public void setScaleError(float error);

	public void setPosition(Vec3f position);
	
	public Vec3f getPosition();

	public void generateParticles();

}
