package renderer.object.gui;

import java.util.Collection;

import object.gui.group.GUIGroupInterface;

/**
 * 
 * @author homelleon
 * @see GUIRenderer
 */
public interface GUIRendererInterface {
	
	void render(Collection<GUIGroupInterface> groupCollection);
	void cleanUp();

}
