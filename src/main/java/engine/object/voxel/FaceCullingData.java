package object.voxel;

public class FaceCullingData {
	
	int index = 0;
	private boolean[] face = new boolean[6];

	public FaceCullingData(int index) {
		this.index = index;
		for (int i = 0; i < 6; i++) {
			this.face[i] = false;
		}
	}

	public FaceCullingData(boolean value) {
		for (int i = 0; i < 6; i++) {
			this.face[i] = value;
		}
	}

	public boolean getFace(int index) {
		return this.face[index];
	}

	public void setFaceRendering(int index, boolean value) {
		this.face[index] = value;
	}
	
	public int getIndex() {
		return this.index;
	}

}
