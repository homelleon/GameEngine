package tool.xml.parser;

import java.util.List;

/**
 * 
 * @author homelleon
 * @param <E>
 */
public interface ListParserInterface<E> extends ObjectParserInterface<List<E>> {

	List<E> parse();

}
