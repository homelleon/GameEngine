package physicMain;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import object.entity.entity.EntityInterface;
import worlds.WorldInterface;
import worlds.WorldG;

/* 
 * 
 * Physical Game Engine
 * version 1.0 
 * 
 * Need to be initialized by 
 * initialize()-method before use.
 * 
 * World - is area where 
 * collision bodies can be created
 * and calculated by that module
 * automatically.
 * 
 * Create World with size you need
 * by using method peCreateWorld,
 * attach bodies to objects in the 
 * scene by using method peAttachBody
 * and use peUpdateWorld-method to 
 * update collision of all objects
 * in the world.
 * 
 */

public class PE10 {
	
	private PE10() {}
	
	/* 
	 * Body type constants
	 */
	
	private static boolean isInitialized = false;
	private static Map<Integer, WorldInterface> worlds;
	private static int worldIDcount = 0;
	
	public static final int BODY_2D_PLANE = 0;
	public static final int BODY_2D_TRIANGLE = 1;
	public static final int BODY_2D_QUAD = 2;
	public static final int BODY_2D_CIRCLE = 3;
	public static final int BODY_3D_CUBE = 4;
	public static final int BODY_3D_SPHERE = 5;
	public static final int BODY_3D_PYRAMID = 6;
	public static final int BODY_3D_MESH = 7;
	
	/*
	 * Intersect type constants
	 */
	
	public static final int DATA_IS_OUT = 0;
	public static final int DATA_IS_IN = 1;
	public static final int DATA_IS_CENTER = 2;
	

	
	public static void initialize() {
		if(!peErrRecurInit()) {
			worlds = new HashMap<Integer, WorldInterface>();
			isInitialized = true;
		}	
	}
	 
	public static int peCreateWorld(Vector3f position1, Vector3f position2) {
		worldIDcount += 1;
		WorldInterface world = new WorldG(worldIDcount, position1, position2);
		worlds.put(worldIDcount, world);
		return worldIDcount;		
	}
	
	public static boolean peDeleteWorld(int worldID) {
		boolean isSucceed = false;
		if(!peErrNoInit()) {
			if(!peErrNoIDWorld(worldID)) {
				worlds.get(worldID).delete();
				worlds.remove(worldID);
				isSucceed = true;
			} 
		}
		return isSucceed;
	}
	
	public static boolean peUpdateWorld(int worldID) {
		boolean isSucced = false;
		if(!peErrNoInit()) {
			if(!peErrNoIDWorld(worldID)) {
				worlds.get(worldID).update();
				isSucced = true;			
			}
		}
		return isSucced;
	}
	
	//TODO Implement worlds.add method and attaching with body of certain type
	public static int peAttachBody(EntityInterface entity, int type, int worldID) {
		int id = -1;
		if(!peErrNoInit()) {
			if(!peErrNoIDWorld(worldID)) {
				id = worlds.get(worldID).attachToEntity(entity, type);			
			}
		}
		return id;
	}
	
	public static boolean peDeleteBody(int bodyID, int worldID) {
		boolean isSucced = false;
		if(!peErrNoInit()) {
			if(!peErrNoIDWorld(worldID)) {
				if(!peErrRemoveIDBody(worldID, worldID)) {
					isSucced = true;
				}				
			}
		}
		return isSucced;
	}
	
	public static boolean peSetGravity(int worldID, float value) {
		boolean isSucced = false;
		if(!peErrNoIDWorld(worldID)) {
			if(!peErrGravityNotAv(worldID)) {
				worlds.get(worldID).setGravity(value);			
			}
		}
		return isSucced;
	}
	
	/*
	 * error return messages  
	 */
	
	private static boolean peErrRecurInit() {
		boolean isError = false;
		if(isInitialized) {
			isError = true;
			System.err.println("Physical engine was repeatedly initilized!");
		}
		return isError;
	}
	
	private static boolean peErrNoInit() {
		boolean isError = false;
		if(!isInitialized) {
			isError = true;
			System.err.println("Physical engine isn't initialized!");
		}
		return isError;
	}
	
	private static boolean peErrNoIDWorld(int worldID) {
		boolean isError = false;
		if(!worlds.containsKey(worldID)) {
			isError = true;
			System.err.println("Such world id isn't existed!");
		}
		return isError;
	}
	
	
	private static boolean peErrGravityNotAv(int worldID) {
		boolean isError = false;
		if(!worlds.get(worldID).hasGravity()) {
			isError = true;
			System.err.println("Gravity is not avalable in that world!");
		}
		return isError;
	}
	
	private static boolean peErrRemoveIDBody(int bodyID, int worldID) {
		boolean isError = false;
		if(!peErrNoIDWorld(worldID)) {
			if(!worlds.get(worldID).removeBody(bodyID)) {
				isError = true;
				System.err.println("Such body id isn't existed!");
			}
		}
		return isError;
	}
	

}
