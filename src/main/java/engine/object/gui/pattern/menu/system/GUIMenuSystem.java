package object.gui.pattern.menu.system;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import object.gui.pattern.menu.IGUIMenu;
import object.gui.pattern.object.GUIObject;

public class GUIMenuSystem implements IGUIMenuSystem {
	
	Map<String, IGUIMenu> menus = new HashMap<String, IGUIMenu>();
	IGUIMenu activeMenu;

	@Override
	public void add(IGUIMenu menu) {
		this.menus.put(((GUIObject) menu).getName(), menu);		
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

}
