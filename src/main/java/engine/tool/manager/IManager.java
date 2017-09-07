package tool.manager;

import java.util.Collection;
import java.util.List;

public interface IManager<E> {
	
	/**
	 * Adds element into manager.
	 * 
	 * @param element {@link E}
	 */
	void add(E element);
	
	/**
	 * Adds collection of elements into manager.
	 * 
	 * @param elementList {@link List}<{@link E}> array of elements
	 */
	void addAll(Collection<E> elementList);
	
	/**
	 * Gets element from manager by name.
	 * 
	 * @param name {@link String} value of element name
	 * @return {@link E} object
	 */
	E get(String name);
	
	/**
	 * Gets collection of elements from manager by name.
	 * @return {@link Collection}<{@link E}> array of elements
	 */
	Collection<E> getAll();
	
	/**
	 * Deletes element from manager by its name.
	 * 
	 * @param name {@link String} value of element name
	 * @return true if element was existed
	 * 		   false if elements wasn't extisted
	 */
	boolean delete(String name);
	
	/**
	 * Cleans manager elements array.
	 */
	void clean();
	
}
