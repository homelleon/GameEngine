package object.gui.element.button;

import tool.math.vector.Vector2f;

@FunctionalInterface
public interface IGUIAnimation<T> {
	
	void start(T button, int time, Vector2f value);

}
