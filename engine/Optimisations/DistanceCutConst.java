package Optimisations;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import scene.Settings;
import terrains.Terrain;
import toolbox.Maths;

//Cut during to constants
public class DistanceCutConst {

	public void cutRender(Camera camera, List<Entity> entities, List<Terrain> terrains) {
		cutEntityRender(camera, entities);	
		cutTerrainRender(camera, terrains);
	}
	
	private void cutEntityRender(Camera camera, List<Entity> entities) {
		for(Entity entity : entities) {
			if(entity.isVisible()) {
				float distance = Maths.distanceFromCamera(entity, camera);
				if(distance <= Settings.RENDERING_VIEW_DISTANCE) {
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
				if(distance <= 50) {
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
