package renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import boundings.BoundingBox;
import boundings.BoundingShader;
import cameras.Camera;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import toolbox.Maths;
import toolbox.OGLUtils;

public class BoundingRenderer {
	
	private BoundingShader shader;
	private boolean boundingWiredFrame = true;
	
	public BoundingRenderer(Matrix4f projectionMatrix) {
		this.shader = new BoundingShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();		
	}
	
	public void render(Map<TexturedModel, List<Entity>> entities, Camera camera) {		
		checkWiredFrameOn(boundingWiredFrame);
		shader.start();
		shader.loadViewMatrix(camera);
		for(TexturedModel model : entities.keySet()) {
			RawModel bModel = model.getRawModel().getBBox().getModel();
			prepareModel(bModel);
			for(Entity entity : entities.get(model)) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getBBox().getModel().getVertexCount(), 
						GL11.GL_UNSIGNED_INT, 0);
			}
			unbindModel();
		}
		shader.stop();
		checkWiredFrameOff(boundingWiredFrame);
	}
	
	public void prepareModel(RawModel model) {
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
	}
	
	private void unbindModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	public void prepareInstance(Entity entity) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTranformationMatrix(transformationMatrix);
	}
	
	private void checkWiredFrameOn(boolean value) {
		if(value) {
			OGLUtils.doWiredFrame(true);
		}
	}
	
	private void checkWiredFrameOff(boolean value) {
		if(value) {
			OGLUtils.doWiredFrame(false);
		}
	}
	
	public void setWiredFrame(boolean value) {
		this.boundingWiredFrame = value;
	}
	
//	private RawModel createBBoxModel(Entity entity) {
//		BoundingBox bbox = entity.getModel().getRawModel().getBBox();
//		Vector3f min = bbox.getMin();
//		Vector3f max = bbox.getMax();
//		
//	}

}
