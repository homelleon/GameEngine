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
public interface ILevelMapWriter {

	public void write(IObjectManager map);

}
