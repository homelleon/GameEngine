package renderer.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import manager.gui.font.IFontManager;
import object.gui.group.IGUIGroup;
import object.gui.gui.IGUI;
import renderer.gui.text.GUITextRenderer;
import renderer.gui.texture.GUITextureRenderer;

/**
 * Graphic user interface renderer class to render GUI groups.
 * 
 * @author homelleon
 *
 */
public class GUIRenderer implements IGUIRenderer {

	private GUITextureRenderer textureRenderer;
	private GUITextRenderer textRenderer;
	Map<Integer, List<IGUIGroup>> groups;

	public GUIRenderer(IFontManager fontManager) {
		this.textureRenderer = new GUITextureRenderer();
		this.textRenderer = new GUITextRenderer(fontManager);
		this.groups = new HashMap<Integer, List<IGUIGroup>>();
	}

	@Override
	public void render(Collection<IGUIGroup> groupCollection) {
		for (IGUIGroup group : groupCollection) {
			processGroup(group);
		}
		List<Integer> keys = new ArrayList<Integer>();
		keys.addAll(this.groups.keySet());
		Collections.sort(keys);
		IntStream.range(0, keys.size())
			.mapToObj(index -> this.groups.get(keys.get(index)))
			.flatMap(list -> list.stream())
			.flatMap(group -> group.getAll().stream())
			.filter(IGUI::getIsVisible)
			.forEach(gui -> {
				this.textureRenderer.render(gui.getTextures());
				this.textRenderer.render(gui.getTexts());
			});
		this.groups.clear();
	}

	@Override
	public void cleanUp() {
		this.textureRenderer.cleanUp();
		this.textRenderer.clean();
	}

	private void processGroup(IGUIGroup group) {
		int priorityNumber = group.getPriorityNumber();
		List<IGUIGroup> batch = this.groups.get(priorityNumber);
		if (batch != null) {
			batch.add(group);
		} else {
			List<IGUIGroup> newBatch = new ArrayList<IGUIGroup>();
			newBatch.add(group);
			this.groups.put(priorityNumber, newBatch);
		}
	}

}
