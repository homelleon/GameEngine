package object.bounding;

import org.lwjgl.util.vector.Vector2f;

public class BoundingQuad {
	
	private Vector2f leftPoint;
	private Vector2f rightPoint;
	
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
		return this.leftPoint;
	}
	
	/**
	 * Gets right-up point for the bounding quad.
	 *  
	 * @return {@link Vector2f} rightPoint value
	 */
	public Vector2f getRightPoint() {
		return this.rightPoint;
	}

	/**
	 * Changes position of bounding quad intersection points.
	 * 
	 * @param position {@link Vector2f} value that indicates in what direction
	 * and at what length the bounding quad intersection point should be moved
	 */
	public void move(Vector2f position) {
		this.leftPoint = Vector2f.add(this.leftPoint, position, null);
		this.rightPoint = Vector2f.add(this.rightPoint, position, null);
		System.out.println(leftPoint);
		System.out.println(rightPoint);
	}
	
	public BoundingQuad clone() {
		return new BoundingQuad(this.leftPoint, this.rightPoint);
	}

}
