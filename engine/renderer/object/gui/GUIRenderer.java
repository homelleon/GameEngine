package renderer.object.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import object.gui.font.manager.FontManagerInterface;
import object.gui.group.GUIGroupInterface;
import object.gui.gui.GUIInterface;

/**
 * Graphic user interface renderer class to render GUI groups.
 * 
 * @author homelleon
 *
 */
public class GUIRenderer implements GUIRendererInterface {
	
	private GUITextureRenderer textureRenderer;
	private GUITextRenderer textRenderer;
	Map<Integer, List<GUIGroupInterface>> groups;
	
	public GUIRenderer(FontManagerInterface fontManager) {
		this.textureRenderer = new GUITextureRenderer();
		this.textRenderer = new GUITextRenderer(fontManager);
		this.groups = new HashMap<Integer, List<GUIGroupInterface>>();
	}
	
	@Override
	public void render(Collection<GUIGroupInterface> groupCollection) {
		for(GUIGroupInterface group : groupCollection) {
			processGroup(group);
		}
		List<Integer> keys = new ArrayList<Integer>(); 
		keys.addAll(this.groups.keySet());
		Collections.sort(keys);
		for(int i = 0; i < keys.size(); i++) {
			for(GUIGroupInterface group : this.groups.get(keys.get(i))) {
				for(GUIInterface gui : group.getAll()) {
					if(gui.getIsShown()) {
						this.textureRenderer.render(gui.getTextures());
						this.textRenderer.render(gui.getTexts());
					}
				}
			}
		}
		this.groups.clear();
	}

	@Override
	public void cleanUp() {
		this.textureRenderer.cleanUp();
		this.textRenderer.cleanUp();		
	}
	
	private void processGroup(GUIGroupInterface group) {
		int priorityNumber = group.getPriorityNumber();
		List<GUIGroupInterface> batch = this.groups.get(priorityNumber);
		if(batch!= null) {			
			batch.add(group);
		} else {
			List<GUIGroupInterface> newBatch = new ArrayList<GUIGroupInterface>();
			newBatch.add(group);
			this.groups.put(priorityNumber, newBatch);
		}
	}

}
