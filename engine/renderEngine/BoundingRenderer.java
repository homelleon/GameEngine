package renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import boundings.BoundingShader;
import cameras.Camera;
import entities.Entity;
import models.TexturedModel;
import toolbox.Maths;

public class BoundingRenderer {
	
	private BoundingShader shader;
	
	public BoundingRenderer(Matrix4f projectionMatrix) {
		this.shader = new BoundingShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();		
	}
	
	public void render(Map<TexturedModel, List<Entity>> entities, Camera camera) {
		shader.start();
		shader.loadViewMatrix(camera);
		for(TexturedModel model : entities.keySet()) {
			for(Entity entity : entities.get(model)) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getBSphere().getRadius(), 
						GL11.GL_UNSIGNED_INT, 0);
			}
		}
		shader.stop();
	}
	
	public void prepareInstance(Entity entity) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTranformationMatrix(transformationMatrix);
	}

}
