package scene;

public class Settings {
	
	//*****************DISPLAY SETTINGS**********************************//
	public static final int DISPLAY_WIDTH = 1024;  
	public static final int DISPLAY_HEIGHT = 768; 
	public static final int FAR_PLANE = 100000;
	public static final float NEAR_PLANE = 0.1f;
	public static final int FPS_CAP = 120;
	public static final float FOV = 70;
	public static final int MULTISAMPLE = 4;
	
	public static final float DISPLAY_RED = 0.4f;
	public static final float DISPLAY_GREEN = 0.5f;
	public static final float DISPLAY_BLUE = 0.55f;
	
	public static final float DISPLAY_CONTRAST = 0.1f;
	
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
	public static final float FOG_DENSITY = 0.004f;
	public static final float SHADOW_DISTANCE = 120;
	public static final int SHADOW_MAP_SIZE = 4096;
	public static final float SHADOW_TRANSITION_DISTANCE = 80;
	public static final int SHADOW_PCF = 2;
	public static final float RENDERING_DISTANCE = 150;
	
	//*****************PATH SETTINGS*************************************//
	public final static String RES_PATH = "res/";
	public final static String SORCE_PATH = "/";
	public final static String MODEL_TEXTURE_PATH = RES_PATH + "textures/modelTextures/";
	public final static String TERRAIN_TEXTURE_PATH = RES_PATH + "textures/terrainTextures/";
	public final static String INTERFACE_TEXTURE_PATH = RES_PATH + "textures/interface/";
	public final static String PARTICLE_TEXTURE_PATH = RES_PATH + "textures/particles/";
	public final static String BLEND_MAP_PATH = RES_PATH + "textures/blendMaps/";
	public final static String HEIGHT_MAP_PATH = RES_PATH + "textures/heightMaps/";
	public final static String DUDV_MAP_PATH = RES_PATH + "textures/DUDVs/";
	public final static String NORMAL_MAP_PATH = RES_PATH + "textures/normalMaps/";
	public final static String FONT_PATH = RES_PATH + "textures/fonts/";
	public final static String SKYBOX_TEXTURE_PATH = RES_PATH + "textures/skybox/";
	public final static String AUDIO_PATH = RES_PATH + "audio/";
	public final static String OBJECT_PATH = RES_PATH + "objFiles/";
	public final static String SHADER_PATH = SORCE_PATH + "shaders/";
	public final static String GUI_SHADER_PATH = SORCE_PATH + "guis/";
	public final static String SKYBOX_SHADER_PATH = SORCE_PATH + "skybox/";
	public final static String NORMAL_MAP_SHADER_PATH = SORCE_PATH + "normalMappingRenderer/";
	public final static String WATER_SHADER_PATH = SORCE_PATH + "water/";
	public final static String PARTICLE_SHADER_PATH = SORCE_PATH + "particles/";
	public final static String SHADOW_SHADER_PATH = SORCE_PATH + "shadows/";
	public final static String FONT_SHADER_PATH = SORCE_PATH + "fontRendering/";
	public final static String POST_PROCESSING_SHADER_PATH = SORCE_PATH + "postProcessing/";
	public final static String BLUR_SHADER_PATH = SORCE_PATH + "gaussianBlur/";
	public final static String TEXT_PATH = RES_PATH + "texts/";
	
	//*****************RENDER SETTINGS**********************************//

	
	//****************GLOBAL WORLD SETTINGS*********************************//
	public final static float SUN_MAX_HEIGHT = 4000; 
	public final static float SUN_MIN_HEIGHT = -4000;
	
	public Settings() {
		

	}
	
	
	

}
