package object.map.writer;

import object.scene.manager.IObjectManager;
import renderer.loader.Loader;

public interface IModelMapWriter {

	public void write(IObjectManager map, Loader loader);

}
