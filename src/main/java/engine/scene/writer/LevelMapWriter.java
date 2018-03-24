package scene.writer;

import manager.scene.IObjectManager;

/**
 * Provides access for writing file from object manager.
 * 
 * @author homelleon
 * 
 * @see LevelMapXMLWriter
 * @see ObjectManager
 */
public interface LevelMapWriter {

	public void write(IObjectManager map);

}
