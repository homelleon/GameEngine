package object.particle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import object.camera.Camera;
import primitive.texture.particle.ParticleMaterial;
import renderer.particle.ParticleRenderer;
import tool.math.Matrix4f;

public class ParticleMaster {

	private static Map<ParticleMaterial, List<Particle>> particles = new HashMap<ParticleMaterial, List<Particle>>();
	private static ParticleRenderer renderer;

	public static void init(Matrix4f projectionMatrix) {
		renderer = new ParticleRenderer(projectionMatrix);
	}

	public static void update(Camera camera) {
		Iterator<Entry<ParticleMaterial, List<Particle>>> mapIterator = particles.entrySet().iterator();
		while (mapIterator.hasNext()) {
			List<Particle> list = mapIterator.next().getValue();
			Iterator<Particle> iterator = list.iterator();
			while (iterator.hasNext()) {
				Particle p = iterator.next();
				boolean stillAlive = p.update(camera);
				if (!stillAlive) {
					iterator.remove();
					if (list.isEmpty()) {
						mapIterator.remove();
					}
				}
			}
			InsertionSort.sortHighToLow(list);
		}
	}

	public static void renderParticles(Camera camera) {
		renderer.render(particles, camera);
	}

	public static void cleanUp() {
		renderer.cleanUp();
	}

	public static void addParticle(Particle particle) {
		List<Particle> list = particles.get(particle.getTexture());
		if (list == null) {
			list = new ArrayList<Particle>();
			particles.put(particle.getTexture(), list);
		}
		list.add(particle);
	}

}