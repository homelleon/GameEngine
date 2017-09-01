package object;

import org.lwjgl.util.vector.Vector;

public interface Moveable<T extends Vector> {
	
	void move(T position);
}
