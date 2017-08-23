package tool.xml.parser;

import java.util.List;

/**
 * 
 * @author homelleon
 * @param <E>
 */
@FunctionalInterface
public interface IListParser<E> extends IObjectParser<List<E>> {

	/**
	 * Parses data.
	 * <br>Returns list of elements.
	 */
	List<E> parse();

}
