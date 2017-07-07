package object.voxel.data;

public class FaceCullingData {
	
	private boolean[] face = new boolean[6];
	
	public FaceCullingData() {
		for(int i = 0; i < 6; i++) {
			this.face[i] = false;
		}
	}
	
	public FaceCullingData(boolean value) {
		for(int i = 0; i < 6; i++) {
			this.face[i] = value;
		}
	}
	
	public boolean getFace(int index) {
		return this.face[index];
	}
	
	public void setFaceRendering(int index, boolean value) {
		this.face[index] = value;
	}

}
