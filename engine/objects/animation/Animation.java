package objects.animation;

public class Animation {
	
	private final float lenght; //in seconds
	private final KeyFrame[] keyFrames;
	
	public Animation(float lengthInSeconds, KeyFrame[] frames) {
		this.keyFrames = frames;
		this.lenght = lengthInSeconds;
	}
	
	public float getLength() {
		return this.lenght;
	}
	
	public KeyFrame[] getKeyFrames() {
		return this.keyFrames;
	}

}
