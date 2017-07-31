package renderer.loader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import core.settings.EngineSettings;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import object.texture.TextureData;

public class TextureBufferLoader {
	
	/**
	 * Map of named textures indicies.
	 */
	private Map<String, Integer> textures = new HashMap<String, Integer>();
	

	/**
	 * Loads texture into video buffer using texture path and texture name
	 * parameters.
	 * 
	 * @param folder
	 *            {@link String} value of texture location name
	 * @param fileName
	 *            {@link String} value of texture file name
	 * 
	 * @return {@link Integer} value of texture ordered number in video buffer
	 */
	public int loadTexture(String folder, String fileName) {
		return loadTexture(folder, fileName, "PNG");
	}

	public int loadTexture(String path, String fileName, String format) {
		Texture texture = null;
		try {
			float bias;
			if (path == EngineSettings.FONT_FILE_PATH) {
				bias = 0;
			} else {
				bias = -2.4f;
			}

			String extension = EngineSettings.EXTENSION_PNG;

			switch (format) {
			case "PNG":
				extension = EngineSettings.EXTENSION_PNG;
				break;
			case "TGA":
				extension = EngineSettings.EXTENSION_TGA;
				break;
			case "JPG":
				extension = EngineSettings.EXTENSION_JPG;
				throw new TypeNotPresentException("JPG file is not supported!", null);
			default:
				throw new TypeNotPresentException("Uknown file extention!", null);
			}

			texture = TextureLoader.getTexture(format, new FileInputStream(path + fileName + extension));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, bias);
			if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
				float amount = Math.min(4f,
						GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT,
						amount);
			} else {
				System.out.println("Anisotropic filtering is not supported");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int textureID = texture.getTextureID();
		textures.put(fileName, textureID);
		return textureID;
	}

	/**
	 * Gets texture name by its identification number (ordered number) in video
	 * buffer.
	 * 
	 * @param id
	 *            {@link Integer} value of texture ordered number in video
	 *            buffer
	 * @return {@link String} value of texture name
	 */
	public String getTextureByID(int id) {
		String name = null;
		for (String key : textures.keySet()) {
			if (textures.get(key).equals(id)) {
				name = key;
				break;
			}
		}
		return name;
	}
	
	/**
	 * Loads cubic texture of cube map into video buffer.
	 * 
	 * @param path
	 *            {@link String} value of texture location name
	 * @param textureFiles
	 *            {@link String} array of 6 flat textures name
	 * 
	 * @return {@link Integer} ordered number of texture in video buffer
	 */
	public int loadCubeMap(String path, String[] textureFiles) {
		int texID = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texID);

		for (int i = 0; i < textureFiles.length; i++) {
			TextureData data = decodeTextureFile(path + textureFiles[i] + ".png");
			GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, data.getWidth(),
					data.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getBuffer());
		}
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		textures.put(path, texID);
		return texID;
	}

	/**
	 * Loads voxel cubic texture of cube map into video buffer.
	 * 
	 * @param path
	 *            {@link String} value of texture location name
	 * @param textureFiles
	 *            {@link String} array of 6 flat textures name
	 * @return
	 */
	public int loadCubeVoxelMap(String path, String[] textureFiles) {
		int texID = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texID);

		for (int i = 0; i < textureFiles.length; i++) {
			TextureData data = decodeTextureFile(path + textureFiles[i] + ".png");
			GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, data.getWidth(),
					data.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getBuffer());
		}
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		textures.put(path, texID);
		return texID;
	}

	private TextureData decodeTextureFile(String fileName) {
		int width = 0;
		int height = 0;
		ByteBuffer buffer = null;
		try {
			FileInputStream in = new FileInputStream(fileName);
			PNGDecoder decoder = new PNGDecoder(in);
			width = decoder.getWidth();
			height = decoder.getHeight();
			buffer = ByteBuffer.allocateDirect(4 * width * height);
			decoder.decode(buffer, width * 4, Format.RGBA);
			buffer.flip();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Tried to load texture " + fileName + ", didn't work");
			System.exit(-1);
		}
		return new TextureData(buffer, width, height);
	}
	
	public void clean() {
		for (int texture : textures.values()) {
			GL11.glDeleteTextures(texture);
		}
	}


}
