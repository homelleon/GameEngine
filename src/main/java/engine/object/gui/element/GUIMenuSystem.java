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
	Map<String, GUIMenu> menus = new HashMap<String, GUIMenu>();
	private GUIMenu activeMenu;
	private boolean isVisible = false;
	
	public static GUIMenuSystem getInstace() {
		if(instance == null) {
			instance = new GUIMenuSystem();
		}
		return instance;
	}
	
	private GUIMenuSystem() {}
	
	public void add(GUIMenu menu) {
		this.menus.put(menu.getName(), menu);
	}

	public void addAll(Collection<GUIMenu> menuList) {
		this.menus.putAll(menuList.stream()
				.collect(Collectors.toMap(
						GUIMenu::getName, Function.identity())));
	}

	public GUIMenu get(String name) {
		return this.menus.get(name);
	}

	public Collection<GUIMenu> getAll() {
		return this.menus.values();
	}	

	public GUIMenu active(String name) {
		return this.activeMenu = this.menus.get(name);
	}	
	
	public GUIMenu getActivated() {
		return this.activeMenu;
	}

	public boolean delete(String name) {
		if(this.menus.containsKey(name)) {
			this.menus.remove(name);
			return true;
		} else {
			return false; 
		}
	}

	public void clean() {
		menus.values().forEach(GUIMenu::clean);
		this.menus.clear();
	}
	
	public void show(String name) {
		if(this.menus.containsKey(name)) {
			this.menus.get(name).show();
		} else {
			throw new NullPointerException("There is no menu with "+ name + " in menu system!");
		}
	}
	
	public void hide(String name) {
		if(this.menus.containsKey(name)) {
			this.menus.get(name).hide();
		} else {
			throw new NullPointerException("There is no menu with "+ name + " in menu system!");
		}
	}

	public void show() {
		this.isVisible = true;
		this.menus.values().forEach(Hideable::show);
	}

	public void hide() {
		this.isVisible = false;
		this.menus.values().forEach(Hideable::hide);
	}

	public void switchVisibility() {
		if(this.isVisible) {
			this.hide();
		} else {
			this.show();
		}
	}

	public boolean getIsVisible() {
		return this.isVisible;
	}

}
