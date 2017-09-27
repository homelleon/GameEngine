package object.animation;

import java.util.HashMap;
import java.util.Map;

import core.display.DisplayManager;
import object.animatedModel.AnimatedModel;
import object.animatedModel.Joint;
import tool.math.VMatrix4f;

public class Animator {

	private AnimatedModel entity;

	private Animation currentAnimation;
	private float animationTime = 0;

	public Animator(AnimatedModel entity) {
		// this.entity = enitity;
	}

	public void doAnimation(Animation animation) {
		this.animationTime = 0;
		this.currentAnimation = animation;
	}

	public void update() {
		if (this.currentAnimation == null) {
			return;
		}
		increaseAnimationTime();
		Map<String, VMatrix4f> currentPose = calculateCurrentAnimationPose();
		applyPoseToJoints(currentPose, entity.getRootJoint(), new VMatrix4f());
	}

	public void increaseAnimationTime() {
		this.animationTime += DisplayManager.getFrameTimeSeconds();
		if (this.animationTime > this.currentAnimation.getLength()) {
			this.animationTime %= this.currentAnimation.getLength();
		}
	}

	private Map<String, VMatrix4f> calculateCurrentAnimationPose() {
		KeyFrame[] frames = getPreviousAndNextFrames();
		float progression = calculateProgression(frames[0], frames[1]);
		return interpolatePoses(frames[0], frames[1], progression);
	}

	private void applyPoseToJoints(Map<String, VMatrix4f> currentPose, Joint joint, VMatrix4f parentTransform) {
		VMatrix4f currentLocalTransform = currentPose.get(joint.name);
		VMatrix4f currentTransform = VMatrix4f.mul(parentTransform, currentLocalTransform);
		for (Joint childJoint : joint.children) {
			applyPoseToJoints(currentPose, childJoint, currentTransform);
			joint.setAnimationTransform(currentTransform);
		}
	}

	private KeyFrame[] getPreviousAndNextFrames() {
		KeyFrame[] allFrames = this.currentAnimation.getKeyFrames();
		KeyFrame previousFrame = allFrames[0];
		KeyFrame nextFrame = allFrames[0];
		for (int i = 1; i < allFrames.length; i++) {
			nextFrame = allFrames[i];
			if (nextFrame.getTimeStamp() > this.animationTime) {
				break;
			}
			previousFrame = allFrames[i];
		}
		return new KeyFrame[] { previousFrame, nextFrame };
	}

	private float calculateProgression(KeyFrame previousFrame, KeyFrame nextFrame) {
		float totalTime = nextFrame.getTimeStamp() - previousFrame.getTimeStamp();
		float currentTime = this.animationTime - previousFrame.getTimeStamp();
		return currentTime / totalTime;
	}

	private Map<String, VMatrix4f> interpolatePoses(KeyFrame previousFrame, KeyFrame nextFrame, float progression) {
		Map<String, VMatrix4f> currentPose = new HashMap<String, VMatrix4f>();
		for (String jointName : previousFrame.getJointKeyFrames().keySet()) {
			JointTransform previousTransform = previousFrame.getJointKeyFrames().get(jointName);
			JointTransform nextTransform = nextFrame.getJointKeyFrames().get(jointName);
			JointTransform currentTransform = JointTransform.interpolate(previousTransform, previousTransform,
					progression);
			currentPose.put(jointName, currentTransform.getLocalTransform());
		}
		return currentPose;
	}

}
