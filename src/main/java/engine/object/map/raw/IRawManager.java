package object.map.raw;

import java.util.List;

import object.model.RawModel;
import object.texture.Texture;

public interface IRawManager {
	
	List<RawModel> getRawModels();
	List<Texture> getTextures();
	void clean();

}
