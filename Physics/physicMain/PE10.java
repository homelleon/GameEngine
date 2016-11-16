package physicMain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
	private static int worldIDs[];
	
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
			worldIDs = new ArrayList<Integer>();
			isInitialized = true;
		}	
	}
	 
	public static int peCreateWorld(Vector3f position1, Vector3f position2) {		
		World world = new WorldG(id, position1, position2);
		worlds.put(id, world);
		return id;		
	}
	
	public static void peDeleteWorld(int id) {
		worlds.get(id).delete();
		worlds.remove(id);
	}
	
	/*
	 * error return messages  
	 */
	
	private static boolean peErrRecurInit() {
		boolean isError = false;
		if(isInitialized) {
			isError = true;
			System.err.println("Engine was repeatedly initilized!");
		}
		return isError;
	}
	
	private static boolean peErrNoInit() {
		boolean isError = false;
		if(!isInitialized) {
			isError = true;
			System.err.println("Engine isn't initialized!");
		}
		return isError;
	}
	

}
