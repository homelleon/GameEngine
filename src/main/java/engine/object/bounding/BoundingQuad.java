package object.bounding;

import tool.math.vector.Vec2f;

public class BoundingQuad {
	
	private Vec2f leftPoint;
	private Vec2f rightPoint;
	
	public BoundingQuad(Vec2f leftPoint, Vec2f rightPoint) {
		this.leftPoint = leftPoint;
		this.rightPoint = rightPoint;
	}
	
	/**
	 * Gets left-down point for the bounding quad.
	 *  
	 * @return {@link Vec2f} leftPoint value
	 */
	public Vec2f getLeftPoint() {
		return this.leftPoint;
	}
	
	/**
	 * Gets right-up point for the bounding quad.
	 *  
	 * @return {@link Vec2f} rightPoint value
	 */
	public Vec2f getRightPoint() {
		return this.rightPoint;
	}

	/**
	 * Changes position of bounding quad intersection points.
	 * 
	 * @param position {@link Vec2f} value that indicates in what direction
	 * and at what length the bounding quad intersection point should be moved
	 */
	public void move(Vec2f position) {
		this.leftPoint = this.leftPoint.add(position);
		this.rightPoint = this.rightPoint.add(position);
	}
	
	public BoundingQuad clone() {
		return new BoundingQuad(this.leftPoint, this.rightPoint);
	}

}
