package object.gui.system;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import object.gui.Hideable;
import object.gui.pattern.menu.IGUIMenu;
import object.gui.pattern.object.GUIObject;

public class GUIMenuSystem implements IGUIMenuSystem {
	
	Map<String, IGUIMenu> menus = new HashMap<String, IGUIMenu>();
	IGUIMenu activeMenu;
	private boolean isShown = false;

	@Override
	public void add(IGUIMenu menu) {
		this.menus.put(((GUIObject) menu).getName(), menu);		
	}

	@Override
	public void addAll(Collection<IGUIMenu> menuList) {
		menuList.forEach(menu -> this.menus.put(((GUIObject) menu).getName(), menu));
	}

	@Override
	public void addAll(List<IGUIMenu> menuList) {
		menuList.forEach(menu -> this.menus.put(((GUIObject) menu).getName(), menu));	
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
		this.activeMenu = this.menus.get(name);
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
		menus.values().forEach(menu -> menu.clean());
		this.menus.clear();
	}
	
	@Override
	public void show(String name) {
		if(this.menus.containsKey(name)) {
			this.menus.get(name).show();
		} else {
			throw new NullPointerException("There is no menu with "+ name + " in menu system!");
		}
	}
	
	@Override
	public void hide(String name) {
		if(this.menus.containsKey(name)) {
			this.menus.get(name).hide();
		} else {
			throw new NullPointerException("There is no menu with "+ name + " in menu system!");
		}
	}

	@Override
	public void show() {
		this.menus.values().forEach(object -> ((Hideable) object).show());
	}

	@Override
	public void hide() {
		this.menus.values().forEach(object -> ((Hideable) object).hide());		
	}

	@Override
	public boolean getIsShown() {
		return this.isShown;
	}

}