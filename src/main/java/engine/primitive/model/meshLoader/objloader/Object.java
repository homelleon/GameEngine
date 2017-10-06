package primitive.model.meshLoader.objloader;

import java.util.ArrayDeque;
import java.util.Deque;

public class Object {

	private Deque<PolygonGroup> polygonGroups;
	private String name = "";
	
	protected Object(){
		polygonGroups = new ArrayDeque<PolygonGroup>();
	}

	protected Deque<PolygonGroup> getPolygonGroups() {
		return polygonGroups;
	}

	protected void setPolygonGroups(Deque<PolygonGroup> polygonGroups) {
		this.polygonGroups = polygonGroups;
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}
}
