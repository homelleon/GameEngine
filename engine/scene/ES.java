package scene;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector4f;

/* 
 * 
 * Engine Settings 
 * here is all constants for the engine
 * 
 */

public class ES {
	
	/* display settings */
	public static final int DISPLAY_WIDTH = 1920; //800; //1920;  
	public static final int DISPLAY_HEIGHT = 1080; //600; //1080; 
	public static final int FAR_PLANE = 10000;
	public static final float NEAR_PLANE = 0.5f;
	public static final int FPS_CAP = 120;
	public static final float FOV = 70;
	public static final int MULTISAMPLE = 4;
	
	public static final float DISPLAY_RED = 0.4f;
	public static final float DISPLAY_GREEN = 0.5f;
	public static final float DISPLAY_BLUE = 0.55f;
	
	public static final float DISPLAY_CONTRAST = 0.5f;
	
	/* audio settings */
	public static final float MUSIC_VOLUME = 0.05f;
	public static final float ENVIRONMENT_VOLUME = 0.2f;
	
	/* controls settings */
	public static final float MOUSE_X_SPEED = 0.2f;
	public static final float MOUSE_Y_SPEED = 0.2f;
	public static final float MOUSE_ZOOM_SPEED = 0.1f;
	
	public static final int KEY_EXIT = Keyboard.KEY_ESCAPE;
	public static final int KEY_APPLY = Keyboard.KEY_INSERT;
	
	/* player control */
	public static final int KEY_PLAYER_MOVE_FORWARD = Keyboard.KEY_W;
	public static final int KEY_PLAYER_MOVE_BACKWARD = Keyboard.KEY_S;
	public static final int KEY_PLAYER_MOVE_LEFT = Keyboard.KEY_A;
	public static final int KEY_PLAYER_MOVE_RIGHT = Keyboard.KEY_D;
	public static final int KEY_PLAYER_JUMP = Keyboard.KEY_SPACE;
	public static final int KEY_PLAYER_ACCELERATE = Keyboard.KEY_LSHIFT;
	
	/* chosen object control */
	public static final int KEY_OBJECT_MOVE_UP = Keyboard.KEY_HOME;
	public static final int KEY_OBJECT_MOVE_DOWN = Keyboard.KEY_END;
	public static final int KEY_OBJECT_MOVE_LEFT = Keyboard.KEY_LEFT;
	public static final int KEY_OBJECT_MOVE_RIGHT = Keyboard.KEY_RIGHT;
	public static final int KEY_OBJECT_MOVE_FORWARD = Keyboard.KEY_UP;
	public static final int KEY_OBJECT_MOVE_BACKWARD = Keyboard.KEY_DOWN;
	public static final int KEY_OBJECT_ROTATE_LEFT = Keyboard.KEY_DELETE;
	public static final int KEY_OBJECT_ROTATE_RIGHT = Keyboard.KEY_NEXT;	
	
	/* edit camera control */
	public static final int KEY_EDITOR_MOVE_FORWARD = Keyboard.KEY_W;
	public static final int KEY_EDITOR_MOVE_BACKWARD = Keyboard.KEY_S;
	public static final int KEY_EDITOR_MOVE_LEFT = Keyboard.KEY_A;
	public static final int KEY_EDITOR_MOVE_RIGHT = Keyboard.KEY_D;
	public static final int KEY_EDITOR_MOVE_UP = Keyboard.KEY_SPACE;
	public static final int KEY_EDITOR_MOVE_DOWN = Keyboard.KEY_C;
	public static final int KEY_EDITOR_ACCELERATE = Keyboard.KEY_LSHIFT;
	public static final int KEY_EDITOR_CENTER_VIEW = Keyboard.KEY_HOME;
	
	/* simulation settings */
	public static final float GRAVITY = -50;
	public static final float TIME_LENGTH = 1;
	public static final float FOG_DENSITY = 0.002f;
	public static final float SHADOW_DISTANCE = 150;
	public static final int SHADOW_MAP_SIZE = 16384;
	public static final float SHADOW_TRANSITION_DISTANCE = SHADOW_DISTANCE;
	public static final int SHADOW_PCF = 2;
	public static final float RENDERING_VIEW_DISTANCE = 400;
	public static final float DETAIL_VIEW_DISTANCE = 150;
	public static final int MAX_LIGHTS = 10;
	
	/* voxel settings */
	public static final float VOXEL_BLOCK_SIZE = 5f;
	public static final int VOXEL_CHUNK_SIZE = 5;

	
	/* path settings */
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
	public final static String VOXEL_SHADER_PATH = SOURCE_PATH + "voxels/";
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
	
	/* render settings */
	public final static Vector4f NO_CLIP = new Vector4f(0, 0, 0, 1);
	
	/* global world settings */
	public final static float SUN_MAX_HEIGHT = 4000; 
	public final static float SUN_MIN_HEIGHT = -4000;
	
	/* display mode constants */
	public final static int DISPLAY_DEBUG_MODE = 0;
	public final static int DISPLAY_EDIT_MODE = 1;
	public final static int DISPLAY_GAME_MODE = 2;
	
	/* entity type */
	public final static int ENTITY_TYPE_SIMPLE = 0;
	public final static int ENTITY_TYPE_NORMAL = 1;
	public final static int ENTITY_TYPE_DETAIL = 2;

	

}
