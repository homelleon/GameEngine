package renderEngine;

import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector4f;

import particles.ParticleMaster;
import postProcessing.PostProcessing;
import scene.Scene;

public class SceneRenderer {
	
	public void render(Scene scene) {
		
	}
	
	private void renderToScreen(Scene scene) {
		waterFBOs.unbindCurrentFrameBuffer();
		multisampleFbo.bindFrameBuffer();
		optimisation.optimize(scene.getCameras().get(cameraName), scene.getEntities().values(), scene.getTerrains().values(), scene.getVoxelGrids().values());
	    renderer.renderScene(scene, camera, new Vector4f(0, -1, 0, 15), environmentMap);
	    waterRenderer.render(scene.getWaters().values(), scene.getCameras().get(cameraName), scene.getLights().get("Sun"));
	    ParticleMaster.renderParticles(scene.getCameras().get(cameraName));
	    multisampleFbo.unbindFrameBuffer();
	    multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT0, outputFbo);
	    multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT1, outputFbo2);
	    PostProcessing.doPostProcessing(outputFbo.getColourTexture(), outputFbo2.getColourTexture());
	    guiRenderer.render(scene.getGuis().values());
	    renderText();
	}
	
	private void renderWaterSurface(Scene scene) {
		renderWaterReflection(scene);
		renderWaterRefraction(scene);
	}
	
	private void renderWaterReflection(Scene scene) {
		renderer.renderShadowMap(scene, "Sun", cameraName);
    	waterFBOs.bindReflectionFrameBuffer();
		float distance = 2 * (scene.getCameras().get(cameraName).getPosition().y - scene.getWaters().get("Water").getHeight());
		scene.getCameras().get(cameraName).getPosition().y -= distance;
		scene.getCameras().get(cameraName).invertPitch();
		//renderer.processEntity(map.getPlayers().get(playerName));
		renderer.renderScene(scene, camera, new Vector4f(0, 1, 0, -scene.getWaters().get("Water").getHeight()), environmentMap);
		camera.getPosition().y += distance;
		camera.invertPitch();
	}
	
	private void renderWaterRefraction(Scene scene) {
		waterFBOs.bindRefractionFrameBuffer();
		renderer.renderScene(scene, camera, new Vector4f(0, -1, 0, scene.getWaters().get("Water").getHeight()+1f), environmentMap);
	}
	
	

}
