package core.settings;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector4f;

/**
 * <b>Engine Settings</b><br>
 * Contains all in game settings and constant variables.
 * 
 * @author homelleon
 * @version 1.0
 */

public class EngineSettings {

	/* display settings */
	public static final int DISPLAY_WIDTH = 800; // 1920;
	public static final int DISPLAY_HEIGHT = 600; // 1080;
	public static final int TERRAIN_SIZE = 1000;
	public static final int FAR_PLANE = 10000;
	public static final float NEAR_PLANE = 0.5f;
	public static final int FPS_CAP = 120;
	public static final float FOV = 70;
	public static final int MULTISAMPLE = 4;

	public static final float DISPLAY_RED = 0.4f;
	public static final float DISPLAY_GREEN = 0.5f;
	public static final float DISPLAY_BLUE = 0.55f;

	public static final float DISPLAY_CONTRAST = 0.8f;

	/* audio settings */
	public static final float MUSIC_VOLUME = 0.05f;
	public static final float ENVIRONMENT_VOLUME = 0.2f;

	/* controls settings */
	public static final float MOUSE_X_SPEED = 0.2f;
	public static final float MOUSE_Y_SPEED = 0.2f;
	public static final float MOUSE_ZOOM_SPEED = 0.1f;

	public static final int KEY_EXIT = Keyboard.KEY_ESCAPE;
	public static final int KEY_APPLY = Keyboard.KEY_INSERT;
	public static final int KEY_PAUSE = Keyboard.KEY_PAUSE;

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

	/* debug control */
	public static final int KEY_DEBUG_BOUNDING_BOX = Keyboard.KEY_F2;
	public static final int KEY_DEBUG_INFORMATION = Keyboard.KEY_F1;

	/* simulation settings */
	public static final float GRAVITY = -50;
	public static final float TIME_LENGTH = 1;
	public static final float FOG_DENSITY = 0.002f;
	public static final float SHADOW_DISTANCE = 150;
	public static final int SHADOW_MAP_SIZE = 16384;
	public static final float SHADOW_TRANSITION_DISTANCE = SHADOW_DISTANCE;
	public static final int SHADOW_PCF = 2;
	public static final float RENDERING_VIEW_DISTANCE = 500;
	public static final float DETAIL_VIEW_DISTANCE = 150;
	public static final int MAX_LIGHTS = 10;

	/* voxel settings */
	public static final float VOXEL_BLOCK_SIZE = 5f;
	public static final int VOXEL_CHUNK_SIZE = 8;

	/* path settings */
	public final static String PROJECT_PATH = "src/main/java/";
	public final static String RES_PATH = "resource/";
	public final static String GAME_PATH = PROJECT_PATH + "game/";
	public final static String SOURCE_PATH = PROJECT_PATH + "engine/";
	public final static String OBJECT_PATH = SOURCE_PATH + "object/";
	public final static String FONT_PATH = OBJECT_PATH + "font/";
	public final static String TEXTURE_PATH = RES_PATH + "texture/";

	/* texture */
	public final static String TEXTURE_MODEL_PATH = TEXTURE_PATH + "model/";
	public final static String TEXTURE_TERRAIN_PATH = TEXTURE_PATH + "terrain/";
	public final static String TEXTURE_INTERFACE_PATH = TEXTURE_PATH + "interface/";
	public final static String TEXTURE_PARTICLE_PATH = TEXTURE_PATH + "particle/";
	public final static String TEXTURE_BLEND_MAP_PATH = TEXTURE_PATH + "blendMap/";
	public final static String TEXTURE_HEIGHT_MAP_PATH = TEXTURE_PATH + "heightMap/";
	public final static String TEXTURE_DUDV_MAP_PATH = TEXTURE_PATH + "DUDV/";
	public final static String TEXTURE_NORMAL_MAP_PATH = TEXTURE_PATH + "normalMap/";
	public final static String TEXTURE_SKYBOX_PATH = TEXTURE_PATH + "skybox/";
	public final static String TEXTURE_SPECULAR_MAP_PATH = TEXTURE_PATH + "specularMap/";

	/* objects */
	public final static String MAP_PATH = RES_PATH + "map/";
	public final static String FONT_FILE_PATH = TEXTURE_PATH + "font/";

	public final static String AUDIO_PATH = "object/audio/";
	public final static String MODEL_PATH = RES_PATH + "model/";
	public final static String TEXT_PATH = RES_PATH + "text/";
	public final static String INTERFACE_PATH = RES_PATH + "interface/";

	/* shaders */
	public final static String SHADERS_PATH = "/shader/";
	public final static String SHADERS_ENTITY_PATH = SHADERS_PATH + "entity/";
	public final static String SHADERS_NORMAL_MAP_PATH = SHADERS_ENTITY_PATH;
	public final static String SHADERS_BOUNDING_PATH = SHADERS_PATH + "bounding/";
	public final static String SHADERS_TERRAIN_PATH = SHADERS_PATH + "terrain/";
	public final static String SHADERS_VOXEL_PATH = SHADERS_PATH + "voxel/";
	public final static String SHADERS_GUI_PATH = SHADERS_PATH + "guiTexture/";
	public final static String SHADERS_SKYBOX_PATH = SHADERS_PATH + "skybox/";
	public final static String SHADERS_WATER_PATH = SHADERS_PATH + "water/";
	public final static String SHADERS_PARTICLE_PATH = SHADERS_PATH + "particle/";
	public final static String SHADERS_SHADOW_PATH = SHADERS_PATH + "shadow/";
	public final static String SHADERS_FONT_PATH = SHADERS_PATH + "font/";
	public final static String SHADERS_POST_PROCESSING_PATH = SHADERS_PATH + "postProcessing/";
	public final static String SHADERS_BLUR_PATH = SHADERS_POST_PROCESSING_PATH + "gaussianBlur/";
	public final static String SHADERS_BLOOM_PATH = SHADERS_POST_PROCESSING_PATH + "bloom/";
	public final static String SHADERS_ANIMATION_PATH = SHADERS_PATH + "animation/";

	public final static String SETTINGS_GAME_PATH = GAME_PATH + "main/";

	public final static String EXTENSION_PNG = ".png";
	public final static String EXTENSION_TGA = ".tga";
	public final static String EXTENSION_JPG = ".jpg";
	public final static String EXTENSION_OBJ = ".obj";
	public final static String EXTENSION_XML = ".xml";

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

	/* bounding box type */
	public final static int BOUNDING_BOX_MIN_MAX = 0;
	public final static int BOUNDING_BOX_DIAM = 1;
	public final static int BOUNDING_BOX_RADIUS = 2;

}
