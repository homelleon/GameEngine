package object.texture.model;

public class ModelTexture {

	private int textureID;
	private int normalMap;
	private int specularMap;
	private String name;

	private float shineDamper = 1;
	private float reflectivity = 0;
	private float reflectiveFactor = 0;
	private float refractiveFactor = 0;
	private float refractiveIndex = 0;

	private boolean hasTransparency = false;
	private boolean useFakeLighting = false;
	private boolean hasSpecularMap = false;

	private int numberOfRows = 1;

	public ModelTexture(int id) {
		this.textureID = id;
	}

	public ModelTexture(String name, int id) {
		this.textureID = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSpecularMap(int specMap) {
		this.specularMap = specMap;
		this.hasSpecularMap = true;
	}

	public boolean hasSpecularMap() {
		return hasSpecularMap;
	}

	public int getSpecularMap() {
		return specularMap;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public int getNormalMap() {
		return normalMap;
	}

	public void setNormalMap(int normalMap) {
		this.normalMap = normalMap;
	}

	public boolean isUseFakeLighting() {
		return useFakeLighting;
	}

	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
	}

	public boolean isHasTransparency() {
		return hasTransparency;
	}

	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}

	public float getRefractiveFactor() {
		return refractiveFactor;
	}

	public void setRefractiveFactor(float refractiveFactor) {
		this.refractiveFactor = refractiveFactor;
	}

	public float getRefractiveIndex() {
		return refractiveIndex;
	}

	public void setRefractiveIndex(float refractiveIndex) {
		this.refractiveIndex = refractiveIndex;
	}

	public float getReflectiveFactor() {
		return reflectiveFactor;
	}

	public void setReflectiveFactor(float reflectiveFactor) {
		this.reflectiveFactor = reflectiveFactor;
	}

	public int getID() {
		return this.textureID;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	
	public ModelTexture clone(String name) {
		ModelTexture texture = new ModelTexture(name, this.textureID);
		texture.setSpecularMap(this.specularMap);
		texture.setNormalMap(normalMap);
		texture.setShineDamper(shineDamper);
		texture.setReflectivity(reflectivity);
		texture.setReflectiveFactor(reflectiveFactor);
		texture.setRefractiveFactor(refractiveFactor);
		texture.setRefractiveIndex(refractiveIndex);
		texture.setHasTransparency(hasTransparency);
		texture.setUseFakeLighting(useFakeLighting);
		texture.setNumberOfRows(numberOfRows);
		return texture;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.hashCode() != this.hashCode()) {
			return false;
		}
		if(obj == this) {
			return true;
		}
		if(obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		ModelTexture modelTexture = (ModelTexture) obj;
		if(modelTexture.getID() == this.getID() &&
				modelTexture.getNormalMap() == this.getNormalMap() &&
				modelTexture.getSpecularMap() == this.getSpecularMap() &&
				modelTexture.getShineDamper() == this.getShineDamper() &&
				modelTexture.getReflectivity() == this.getReflectivity() &&
				modelTexture.getReflectiveFactor() == this.getReflectiveFactor() &&
				modelTexture.getRefractiveFactor() == this.getRefractiveFactor() &&
				modelTexture.getRefractiveIndex() == this.getRefractiveIndex() &&
				modelTexture.isHasTransparency() == this.isHasTransparency() &&
				modelTexture.isUseFakeLighting() == this.isUseFakeLighting() &&
				modelTexture.hasSpecularMap() == this.hasSpecularMap() &&
				modelTexture.getNumberOfRows() == this.getNumberOfRows())
		{
			return true;
		} else {
			return false;
		} 
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + textureID;
		result = prime * result + normalMap;
		result = prime * result + specularMap;
		result = prime * result + (int) shineDamper;
		result = prime * result + (int) reflectivity;
		result = prime * result + (int) reflectiveFactor;
		result = prime * result + (int) refractiveFactor;
		result = prime * result + (int) refractiveIndex;
		result = prime * result + (hasTransparency ? 1 : 0);
		result = prime * result + (useFakeLighting ? 1 : 0);
		result = prime * result + (hasSpecularMap ? 1 : 0);
		result = prime * result + numberOfRows;
		return result;
	}

}
