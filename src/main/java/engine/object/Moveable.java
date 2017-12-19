package object;

import tool.math.vector.Vector;

public interface Moveable<T extends Vector> {
	
	void move(T position);
}
