package tool.xml.parser;

import java.util.List;

/**
 * 
 * @author homelleon
 * @param <E>
 */
public interface IListParser<E> extends IObjectParser<List<E>> {

	List<E> parse();

}
