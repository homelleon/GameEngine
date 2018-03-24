package object.gui.text;

import java.util.List;

/**
 * 
 * @author homelleon
 * @see GUITextXMLLoader
 */
public interface GUITextLoader {

	List<GUIText> loadFile(String fileName);

}
