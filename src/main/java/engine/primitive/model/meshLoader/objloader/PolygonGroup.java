package primitive.model.meshLoader.objloader;

import java.util.ArrayList;
import java.util.HashMap;

public class PolygonGroup {

	private ArrayList<Polygon> polygons;
	private HashMap<Integer,SmoothingGroup> smoothingGroups = new HashMap<Integer,SmoothingGroup>();
	private String name = "";
	
	protected PolygonGroup(){
		smoothingGroups = new HashMap<Integer,SmoothingGroup>();
		polygons = new ArrayList<Polygon>();
	}

	protected ArrayList<Polygon> getPolygons() {
		return polygons;
	}

	protected void setPolygons(ArrayList<Polygon> polygons) {
		this.polygons = polygons;
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected HashMap<Integer,SmoothingGroup> getSmoothingGroups() {
		return smoothingGroups;
	}

	protected void setSmoothingGroups(HashMap<Integer,SmoothingGroup> smoothingGroups) {
		this.smoothingGroups = smoothingGroups;
	}	
}
