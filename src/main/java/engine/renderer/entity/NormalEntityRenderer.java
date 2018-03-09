package renderer.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.light.ILight;
import primitive.buffer.VAO;
import primitive.model.Mesh;
import primitive.model.Model;
import primitive.texture.Texture;
import primitive.texture.material.Material;
import shader.entity.normal.NormalMappedEntityShader;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Color;
import tool.math.vector.Vector2f;
import tool.openGL.OGLUtils;

public class NormalEntityRenderer implements IEntityRenderer {

	private NormalMappedEntityShader shader;
	private Texture environmentMap;

	public NormalEntityRenderer(Matrix4f projectionMatrix) {
		this.shader = new NormalMappedEntityShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
	}

	@Override
	public void renderHigh(Map<Model, List<IEntity>> entities, Vector4f clipPlane, Collection<ILight> lights,
			ICamera camera, Matrix4f toShadowMapSpace, Texture environmentMap) {
		if(!entities.isEmpty()) {
			shader.start();
			shader.loadFogDensity(EngineSettings.FOG_DENSITY);
			shader.loadToShadowSpaceMatrix(toShadowMapSpace);
			shader.loadShadowVariables(EngineSettings.SHADOW_DISTANCE, EngineSettings.SHADOW_MAP_SIZE,
					EngineSettings.SHADOW_TRANSITION_DISTANCE, EngineSettings.SHADOW_PCF);
			prepare(clipPlane, lights, camera);
			entities.keySet().forEach(model -> {
				prepareTexturedModel(model);
				entities.get(model).forEach(entity -> {
					prepareInstance(entity);
					GL11.glDrawElements(GL11.GL_TRIANGLES, model.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
					GL11.glFlush();
				});
				unbindTexturedModel();
			});
			shader.stop();
		}
	}
	
	@Override
	public void renderLow(Map<Model, List<IEntity>> entities, Collection<ILight> lights, ICamera camera, Matrix4f toShadowMapSpace) {
		GL11.glClearColor(1, 1, 1, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		shader.start();
		shader.loadClipPlane(EngineSettings.NO_CLIP);
		shader.loadSkyColor(new Color(EngineSettings.RED, EngineSettings.GREEN, EngineSettings.BLUE));
		shader.loadFogDensity(EngineSettings.FOG_DENSITY);
		entities.keySet()
			.forEach(model -> {
				prepareLowTexturedModel(model);
				entities.get(model)
					.forEach(entity -> {
						prepareInstance(entity);
						GL11.glDrawElements(GL11.GL_TRIANGLES, model.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
					});
					unbindTexturedModel();
			});
		shader.stop();
	}	

	public void clean() {
		shader.clean();
	}
	
	private void prepareLowTexturedModel(Model model) {
		prepareTexturedModel(model);
	}

	private void prepareTexturedModel(Model model) {
		Mesh rawModel = model.getMesh();
		VAO vao = rawModel.getVAO();
		vao.bind(0, 1, 2, 3);
		Material material = model.getMaterial();
		shader.loadNumberOfRows(material.getDiffuseMap().getNumberOfRows());
		if (material.getDiffuseMap().isHasTransparency()) {
			OGLUtils.cullBackFaces(false);
		}
		shader.loadFakeLightingVariable(material.isUseFakeLighting());
		shader.loadShineVariables(material.getShininess(), material.getReflectivity());
		model.getMaterial().getDiffuseMap().bind(0);
		model.getMaterial().getNormalMap().bind(1);
		shader.loadUseSpecularMap(material.getSpecularMap() !=null);
		if (material.getSpecularMap() != null) {
			model.getMaterial().getSpecularMap().bind(4);
		}
		shader.loadUseAlphaMap(material.getAlphaMap() != null);
		if (material.getAlphaMap() != null) {
			model.getMaterial().getAlphaMap().bind(5);
		}
	}

	private void unbindTexturedModel() {
		OGLUtils.cullBackFaces(true);
		VAO.unbind(0, 1, 2, 3);
	}

	private void prepareInstance(IEntity entity) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation().getX(),
				entity.getRotation().getY(), entity.getRotation().getZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		Vector2f textureOffset = entity.getTextureOffset();
		shader.loadOffset(textureOffset.x, textureOffset.y);
		shader.loadManipulationVariables(entity.isChosen());
	}

	private void prepare(Vector4f clipPlane, Collection<ILight> lights, ICamera camera) {
		shader.loadClipPlane(clipPlane);
		shader.loadSkyColor(new Color(EngineSettings.RED, EngineSettings.GREEN, EngineSettings.BLUE));
		Matrix4f viewMatrix = camera.getViewMatrix();

		shader.loadLights(lights, viewMatrix);
		shader.loadViewMatrix(viewMatrix);
	}

}
