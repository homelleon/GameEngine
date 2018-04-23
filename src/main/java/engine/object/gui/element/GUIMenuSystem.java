package object.gui.element;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import object.gui.Hideable;

/**
 * Realizes unique system of storage and accessig the graphic user interface menus.
 * 
 * @author homelleon
 * @see IGUIMenuSystem
 * @version 1.0.1
 */
public class GUIMenuSystem {
	
	private static GUIMenuSystem instance;
	private Map<String, GUIMenu> menus = new HashMap<String, GUIMenu>();
	private GUIMenu activeMenu;
	private boolean isVisible = false;
	
	public static GUIMenuSystem getInstace() {
		if (instance == null) {
			instance = new GUIMenuSystem();
		}
		return instance;
	}
	
	private GUIMenuSystem() {}
	
	public void add(GUIMenu menu) {
		menus.put(menu.getName(), menu);
	}

	public void addAll(Collection<GUIMenu> menuList) {
		menus.putAll(menuList.stream()
				.collect(Collectors.toMap(
						GUIMenu::getName, Function.identity())));
	}

	public GUIMenu get(String name) {
		return this.menus.get(name);
	}

	public Collection<GUIMenu> getAll() {
		return menus.values();
	}	

	public GUIMenu active(String name) {
		return activeMenu = menus.get(name);
	}	
	
	public GUIMenu getActivated() {
		return activeMenu;
	}

	public boolean delete(String name) {
		if (!menus.containsKey(name)) return false;
		menus.remove(name);
		return true;
	}

	public void clean() {
		menus.values().forEach(GUIMenu::clean);
		menus.clear();
	}
	
	public void show(String name) {
		if (!menus.containsKey(name)) return;
		menus.get(name).show();
	}
	
	public void hide(String name) {
		if (!menus.containsKey(name)) return;
		menus.get(name).hide();
	}

	public void show() {
		isVisible = true;
		menus.values().forEach(Hideable::show);
	}

	public void hide() {
		isVisible = false;
		menus.values().forEach(Hideable::hide);
	}

	public void switchVisibility() {
		if (isVisible) {
			hide();
		} else {
			show();
		}
	}

	public boolean getIsVisible() {
		return isVisible;
	}

}
