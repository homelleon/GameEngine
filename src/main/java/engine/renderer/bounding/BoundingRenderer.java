package renderer.bounding;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import object.camera.ICamera;
import object.entity.Entity;
import primitive.buffer.VAO;
import primitive.model.Mesh;
import primitive.model.Model;
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

	public void render(Map<Model, List<Entity>> entities, Map<Model, List<Entity>> normalEntities, 
			Map<Model, List<Entity>> decorEntities, ICamera camera) {
		checkWiredFrameOn(boundingWiredFrame);
		shader.start();
		shader.loadViewMatrix(camera);
		
		for (Model model : entities.keySet()) {
			Mesh bModel = model.getMesh().getBBox().getModel();
			prepareModel(bModel);
			entities.get(model).forEach(entity -> {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, bModel.getVertexCount(),
						GL11.GL_UNSIGNED_INT, 0);
			});
			unbindModel();
		}
		
		for (Model model : normalEntities.keySet()) {
			Mesh bModel = model.getMesh().getBBox().getModel();
			prepareModel(bModel);
			for (Entity entity : normalEntities.get(model)) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, bModel.getVertexCount(),
						GL11.GL_UNSIGNED_INT, 0);
			}
			unbindModel();
		}
		
		for (Model model : decorEntities.keySet()) {
			Mesh bModel = model.getMesh().getBBox().getModel();
			prepareModel(bModel);
			for (Entity entity : decorEntities.get(model)) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, bModel.getVertexCount(),
						GL11.GL_UNSIGNED_INT, 0);
			}
			unbindModel();
		}
		shader.stop();
		checkWiredFrameOff(boundingWiredFrame);
	}

	public Mesh prepareModel(Mesh model) {
		VAO vao = model.getVAO();
		vao.bind(0, 1, 2);
		return model;
	}

	private void unbindModel() {
		VAO.unbind(0, 1, 2);
	}

	public void prepareInstance(Entity entity) {
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
