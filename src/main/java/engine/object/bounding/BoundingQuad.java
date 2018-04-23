package object.bounding;

import tool.math.vector.Vector2f;

public class BoundingQuad 
{
	
	private Vector2f leftPoint = new Vector2f(0,0);
	private Vector2f rightPoint = new Vector2f(0,0);
	
	public BoundingQuad(Vector2f leftPoint, Vector2f rightPoint) {
		this.leftPoint = leftPoint;
		this.rightPoint = rightPoint;
	}
	
	/**
	 * Gets left-down point for the bounding quad.
	 *  
	 * @return {@link Vector2f} leftPoint value
	 */
	public Vector2f getLeftPoint() {
		return leftPoint;
	}
	
	/**
	 * Gets right-up point for the bounding quad.
	 *  
	 * @return {@link Vector2f} rightPoint value
	 */
	public Vector2f getRightPoint()	{
		return rightPoint;
	}

	/**
	 * Changes position of bounding quad intersection points.
	 * 
	 * @param position {@link Vector2f} value that indicates in what direction
	 * and at what length the bounding quad intersection point should be moved
	 */
	public void move(Vector2f position) {
		leftPoint.add(position);
		rightPoint.add(position);
	}
	
	public BoundingQuad clone()	{
		return new BoundingQuad(new Vector2f(leftPoint), new Vector2f(rightPoint));
	}

}
