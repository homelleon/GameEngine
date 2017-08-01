package tool.manager;

import java.util.Collection;
import java.util.List;

public interface ManagerInterface<E> {
	
	void add(E element);
	void addAll(List<E> elementList);
	void addAll(Collection<E> elementList);
	E get(String name);
	Collection<E> getAll();
	boolean delete(String name);
	void clean();
	
}
