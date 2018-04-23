package renderer.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.camera.Camera;
import object.entity.Entity;
import object.light.Light;
import primitive.buffer.VAO;
import primitive.model.Mesh;
import primitive.model.Model;
import primitive.texture.Texture;
import primitive.texture.material.Material;
import scene.Scene;
import shader.entity.NormalMappedEntityShader;
import tool.GraphicUtils;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Color;
import tool.math.vector.Vector2f;

public class NormalEntityRenderer implements EntityRenderer {

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
	public void render(Map<Model, List<Entity>> entities, Scene scene, Vector4f clipPlane, Matrix4f toShadowMapSpace, Texture environmentMap) {
		if (entities.isEmpty()) return;
		
		shader.start();
		shader.loadFogDensity(EngineSettings.FOG_DENSITY);
		
		if (toShadowMapSpace != null)
			shader.loadToShadowSpaceMatrix(toShadowMapSpace);
		
		shader.loadShadowVariables(EngineSettings.SHADOW_DISTANCE, EngineSettings.SHADOW_MAP_SIZE,
				EngineSettings.SHADOW_TRANSITION_DISTANCE, EngineSettings.SHADOW_PCF);
		prepare(clipPlane, scene.getLights().getAll(), scene.getCamera());
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

	public void clean() {
		shader.clean();
	}

	private void prepareTexturedModel(Model model) {
		Mesh rawModel = model.getMesh();
		VAO vao = rawModel.getVAO();
		vao.bind(0, 1, 2, 3);
		Material material = model.getMaterial();
		shader.loadNumberOfRows(material.getDiffuseMap().getNumberOfRows());
		
		if (material.getDiffuseMap().isHasTransparency())
			GraphicUtils.cullBackFaces(false);
		
		shader.loadFakeLightingVariable(material.isUseFakeLighting());
		shader.loadShineVariables(material.getShininess(), material.getReflectivity());
		model.getMaterial().getDiffuseMap().bind(0);
		model.getMaterial().getNormalMap().bind(1);
		shader.loadUseSpecularMap(material.getSpecularMap() != null);
		
		if (material.getSpecularMap() != null)
			model.getMaterial().getSpecularMap().bind(4);
		
		shader.loadUseAlphaMap(material.getAlphaMap() != null);
		
		if (material.getAlphaMap() != null)
			model.getMaterial().getAlphaMap().bind(5);
	}

	private void unbindTexturedModel() {
		GraphicUtils.cullBackFaces(true);
		VAO.unbind(0, 1, 2, 3);
	}

	private void prepareInstance(Entity entity) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation().getX(),
				entity.getRotation().getY(), entity.getRotation().getZ(), entity.getScale().getX());
		shader.loadTransformationMatrix(transformationMatrix);
		Vector2f textureOffset = entity.getTextureOffset();
		shader.loadOffset(textureOffset.x, textureOffset.y);
		shader.loadManipulationVariables(entity.isChosen());
	}

	private void prepare(Vector4f clipPlane, Collection<Light> lights, Camera camera) {
		if (clipPlane != null)
			shader.loadClipPlane(clipPlane);
		
		shader.loadSkyColor(new Color(EngineSettings.RED, EngineSettings.GREEN, EngineSettings.BLUE));
		Matrix4f viewMatrix = camera.getViewMatrix();

		shader.loadLights(lights, viewMatrix);
		shader.loadViewMatrix(viewMatrix);
	}

}
