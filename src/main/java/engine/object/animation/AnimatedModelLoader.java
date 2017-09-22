package object.animation;

import object.animatedModel.AnimatedModel;
import object.animatedModel.Joint;
import object.openglObject.VAO;
import object.texture.Texture;
import tool.MyFile;
import tool.colladaParser.colladaLoader.ColladaLoader;
import tool.colladaParser.dataStructures.AnimatedModelData;
import tool.colladaParser.dataStructures.JointData;
import tool.colladaParser.dataStructures.MeshData;
import tool.colladaParser.dataStructures.SkeletonData;

public class AnimatedModelLoader {

	/**
	 * Creates an AnimatedEntity from the data in an entity file. It loads up
	 * the collada model data, stores the extracted data in a VAO, sets up the
	 * joint heirarchy, and loads up the entity's texture.
	 * 
	 * @param entityFile
	 *            - the file containing the data for the entity.
	 * @return The animated entity (no animation applied though)
	 */
	public static AnimatedModel loadEntity(MyFile modelFile, MyFile textureFile) {
		AnimatedModelData entityData = ColladaLoader.loadColladaModel(modelFile, 50);
		VAO model = createVao(entityData.getMeshData());
		Texture texture = loadTexture(textureFile);
		SkeletonData skeletonData = entityData.getJointsData();
		Joint headJoint = createJoints(skeletonData.headJoint);
		return new AnimatedModel(model, texture, headJoint, skeletonData.jointCount);
	}

	/**
	 * Loads up the diffuse texture for the model.
	 * 
	 * @param textureFile
	 *            - the texture file.
	 * @return The diffuse texture.
	 */
	private static Texture loadTexture(MyFile textureFile) {
		Texture diffuseTexture = Texture.newTexture(textureFile).anisotropic().build();
		return diffuseTexture;
	}

	/**
	 * Constructs the joint-hierarchy skeleton from the data extracted from the
	 * collada file.
	 * 
	 * @param data
	 *            - the joints data from the collada file for the head joint.
	 * @return The created joint, with all its descendants added.
	 */
	private static Joint createJoints(JointData data) {
		Joint joint = new Joint(data.index, data.nameId, data.bindLocalTransform);
		for (JointData child : data.children) {
			joint.addChild(createJoints(child));
		}
		return joint;
	}

	/**
	 * Stores the mesh data in a VAO.
	 * 
	 * @param data
	 *            - all the data about the mesh that needs to be stored in the
	 *            VAO.
	 * @return The VAO containing all the mesh data for the model.
	 */
	private static VAO createVao(MeshData data) {
		VAO vao = VAO.create();
		vao.bind();
		vao.createIndexBuffer(data.getIndices());
		vao.createAttribute(0, 3, data.getVertices());
		vao.createAttribute(1, 2, data.getTextureCoords());
		vao.createAttribute(2, 3, data.getNormals());
		vao.createIntAttribute(3, 3, data.getJointIds());
		vao.createAttribute(4, 3, data.getVertexWeights());
		VAO.unbind();
		return vao;
	}

}
