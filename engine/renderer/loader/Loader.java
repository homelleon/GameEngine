package renderer.loader;

/**
 * Class to load vertices information and textures into video buffer.
 * 
 * @author homelleon
 *
 */
public class Loader {

	private static Loader instance;

	private Loader() {
	};

	/**
	 * Gets instance of loader class object.
	 * 
	 * @return {@link Loader} class object
	 */
	public static Loader getInstance() {
		if (instance == null) {
			instance = new Loader();
		}
		return instance;
	}
	
	private GameTextureLoader textureLoader = new GameTextureLoader();
	private VertexBufferLoader vertexLoader = new VertexBufferLoader();

	
	public GameTextureLoader getTextureLoader() {
		return this.textureLoader;
	}
	
	public VertexBufferLoader getVertexLoader() {
		return this.vertexLoader;		
	}
	
	public void clean() {
		this.textureLoader.clean();
		this.vertexLoader.clean();
	}

}
