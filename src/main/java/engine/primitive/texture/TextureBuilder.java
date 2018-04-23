package primitive.texture;

import tool.MyFile;

public class TextureBuilder {

	private boolean clampEdges = false;
	private boolean mipmap = false;
	private boolean anisotropic = true;
	private boolean nearest = false;

	private MyFile file;

	public TextureBuilder(MyFile textureFile) {
		file = textureFile;
	}

	public TextureBuilder clampEdges() {
		clampEdges = true;
		return this;
	}

	public TextureBuilder normalMipMap() {
		mipmap = true;
		anisotropic = false;
		return this;
	}

	public TextureBuilder nearestFiltering() {
		mipmap = false;
		anisotropic = false;
		nearest = true;
		return this;
	}

	public TextureBuilder anisotropic() {
		mipmap = true;
		anisotropic = true;
		return this;
	}

	public boolean isClampEdges() {
		return clampEdges;
	}

	public boolean isMipmap() {
		return mipmap;
	}

	public boolean isAnisotropic() {
		return anisotropic;
	}

	public boolean isNearest() {
		return nearest;
	}

	public Texture build() {
		TextureData textureData = TextureUtils.decodeTextureFile(file);
		int textureId = TextureUtils.loadTextureToOpenGL(textureData, this);
		return new Texture(textureId, textureData.getWidth());
	}

}
