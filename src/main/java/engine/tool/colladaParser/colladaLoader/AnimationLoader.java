package tool.colladaParser.colladaLoader;

import java.nio.FloatBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;

import tool.colladaParser.dataStructures.AnimationData;
import tool.colladaParser.dataStructures.JointTransformData;
import tool.colladaParser.dataStructures.KeyFrameData;
import tool.colladaParser.xmlParser.XmlNode;
import tool.math.VMatrix4f;
import tool.math.vector.Vector3fF;

public class AnimationLoader {

	private static final VMatrix4f CORRECTION = new VMatrix4f().rotate((float) Math.toRadians(-90),
			new Vector3fF(1, 0, 0));

	private XmlNode animationData;
	private XmlNode jointHierarchy;

	public AnimationLoader(XmlNode animationData, XmlNode jointHierarchy) {
		this.animationData = animationData;
		this.jointHierarchy = jointHierarchy;
	}

	public AnimationData extractAnimation() {
		String rootNode = findRootJointName();
		float[] times = getKeyTimes();
		float duration = times[times.length - 1];
		KeyFrameData[] keyFrames = initKeyFrames(times);
		List<XmlNode> animationNodes = animationData.getChildren("animation");
		for (XmlNode jointNode : animationNodes) {
			loadJointTransforms(keyFrames, jointNode, rootNode);
		}
		return new AnimationData(duration, keyFrames);
	}

	private float[] getKeyTimes() {
		XmlNode timeData = animationData.getChild("animation").getChild("source").getChild("float_array");
		String[] rawTimes = timeData.getData().split(" ");
		float[] times = new float[rawTimes.length];
		for (int i = 0; i < times.length; i++) {
			times[i] = Float.parseFloat(rawTimes[i]);
		}
		return times;
	}

	private KeyFrameData[] initKeyFrames(float[] times) {
		KeyFrameData[] frames = new KeyFrameData[times.length];
		for (int i = 0; i < frames.length; i++) {
			frames[i] = new KeyFrameData(times[i]);
		}
		return frames;
	}

	private void loadJointTransforms(KeyFrameData[] frames, XmlNode jointData, String rootNodeId) {
		String jointNameId = getJointName(jointData);
		String dataId = getDataId(jointData);
		XmlNode transformData = jointData.getChildWithAttribute("source", "id", dataId);
		String[] rawData = transformData.getChild("float_array").getData().split(" ");
		processTransforms(jointNameId, rawData, frames, jointNameId.equals(rootNodeId));
	}

	private String getDataId(XmlNode jointData) {
		XmlNode node = jointData.getChild("sampler").getChildWithAttribute("input", "semantic", "OUTPUT");
		return node.getAttribute("source").substring(1);
	}

	private String getJointName(XmlNode jointData) {
		XmlNode channelNode = jointData.getChild("channel");
		String data = channelNode.getAttribute("target");
		return data.split("/")[0];
	}

	private void processTransforms(String jointName, String[] rawData, KeyFrameData[] keyFrames, boolean root) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		float[] matrixData = new float[16];
		for (int i = 0; i < keyFrames.length; i++) {
			for (int j = 0; j < 16; j++) {
				matrixData[j] = Float.parseFloat(rawData[i * 16 + j]);
			}
			buffer.clear();
			buffer.put(matrixData);
			buffer.flip();
			VMatrix4f transform = new VMatrix4f();
			transform.load(buffer);
			transform.transpose();
			if (root) {
				// because up axis in Blender is different to up axis in game
				transform.mul(CORRECTION);
			}
			keyFrames[i].addJointTransform(new JointTransformData(jointName, transform));
		}
	}

	private String findRootJointName() {
		XmlNode skeleton = jointHierarchy.getChild("visual_scene").getChildWithAttribute("node", "id", "Armature");
		return skeleton.getChild("node").getAttribute("id");
	}

}
