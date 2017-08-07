package object.gui.text.loader;

import java.util.List;

import object.gui.text.GUIText;

/**
 * 
 * @author homelleon
 * @see GUITextXMLLoader
 */
public interface IGUITextLoader {

	List<GUIText> loadFile(String fileName);

}
