package texts;

import fontMeshCreator.GuiText;

public class TimeText {
	
	private GuiText text;
	private float startTime;
	private float duration;
	
	public TimeText(GuiText text, float startTime, float duration) {
		this.text = text;
		this.startTime = startTime;
		this.duration = duration;
	}
	
	public float getStartTime() {
		return this.startTime;
	}
	
	public float getDuration() {
		return this.duration;
	}

}
