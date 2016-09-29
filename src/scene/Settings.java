package scene;

public class Settings {
	
	//*****************DISPLAY SETTINGS**********************************//
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768; 
	public static final int FPS_CAP = 120;
	
	//*****************SIMULATION SETTINGS*************************************//
	public static final float GRAVITY = 10;
	public static final float TIME_LENGTH = 1;
	
	//*****************PATH SETTINGS*************************************//
	public final static String RES_PATH = "res/";
	public final static String MODEL_TEXTURE_PATH = "res/textures/modelTextures/";
	public final static String TERRAIN_TEXTURE_PATH = "res/textures/terrainTextures/";
	public final static String INTERFACE_TEXTURE_PATH = "res/textures/interface/";
	public final static String BLEND_MAP_PATH = "res/textures/blendMaps/";
	public final static String HEIGHT_MAP_PATH = "res/textures/heightMaps/";
	public final static String SKYBOX_TEXTURE_PATH = "res/textures/skyBox/";
	public final static String AUDIO_PATH = "res/audio/";
	public final static String OBJECT_PATH = "res/objFiles/";
	public final static String SHADER_PATH = "src/shaders/";
	public final static String GUI_SHADER_PATH = "src/guis/";
	public final static String SKYBOX_SHADER_PATH = "src/skybox/";
	
	//*****************RENDER SETTINGS**********************************//

	
	//****************GLOBAL WORLD SETTINGS*********************************//
	public final static float SUN_MAX_HEIGHT = 4000; 
	public final static float SUN_MIN_HEIGHT = -4000;
	
	public Settings() {

	}
	
	
	

}
