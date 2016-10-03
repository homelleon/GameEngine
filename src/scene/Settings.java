package scene;

public class Settings {
	
	//*****************DISPLAY SETTINGS**********************************//
	public static final int DISPLAY_WIDTH = 1024;
	public static final int DISPLAY_HEIGHT = 768; 
	public static final int FPS_CAP = 120;
	
	//****************AUIDO SETTINGS****************************************//
	public static final float MUSIC_VOLUME = 0.05f;
	public static final float ENVIRONMENT_VOLUME = 0.2f;
	
	//****************CONTROLS SETTINGS**************************************//
	public static final float MOUSE_X_SPEED = 0.2f;
	public static final float MOUSE_Y_SPEED = 0.2f;
	public static final float MOUSE_ZOOM_SPEED = 0.1f;
	//*****************SIMULATION SETTINGS*************************************//
	public static final float GRAVITY = -50;
	public static final float TIME_LENGTH = 1;
	
	//*****************PATH SETTINGS*************************************//
	public final static String RES_PATH = "res/";
	public final static String MODEL_TEXTURE_PATH = "res/textures/modelTextures/";
	public final static String TERRAIN_TEXTURE_PATH = "res/textures/terrainTextures/";
	public final static String INTERFACE_TEXTURE_PATH = "res/textures/interface/";
	public final static String BLEND_MAP_PATH = "res/textures/blendMaps/";
	public final static String HEIGHT_MAP_PATH = "res/textures/heightMaps/";
	public final static String DUDV_MAP_PATH = "res/textures/DUDVs/";
	public final static String NORMAL_MAP_PATH = "res/textures/normalMaps/";
	public final static String FONT_PATH = "res/textures/fonts/";
	public final static String SKYBOX_TEXTURE_PATH = "res/textures/skybox/";
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
