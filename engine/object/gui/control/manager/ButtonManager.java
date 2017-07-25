package object.gui.control.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import object.gui.control.button.GUIButtonInterface;

public class ButtonManager implements ButtonManagerInterface {
	
	private Map<String, GUIButtonInterface> buttons = new HashMap<String, GUIButtonInterface>();

	@Override
	public GUIButtonInterface get(String name) {
		return this.buttons.get(name);
	}

	@Override
	public Collection<GUIButtonInterface> getAll() {
		return this.buttons.values();
	}

	@Override
	public void add(GUIButtonInterface button) {
		this.buttons.put(button.getName(), button);
	}

	@Override
	public void addAll(List<GUIButtonInterface> buttonList) {
		buttonList.forEach(button -> this.buttons.put(button.getName(), button));
	}

	@Override
	public List<GUIButtonInterface> getMouseOverButton(Vector2f mouseCoord) {
		List<GUIButtonInterface> buttonList = new ArrayList<GUIButtonInterface>();
		for(GUIButtonInterface button : this.buttons.values()) {
			if(button.getIsMouseOver(mouseCoord)) {
				buttonList.add(button);
			}
		}
		return buttonList;
	}

	@Override
	public void cleanAll() {
		this.buttons.clear();
	}

}
