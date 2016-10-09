package particles;

public class ParticleTexture {
	
	private int textureID;
	private int numberOfRows;
	private boolean additive;
	
	public ParticleTexture(int textureID, int numberOfRows, boolean additive) {
		super();
		this.textureID = textureID;
		this.numberOfRows = numberOfRows;
		this.additive = additive;
	}

	public int getTextureID() {
		return textureID;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}
	
	protected boolean isAdditive(){
		return additive;
	}
	
		

}
