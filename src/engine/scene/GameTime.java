package engine.scene;

import engine.renderEngine.DisplayManager;

public class GameTime {
	
	private float timeLength;
	private float time;
	private float dayTime;
	private float sunTime;
	private boolean sunRise = false;
	
	public GameTime(int timeLength){
		this.timeLength = timeLength;		
	}
	
	public float getDayTime() {
		return dayTime;
	}

	
	public float getTime() {
		return time;
	}
	
	public void start() {
		time += DisplayManager.getFrameTimeSeconds() * timeLength;
		dayTime = time % 24;
		if(dayTime < 24){
			sunTime += 1;
		}else{
			sunTime -= 1;
		}
	}

	public float getSunTime() {
		return sunTime;
	}
	
}
