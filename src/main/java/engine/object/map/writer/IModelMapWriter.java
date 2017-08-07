package object.map.writer;

import object.map.objectMap.IObjectManager;
import renderer.loader.Loader;

public interface IModelMapWriter {

	public void write(IObjectManager map, Loader loader);

}