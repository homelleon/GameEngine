package userInterfaces;

import java.util.Collection;

public interface UIManager {
	
	void add(UI ui);
	void addAll(Collection<UI> uiList);
	
	UI getByName(String name);
	Collection<UI> getAll();
	
	void cleanUp();	
}
