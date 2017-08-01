package object.gui.pattern.menu.system;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import object.gui.pattern.menu.GUIMenuInterface;
import object.gui.pattern.object.GUIObject;

public class GUIMenuSystem implements GUIMenuSystemInterface {
	
	Map<String, GUIMenuInterface> menus = new HashMap<String, GUIMenuInterface>();
	GUIMenuInterface activeMenu;

	@Override
	public void add(GUIMenuInterface menu) {
		this.menus.put(((GUIObject) menu).getName(), menu);		
	}

	@Override
	public void addAll(List<GUIMenuInterface> menuList) {
		menuList.forEach(menu -> this.menus.put(((GUIObject) menu).getName(), menu));
	}

	@Override
	public GUIMenuInterface get(String name) {
		return this.menus.get(name);
	}

	@Override
	public Collection<GUIMenuInterface> getAll() {
		return this.menus.values();
	}	

	@Override
	public GUIMenuInterface active(String name) {
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
