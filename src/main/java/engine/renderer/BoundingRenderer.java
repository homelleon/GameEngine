package renderer;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import object.camera.Camera;
import object.entity.Entity;
import primitive.buffer.VAO;
import primitive.model.Mesh;
import primitive.model.Model;
import shader.bounding.BoundingShader;
import tool.GraphicUtils;
import tool.math.Maths;
import tool.math.Matrix4f;

public class BoundingRenderer {

	private BoundingShader shader;
	private boolean boundingWiredFrame = true;

	public BoundingRenderer(Matrix4f projectionMatrix) {
		shader = new BoundingShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(Map<Model, List<Entity>> entities, Map<Model, List<Entity>> normalEntities, Camera camera) {
		checkWiredFrameOn(boundingWiredFrame);
		shader.start();
		shader.loadViewMatrix(camera);
		
		drawBoundings(entities);
		drawBoundings(normalEntities);
		shader.stop();
		checkWiredFrameOff(boundingWiredFrame);
	}
	
	private void drawBoundings(Map<Model, List<Entity>> entities) {
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
				entity.getRotation().getY(), entity.getRotation().getZ(), entity.getScale().getX());
		shader.loadTranformationMatrix(transformationMatrix);
	}

	private void checkWiredFrameOn(boolean value) {
		if (value) GraphicUtils.doWiredFrame(true);
	}

	private void checkWiredFrameOff(boolean value) {
		if (value) GraphicUtils.doWiredFrame(false);
	}

	public void setWiredFrame(boolean value) {
		boundingWiredFrame = value;
	}

}
