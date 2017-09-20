package object.gui.pattern.button;

import tool.math.vector.Vec2f;

@FunctionalInterface
public interface IGUIAnimation<T> {
	
	void start(T button, int time, Vec2f value);

}
