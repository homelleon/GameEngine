package object.gui.animation;

import tool.math.vector.Vector2f;

@FunctionalInterface
public interface GUIAnimation<T> {
	
	void start(T button, int time, Vector2f value);

}
