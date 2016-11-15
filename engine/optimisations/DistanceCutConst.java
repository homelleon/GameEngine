package optimisations;

import java.util.List;

import entities.Camera;
import entities.Entity;
import scene.EngineSettings;
import terrains.Terrain;
import terrains.TerrainTextured;
import toolbox.Maths;

//Cut during to constants
public class DistanceCutConst {
	
	private static final float TERRAIN_CUT_DISTANCE = 500;
	
	public void cutRender(Camera camera, List<Entity> entities, List<Terrain> terrains) {
		cutEntityRender(camera, entities);	
		cutTerrainRender(camera, terrains);
	}
	
	private void cutEntityRender(Camera camera, List<Entity> entities) {
		for(Entity entity : entities) {
			if(entity.isVisible()) {
				float distance = Maths.distanceFromCamera(entity, camera);
				if(distance <= EngineSettings.RENDERING_VIEW_DISTANCE) {
					entity.setRendered(true);
				} else {
					entity.setRendered(false);
				}
			} else {
				entity.setRendered(false);
			}
			
		}
	}
	
	private void cutTerrainRender(Camera camera, List<Terrain> terrains) {
		for(Terrain terrain : terrains) {
			if(terrain.isVisible()) {
				float distance = Maths.distanceFromCamera(terrain, camera);
				if(distance <= TERRAIN_CUT_DISTANCE) {
					terrain.setRendered(true);
				} else {
					terrain.setRendered(false);
				}
			} else {
				terrain.setRendered(false);
			}
			
		}
	}
	
}
