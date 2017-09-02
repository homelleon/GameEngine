package object.gui.pattern.button;

import org.lwjgl.util.vector.Vector2f;

@FunctionalInterface
public interface IGUIAnimation {
	
	void start(IGUIButton button, int time, Vector2f value);

}
