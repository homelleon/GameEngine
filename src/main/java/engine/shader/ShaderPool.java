package shader;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import shader.bounding.BoundingShader;
import shader.debug.DebugShader;
import shader.entity.EntityShader;
import shader.entity.NormalMappedEntityShader;
import shader.font.FontShader;
import shader.gpgpu.HeightMapShader;
import shader.gpgpu.HeightPositionShader;
import shader.gpgpu.NormalMapShader;
import shader.guiTexture.GUITextureShader;
import shader.postprocess.ContrastShader;
import shader.postprocess.bloom.BrightFilterShader;
import shader.postprocess.bloom.CombineShader;
import shader.postprocess.gaussianBlur.HorizontalBlurShader;
import shader.postprocess.gaussianBlur.VerticalBlurShader;
import shader.shadow.ShadowShader;
import shader.skybox.SkyboxShader;
import shader.terrain.TerrainShader;
import shader.voxel.VoxelShader;
import shader.water.WaterShader;

public class ShaderPool {
	
	public static final ShaderPool INSTANCE = new ShaderPool();
	
	private Map<Integer, Shader> shaders = new HashMap<Integer, Shader>();
	
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