package object.gui.pattern.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import object.gui.pattern.button.IGUIButton;

public class GUIButtonManager implements IGUIButtonManager {

	private Map<String, IGUIButton> buttons = new HashMap<String, IGUIButton>();

	@Override
	public IGUIButton get(String name) {
		return this.buttons.get(name);
	}

	@Override
	public Collection<IGUIButton> getAll() {
		return this.buttons.values();
	}

	@Override
	public void add(IGUIButton button) {
		this.buttons.put(button.getName(), button);
	}

	@Override
	public void addAll(List<IGUIButton> buttonList) {
		buttonList.forEach(button -> this.buttons.put(button.getName(), button));
	}

	@Override
	public List<IGUIButton> getMouseOverButton(Vector2f mouseCoord) {
		List<IGUIButton> buttonList = new ArrayList<IGUIButton>();
		for (IGUIButton button : this.buttons.values()) {
			if (button.getIsMouseOver(mouseCoord)) {
				buttonList.add(button);
			}
		}
		return buttonList;
	}

	@Override
	public void clean() {
		this.buttons.clear();
	}

}
