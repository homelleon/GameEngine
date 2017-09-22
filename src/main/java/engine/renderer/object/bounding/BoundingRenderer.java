package renderer.object.bounding;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.model.raw.RawModel;
import object.model.textured.TexturedModel;
import object.openglObject.VAO;
import shader.bounding.BoundingShader;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.openGL.OGLUtils;

public class BoundingRenderer {

	private BoundingShader shader;
	private boolean boundingWiredFrame = true;

	public BoundingRenderer(Matrix4f projectionMatrix) {
		this.shader = new BoundingShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(Map<TexturedModel, List<IEntity>> entities, Map<TexturedModel, List<IEntity>> normalEntities,
			ICamera camera) {
		checkWiredFrameOn(boundingWiredFrame);
		shader.start();
		shader.loadViewMatrix(camera);
		
		for (TexturedModel model : entities.keySet()) {
			RawModel bModel = model.getRawModel().getBBox().getModel();
			prepareModel(bModel);
			entities.get(model).forEach(entity -> {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, bModel.getVertexCount(),
						GL11.GL_UNSIGNED_INT, 0);
			});
			unbindModel();
		}
		
		for (TexturedModel model : normalEntities.keySet()) {
			RawModel bModel = model.getRawModel().getBBox().getModel();
			prepareModel(bModel);
			for (IEntity entity : normalEntities.get(model)) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, bModel.getVertexCount(),
						GL11.GL_UNSIGNED_INT, 0);
			}
			unbindModel();
		}
		shader.stop();
		checkWiredFrameOff(boundingWiredFrame);
	}

	public RawModel prepareModel(RawModel model) {
		VAO vao = model.getVAO();
		vao.bind(0,1,2);
		return model;
	}

	private void unbindModel() {
		VAO.unbind(0,1,2);
	}

	public void prepareInstance(IEntity entity) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation().getX(),
				entity.getRotation().getY(), entity.getRotation().getZ(), entity.getScale());
		shader.loadTranformationMatrix(transformationMatrix);
	}

	private void checkWiredFrameOn(boolean value) {
		if (value) {
			OGLUtils.doWiredFrame(true);
		}
	}

	private void checkWiredFrameOff(boolean value) {
		if (value) {
			OGLUtils.doWiredFrame(false);
		}
	}

	public void setWiredFrame(boolean value) {
		this.boundingWiredFrame = value;
	}

}
