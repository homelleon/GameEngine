package tool.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import object.Nameable;

public abstract class AbstractManager<E extends Nameable> {
	
	protected Map<String, E> elements = new HashMap<String, E>();
	
	public void add(E element) {
		this.elements.put(((Nameable) element).getName(), element);
	}

	public void addAll(List<E> elementList) {
		elementList.forEach(element -> elements.put(((Nameable) element).getName(), element));
	}

	public void addAll(Collection<E> elementList) {
		elementList.forEach(element -> elements.put(((Nameable) element).getName(), element));
	}

	public E get(String name) {
		if(this.elements.containsKey(name)) {
			return this.elements.get(name);
		} else {
			throw new NullPointerException(name + " element wasn't found in " + this.getClass().getName() + "!");
		}
	}
	
	public Collection<E> getAll() {
		return this.elements.values();
	}
	
	public boolean delete(String name) {
		if(this.elements.containsKey(name)) {
			this.elements.remove(name);
			return true;
		} else {
			return false;
		}
	}
	
	public void clean() {
		this.elements.clear();
	}

}
