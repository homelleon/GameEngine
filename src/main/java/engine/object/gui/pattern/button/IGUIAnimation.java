package object.gui.pattern.button;

import org.lwjgl.util.vector.Vector2f;

@FunctionalInterface
public interface IGUIAnimation<T> {
	
	void start(T button, int time, Vector2f value);

}
