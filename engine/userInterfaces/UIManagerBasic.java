package userInterfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fontMeshCreator.GuiText;
import guis.GuiTexture;

public class UIManagerBasic implements UIManager {
	
	private static final String TXT_FILE_NAME = "GUITexts";
	private static final String TEXTURE_FILE_NAME = "GUITextures";
	
	UIComponentManager componentManager;
	Map<String, UIGroup> groups = new HashMap<String, UIGroup>();
	
	public UIManagerBasic(UIComponentManager componentManager, 
			List<UIGroup> groupList) {
		this.componentManager = componentManager;
		for(UIGroup group : groupList) {
			this.groups.put(group.getName(), group);
		}
	}
	
	public UIManagerBasic(UIComponentManager componentManager) {
		this.componentManager = componentManager;
	}
	
	public UIManagerBasic() {
		System.out.println("Prepare User Interface...");
		this.componentManager = new UIComponentManagerBasic(TEXTURE_FILE_NAME, TXT_FILE_NAME);
		
		GuiText txt1 = componentManager.getTexts().getByName("firstText");
		GuiText txt2 = componentManager.getTexts().getByName("secondText");
		List<GuiTexture> textures = new ArrayList<GuiTexture>();
		List<GuiText> texts = new ArrayList<GuiText>();	
		texts.add(txt1);
		texts.add(txt2);
		UI ui1 = new UISimple("UI1", textures, texts);	
		System.out.println("Succed!");
	}

	@Override
	public UIGroup getUIGroup(String name) {
		return groups.get(name);
	}
	
	@Override
	public void addUIGroup(Collection<UIGroup> groupList) {
		for(UIGroup group : groupList) {
			this.groups.put(group.getName(), group);
		}		
	}
	
	@Override
	public UIComponentManager getComponentManager() {
		return this.componentManager;
	}

	@Override
	public void cleanAll() {
		for(UIGroup group : this.groups.values()) {
			group.hideAll();
			group.cleanAll();
		}
		this.groups.clear();
	}		

}
