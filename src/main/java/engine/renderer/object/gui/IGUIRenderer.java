package renderer.object.gui;

import java.util.Collection;

import object.gui.group.IGUIGroup;

/**
 * 
 * @author homelleon
 * @see GUIRenderer
 */
public interface IGUIRenderer {

	void render(Collection<IGUIGroup> groupCollection);

	void cleanUp();

}
