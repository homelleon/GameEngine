package object.particle;

import org.lwjgl.util.vector.Vector3f;

public interface IParticleSystem {
	
	/**
	 * Gets particle system name.
	 * 
	 * @return String value of particle system name
	 */
	public String getName();
	
	/**
	 * @param direction
	 *            - The average direction in which particles are emitted.
	 * @param deviation
	 *            - A value between 0 and 1 indicating how far from the chosen
	 *            direction particles can deviate.
	 */
	public void setDirection(Vector3f direction, float deviation);

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

	public void setPosition(Vector3f position);
	
	public Vector3f getPosition();

	public void generateParticles();

}
