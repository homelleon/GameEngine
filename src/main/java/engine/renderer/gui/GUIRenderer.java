package renderer.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import manager.gui.FontManager;
import object.gui.GUI;
import object.gui.GUIGroup;

/**
 * Graphic user interface renderer class to render GUI groups.
 * 
 * @author homelleon
 *
 */
public class GUIRenderer {

	private GUITextureRenderer textureRenderer;
	private GUITextRenderer textRenderer;
	Map<Integer, List<GUIGroup>> groups;

	public GUIRenderer(FontManager fontManager) {
		this.textureRenderer = new GUITextureRenderer();
		this.textRenderer = new GUITextRenderer(fontManager);
		this.groups = new HashMap<Integer, List<GUIGroup>>();
	}

	public void render(Collection<GUIGroup> groupCollection) {
		for (GUIGroup group : groupCollection) {
			processGroup(group);
		}
		List<Integer> keys = new ArrayList<Integer>();
		keys.addAll(this.groups.keySet());
		Collections.sort(keys);
		IntStream.range(0, keys.size())
			.mapToObj(index -> this.groups.get(keys.get(index)))
			.flatMap(list -> list.stream())
			.flatMap(group -> group.getAll().stream())
			.filter(GUI::getIsVisible)
			.forEach(gui -> {
				this.textureRenderer.render(gui.getTextures());
				this.textRenderer.render(gui.getTexts());
			});
		this.groups.clear();
	}

	public void cleanUp() {
		this.textureRenderer.cleanUp();
		this.textRenderer.clean();
	}

	private void processGroup(GUIGroup group) {
		int priorityNumber = group.getPriorityNumber();
		List<GUIGroup> batch = this.groups.get(priorityNumber);
		if (batch != null) {
			batch.add(group);
		} else {
			List<GUIGroup> newBatch = new ArrayList<GUIGroup>();
			newBatch.add(group);
			this.groups.put(priorityNumber, newBatch);
		}
	}

}
