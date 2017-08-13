package object.map.writer;

import manager.scene.IObjectManager;
import renderer.loader.Loader;

public interface IModelMapWriter {

	public void write(IObjectManager map, Loader loader);

}
