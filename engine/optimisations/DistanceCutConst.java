package optimisations;

import java.util.Collection;

import cameras.Camera;
import entities.Entity;
import scene.ES;
import terrains.Terrain;
import toolbox.Maths;
import voxels.Chunk;

//Cut during to constants
public class DistanceCutConst {
	
	private static final float TERRAIN_CUT_DISTANCE = 400;
	
	public void cutRender(Camera camera, Collection<Entity> entities, Collection<Terrain> terrains, Collection<Chunk> chunks) {
		cutEntityRender(camera, entities);	
		cutTerrainRender(camera, terrains);
		//cutChunkRender(camera, grids);
	}
	
	private void cutEntityRender(Camera camera, Collection<Entity> entities) {
		for(Entity entity : entities) {
			if(entity.isVisible()) {
				float distance = Maths.distanceFromCamera(entity, camera);
				if(entity.isDetail()) {
					if(distance <= ES.DETAIL_VIEW_DISTANCE) {
						entity.setRendered(true);
					} else {
						entity.setRendered(false);
					}
				} else {
					if(distance <= ES.RENDERING_VIEW_DISTANCE) {
						entity.setRendered(true);
					} else {
						entity.setRendered(false);
					}
				}	
			}
		}
	}
	
	private void cutTerrainRender(Camera camera, Collection<Terrain> terrains) {
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
	
	private void cutChunkRender(Camera camera, Collection<Chunk> chunks) {
		for(Chunk chunk : chunks) {
			
		}
			
	}
	
}
