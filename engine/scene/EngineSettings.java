package scene;

public class EngineSettings {
	
	//*****************DISPLAY SETTINGS**********************************//
	public static final int DISPLAY_WIDTH = 800; //1920;  
	public static final int DISPLAY_HEIGHT = 600; //1080; 
	public static final int FAR_PLANE = 10000000;
	public static final float NEAR_PLANE = 0.5f;
	public static final int FPS_CAP = 120;
	public static final float FOV = 70;
	public static final int MULTISAMPLE = 4;
	
	public static final float DISPLAY_RED = 0.4f;
	public static final float DISPLAY_GREEN = 0.5f;
	public static final float DISPLAY_BLUE = 0.55f;
	
	public static final float DISPLAY_CONTRAST = 0.5f;
	
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
	public static final float FOG_DENSITY = 0.003f;
	public static final float SHADOW_DISTANCE = 200;
	public static final int SHADOW_MAP_SIZE = 4096;
	public static final float SHADOW_TRANSITION_DISTANCE = 25;
	public static final int SHADOW_PCF = 2;
	public static final float RENDERING_VIEW_DISTANCE = 1000;
	public static final float DETAIL_VIEW_DISTANCE = 150;

	
	//*****************PATH SETTINGS*************************************//
	public final static String RES_PATH = "res/";
	public final static String GAME_PATH = "game/";
	public final static String SOURCE_PATH = "/";
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
	public final static String SPECULAR_MAP_PATH = RES_PATH + "textures/specularMaps/";
	public final static String MAP_PATH = RES_PATH + "maps/"; 
	
	public final static String AUDIO_PATH = "audio/";
	public final static String OBJECT_PATH = RES_PATH + "objFiles/";
	public final static String TEXT_PATH = RES_PATH + "texts/";
	
	public final static String SHADER_PATH = SOURCE_PATH + "shaders/";
	public final static String ENTITY_SHADER_PATH = SOURCE_PATH + "entities/";
	public final static String TERRAIN_SHADER_PATH = SOURCE_PATH + "terrains/";
	public final static String GUI_SHADER_PATH = SOURCE_PATH + "guis/";
	public final static String SKYBOX_SHADER_PATH = SOURCE_PATH + "skybox/";
	public final static String NORMAL_MAP_SHADER_PATH = SOURCE_PATH + "normalMappingRenderer/";
	public final static String WATER_SHADER_PATH = SOURCE_PATH + "water/";
	public final static String PARTICLE_SHADER_PATH = SOURCE_PATH + "particles/";
	public final static String SHADOW_SHADER_PATH = SOURCE_PATH + "shadows/";
	public final static String FONT_SHADER_PATH = SOURCE_PATH + "fontRendering/";
	public final static String POST_PROCESSING_SHADER_PATH = SOURCE_PATH + "postProcessing/";
	public final static String BLUR_SHADER_PATH = SOURCE_PATH + "gaussianBlur/";
	public final static String BLOOM_SHADER_PATH = SOURCE_PATH + "bloom/";
	
	public final static String GAME_SETTINGS_PATH = GAME_PATH + "gameMain/";
	
	//*****************RENDER SETTINGS**********************************//

	
	//****************GLOBAL WORLD SETTINGS*********************************//
	public final static float SUN_MAX_HEIGHT = 4000; 
	public final static float SUN_MIN_HEIGHT = -4000;	

}
