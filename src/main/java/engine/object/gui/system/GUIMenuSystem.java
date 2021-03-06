package object.gui.system;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import object.gui.Hideable;
import object.gui.pattern.menu.IGUIMenu;

/**
 * Realizes unique system of storage and accessig the graphic user interface menus.
 * 
 * @author homelleon
 * @see IGUIMenuSystem
 * @version 1.0.1
 */
public class GUIMenuSystem implements IGUIMenuSystem {
	
	private static IGUIMenuSystem instance;
	Map<String, IGUIMenu> menus = new HashMap<String, IGUIMenu>();
	private IGUIMenu activeMenu;
	private boolean isVisible = false;
	
	public static IGUIMenuSystem getInstace() {
		if(instance == null) {instance = new GUIMenuSystem();}
		return instance;
	}
	
	private GUIMenuSystem() {}
	
	@Override
	public void add(IGUIMenu menu) {
		this.menus.put(menu.getName(), menu);
	}

	@Override
	public void addAll(Collection<IGUIMenu> menuList) {
		this.menus.putAll(menuList.stream()
				.collect(Collectors.toMap(
						IGUIMenu::getName, Function.identity())));
	}

	@Override
	public IGUIMenu get(String name) {
		return this.menus.get(name);
	}

	@Override
	public Collection<IGUIMenu> getAll() {
		return this.menus.values();
	}	

	@Override
	public IGUIMenu active(String name) {
		return this.activeMenu = this.menus.get(name);
	}	
	
	@Override
	public IGUIMenu getActivated() {
		return this.activeMenu;
	}

	@Override
	public boolean delete(String name) {
		if(this.menus.containsKey(name)) {
			this.menus.remove(name);
			return true;
		} else {
			return false; 
		}
	}

	@Override
	public void clean() {
		menus.values().forEach(IGUIMenu::clean);
		this.menus.clear();
	}
	
	@Override
	public void show(String name) {
		if(this.menus.containsKey(name)) {this.menus.get(name).show();}
		else {
			throw new NullPointerException("There is no menu with "+ name + " in menu system!");
		}
	}
	
	@Override
	public void hide(String name) {
		if(this.menus.containsKey(name)) {this.menus.get(name).hide();}
		else {
			throw new NullPointerException("There is no menu with "+ name + " in menu system!");
		}
	}

	@Override
	public void show() {
		this.isVisible = true;
		this.menus.values().forEach(Hideable::show);
	}

	@Override
	public void hide() {
		this.isVisible = false;
		this.menus.values().forEach(Hideable::hide);
	}

	@Override
	public void switchVisibility() {
		if(this.isVisible) {this.hide();}
		else {this.show();}
	}

	@Override
	public boolean getIsVisible() {
		return this.isVisible;
	}

}
