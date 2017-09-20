package object;

import tool.math.vector.Vec;

public interface Moveable<T extends Vec> {
	
	void move(T position);
}
