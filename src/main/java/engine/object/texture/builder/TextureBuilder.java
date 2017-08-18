package object.texture.builder;

import object.texture.Texture;
import object.texture.TextureData;
import object.texture.TextureUtils;
import tool.MyFile;

public class TextureBuilder {

	private boolean clampEdges = false;
	private boolean mipmap = false;
	private boolean anisotropic = true;
	private boolean nearest = false;

	private MyFile file;

	public TextureBuilder(MyFile textureFile) {
		this.file = textureFile;
	}

	public Texture create() {
		TextureData textureData = TextureUtils.decodeTextureFile(file);
		int textureId = TextureUtils.loadTextureToOpenGL(textureData, this);
		return new Texture(textureId, textureData.getWidth());
	}

	public TextureBuilder clampEdges() {
		this.clampEdges = true;
		return this;
	}

	public TextureBuilder normalMipMap() {
		this.mipmap = true;
		this.anisotropic = false;
		return this;
	}

	public TextureBuilder nearestFiltering() {
		this.mipmap = false;
		this.anisotropic = false;
		this.nearest = true;
		return this;
	}

	public TextureBuilder anisotropic() {
		this.mipmap = true;
		this.anisotropic = true;
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

}
