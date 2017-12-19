package manager.gui.group;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import core.debug.EngineDebug;
import manager.gui.component.IGUIComponentManager;
import object.gui.group.GUIGroup;
import object.gui.group.IGUIGroup;

/**
 * Manages GUIGroups.
 * 
 * @author homelleon
 * 
 * @see IGUIGroupManager
 * @see GUIGroup
 * @see GUI
 */
public class GUIGroupManager implements IGUIGroupManager {

	Map<String, IGUIGroup> groups = new HashMap<String, IGUIGroup>();
	IGUIComponentManager componentManager;
	
	/**
	 * Contructes group manager with defined component manager.
	 * 
	 * @param componentManager {@link IGUIComponentManagaer} object
	 */
	public GUIGroupManager(IGUIComponentManager componentManager) {
		this.componentManager = componentManager;
	}
	
	@Override
	public IGUIGroup createEmpty(String name) {
		IGUIGroup group = new GUIGroup(name);
		this.groups.put(group.getName(), group);
		return group;
	}

	@Override
	public IGUIGroup get(String name) {
		return groups.get(name);
	}

	@Override
	public void addAll(Collection<IGUIGroup> groupList) {
		if(groupList!=null && !groupList.isEmpty()) {
			this.groups.putAll(
					groupList.stream()
						.collect(Collectors.toMap(
								IGUIGroup::getName, Function.identity())));
		} else {
			if(EngineDebug.hasDebugPermission()) {
				System.err.println(
						"Trying to add null collection value into GUIGroupManager array!");
			}
		}
	}

	@Override
	public void add(IGUIGroup group) {
		this.groups.put(group.getName(), group);
	}

	@Override
	public Collection<IGUIGroup> getAll() {
		return this.groups.values();
	}

	@Override
	public boolean delete(String name) {
		if (this.groups.containsKey(name)) {
			this.get(name).getAll().stream()
				.flatMap(gui -> gui.getTexts().stream())
				.forEach(text -> this.componentManager.getTexts().delete(text.getName()));
			this.get(name).getAll().stream()
				.flatMap(gui -> gui.getTextures().stream())
				.forEach(texture -> this.componentManager.getTextures().delete(texture.getName()));
			this.groups.get(name).clean();
			this.groups.remove(name);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void clean() {
		this.groups.values()
			.forEach(group -> group.clean());
		this.groups.clear();		
	}
}
