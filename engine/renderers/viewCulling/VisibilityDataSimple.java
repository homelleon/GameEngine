package renderers.viewCulling;

public class VisibilityDataSimple implements VisibilityData {
	
	private boolean isVisible = false;
	private float distance = 0;
	
	public VisibilityDataSimple(boolean isVisible, float distance) {
		this.isVisible = isVisible;
		this.distance = distance;
	}

	@Override
	public boolean getIsVisible() {
		return isVisible;
	}

	@Override
	public float getDistance() {
		return distance;
	}

}
