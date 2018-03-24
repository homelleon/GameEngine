package shader;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import shader.bounding.BoundingShader;
import shader.debug.DebugShader;
import shader.entity.normal.NormalMappedEntityShader;
import shader.entity.textured.EntityShader;
import shader.font.FontShader;
import shader.gpgpu.HeightMapShader;
import shader.gpgpu.HeightPositionShader;
import shader.gpgpu.NormalMapShader;
import shader.guiTexture.GUITextureShader;
import shader.postProcessing.ContrastShader;
import shader.postProcessing.bloom.BrightFilterShader;
import shader.postProcessing.bloom.CombineShader;
import shader.postProcessing.gaussianBlur.HorizontalBlurShader;
import shader.postProcessing.gaussianBlur.VerticalBlurShader;
import shader.shadow.ShadowShader;
import shader.skybox.SkyboxShader;
import shader.terrain.TerrainShader;
import shader.voxel.VoxelShader;
import shader.water.WaterShader;

public class ShaderPool {
	
	private static ShaderPool instance;
	
	private Map<Integer, Shader> shaders = new HashMap<Integer, Shader>();
	
	public static ShaderPool getInstance() {
		if (instance == null) {
			instance = new ShaderPool();
		}
		return instance;
	}
	
	public Shader get(int type) {
		return shaders.get(type);
	}
	
	public void clean() {
		shaders.values().forEach(Shader::clean);
	}
	
	private ShaderPool() {
		Shader[] shaderArray = {
			new DebugShader(),
			new EntityShader(),
			new NormalMappedEntityShader(),
			new TerrainShader(),
			new GUITextureShader(),
			new FontShader(),
			new SkyboxShader(),
			new WaterShader(),
			new BoundingShader(),
			new ShadowShader(),
			new BrightFilterShader(),
			new CombineShader(),
			new HorizontalBlurShader(),
			new VerticalBlurShader(),
			new ContrastShader(),
			new HeightMapShader(),
			new HeightPositionShader(),
			new NormalMapShader(),
			new VoxelShader()
		}; 
		
		Stream.of(shaderArray).forEach(this::processShader);
	}
	
	private void processShader(Shader shader) {
		shaders.put(shader.getType(), shader);
	}

}
