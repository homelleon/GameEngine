package primitive.texture.material;

import primitive.texture.Texture2D;
import tool.math.vector.Vector3f;

public class Material {

	private Texture2D diffuseMap;
	private Texture2D normalMap;
	private Texture2D displaceMap;
	private Texture2D ambientMap;
	private Texture2D specularMap;
	private Texture2D alphaMap;
	private Vector3f color;
	
	private String name;

	private float shininess = 1;
	private float reflectivity = 0;
	private float reflectiveFactor = 0;
	private float refractiveFactor = 0;
	private float refractiveIndex = 0;
	private float emission;

	private boolean useFakeLighting = false;

	public Material(String name) {
		this.name = name;
	}
	
	public Material(String name, Texture2D diffuseMap) {
		this.diffuseMap = diffuseMap;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	

	public Texture2D getDiffuseMap() {
		return diffuseMap;
	}

	public void setDiffuseMap(Texture2D diffuseMap)	{
		this.diffuseMap = diffuseMap;
	}

	public Texture2D getDisplaceMap() {
		return displaceMap;
	}

	public void setDisplaceMap(Texture2D displaceMap) {
		this.displaceMap = displaceMap;
	}

	public Texture2D getAmbientMap() {
		return ambientMap;
	}

	public void setAmbientMap(Texture2D ambientMap) {
		this.ambientMap = ambientMap;
	}	

	public Texture2D getSpecularMap() {
		return specularMap;
	}
	
	public void setSpecularMap(Texture2D specularMap) {
		this.specularMap = specularMap;
	}

	public Texture2D getAlphaMap() {
		return alphaMap;
	}

	public void setAlphaMap(Texture2D alphaMap) {
		this.alphaMap = alphaMap;
	}

	public Texture2D getNormalMap() {
		return normalMap;
	}

	public void setNormalMap(Texture2D normalMap) {
		this.normalMap = normalMap;
	}

	public boolean isUseFakeLighting() {
		return useFakeLighting;
	}

	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
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

	public float getShininess() {
		return shininess;
	}

	public void setShininess(float shininess) {
		this.shininess = shininess;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	
	public Material clone(String name) {
		Material texture = new Material(name, this.diffuseMap);
		texture.setNormalMap(normalMap);
		texture.setDisplaceMap(displaceMap);
		texture.setAmbientMap(ambientMap);
		texture.setSpecularMap(this.specularMap);
		texture.setAlphaMap(alphaMap);
		texture.setColor(new Vector3f(color.x,color.y,color.z));		
		texture.setShininess(shininess);
		texture.setReflectivity(reflectivity);
		texture.setReflectiveFactor(reflectiveFactor);
		texture.setRefractiveFactor(refractiveFactor);
		texture.setRefractiveIndex(refractiveIndex);
		texture.setUseFakeLighting(useFakeLighting);
		return texture;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.hashCode() != this.hashCode()) 
		{
			return false;
		}
		if(obj == this) 
		{
			return true;
		}
		if(obj == null || obj.getClass() != this.getClass()) 
		{
			return false;
		}
		Material modelTexture = (Material) obj;
		return (modelTexture.getDiffuseMap().equals(this.getDiffuseMap()) &&
				modelTexture.getNormalMap().equals(this.getNormalMap()) &&
				modelTexture.getDisplaceMap().equals(this.getDisplaceMap()) &&
				modelTexture.getAmbientMap().equals(this.getAmbientMap()) &&
				modelTexture.getSpecularMap().equals(this.getSpecularMap()) &&
				modelTexture.getAlphaMap().equals(this.getAlphaMap()) &&
				modelTexture.getShininess() == this.getShininess() &&
				modelTexture.getReflectivity() == this.getReflectivity() &&
				modelTexture.getReflectiveFactor() == this.getReflectiveFactor() &&
				modelTexture.getRefractiveFactor() == this.getRefractiveFactor() &&
				modelTexture.getRefractiveIndex() == this.getRefractiveIndex() &&
				modelTexture.isUseFakeLighting() == this.isUseFakeLighting());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + diffuseMap.hashCode();
		result = prime * result + normalMap.hashCode();
		result = prime * result + displaceMap.hashCode();
		result = prime * result + ambientMap.hashCode();
		result = prime * result + specularMap.hashCode();
		result = prime * result + alphaMap.hashCode();
		result = prime * result + (int) shininess;
		result = prime * result + (int) reflectivity;
		result = prime * result + (int) reflectiveFactor;
		result = prime * result + (int) refractiveFactor;
		result = prime * result + (int) refractiveIndex;
		result = prime * result + (useFakeLighting ? 1 : 0);
		return result;
	}

	public void setEmission(float emission) {
		this.emission = emission;
	}
	
	public float getEmission() {
		return this.emission;
	}

}
