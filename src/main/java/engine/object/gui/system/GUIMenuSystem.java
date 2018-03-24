package object.gui.system;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import object.gui.Hideable;
import object.gui.element.menu.IGUIMenu;

/**
 * Realizes unique system of storage and accessig the graphic user interface menus.
 * 
 * @author homelleon
 * @see IGUIMenuSystem
 * @version 1.0.1
 */
public class GUIMenuSystem {
	
	private static GUIMenuSystem instance;
	Map<String, IGUIMenu> menus = new HashMap<String, IGUIMenu>();
	private IGUIMenu activeMenu;
	private boolean isVisible = false;
	
	public static GUIMenuSystem getInstace() {
		if(instance == null) {
			instance = new GUIMenuSystem();
		}
		return instance;
	}
	
	private GUIMenuSystem() {}
	
	public void add(IGUIMenu menu) {
		this.menus.put(menu.getName(), menu);
	}

	public void addAll(Collection<IGUIMenu> menuList) {
		this.menus.putAll(menuList.stream()
				.collect(Collectors.toMap(
						IGUIMenu::getName, Function.identity())));
	}

	public IGUIMenu get(String name) {
		return this.menus.get(name);
	}

	public Collection<IGUIMenu> getAll() {
		return this.menus.values();
	}	

	public IGUIMenu active(String name) {
		return this.activeMenu = this.menus.get(name);
	}	
	
	public IGUIMenu getActivated() {
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
		menus.values().forEach(IGUIMenu::clean);
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
