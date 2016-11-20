package physicMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import worlds.World;
import worlds.WorldG;

/* 
 * 
 * Physical Game Engine
 * version 1.0 
 * 
 */

public class PE10 {
	
	private PE10() {}
	
	/* 
	 * Body type constants
	 */
	
	private static boolean isInitialized = false;
	private static Map<Integer, World> worlds;
	private static int worldIDcount = 0;
	
	public static final int PE_2D_PLANE = 0;
	public static final int PE_2D_TRIANGLE = 1;
	public static final int PE_2D_QUAD = 2;
	public static final int PE_2D_CIRCLE = 3;
	public static final int PE_3D_CUBE = 4;
	public static final int PE_3D_SPHERE = 5;
	public static final int PE_3D_PYRAMID = 6;
	public static final int PE_3D_MESH = 7;
	
	public static void initialize() {
		if(!peErrRecurInit()) {
			worlds = new HashMap<Integer, World>();
			isInitialized = true;
		}	
	}
	 
	public static int peCreateWorld(Vector3f position1, Vector3f position2) {
		worldIDcount += 1;
		World world = new WorldG(worldIDcount, position1, position2);
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
	

}
