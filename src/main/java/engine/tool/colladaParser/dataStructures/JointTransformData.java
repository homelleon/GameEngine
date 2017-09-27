package tool.colladaParser.dataStructures;

import tool.math.VMatrix4f;

/**
 * This contains the data for a transformation of one joint, at a certain time
 * in an animation. It has the name of the joint that it refers to, and the
 * local transform of the joint in the pose position.
 * 
 * @author Karl
 *
 */
public class JointTransformData {

	public final String jointNameId;
	public final VMatrix4f jointLocalTransform;

	public JointTransformData(String jointNameId, VMatrix4f jointLocalTransform) {
		this.jointNameId = jointNameId;
		this.jointLocalTransform = jointLocalTransform;
	}
}
