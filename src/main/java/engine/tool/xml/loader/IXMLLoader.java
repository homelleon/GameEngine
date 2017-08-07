package tool.xml.loader;

import org.w3c.dom.Document;

/**
 * 
 * @author homelleon
 * @see XMLFileLoader
 */
public interface IXMLLoader {

	/**
	 * @return {@link Document} value of structured XML text
	 */
	public Document load();

}
