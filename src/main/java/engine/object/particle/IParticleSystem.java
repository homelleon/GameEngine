package object.particle;

import object.Nameable;
import tool.math.vector.Vector3fF;

public interface IParticleSystem extends Nameable {

	
	/**
	 * @param direction
	 *            - The average direction in which particles are emitted.
	 * @param deviation
	 *            - A value between 0 and 1 indicating how far from the chosen
	 *            direction particles can deviate.
	 */
	public void setDirection(Vector3fF direction, float deviation);

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

	public void setPosition(Vector3fF position);
	
	public Vector3fF getPosition();

	public void generateParticles();

}
