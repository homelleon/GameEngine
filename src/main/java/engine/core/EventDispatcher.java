package core;

import java.lang.reflect.Method;
import java.util.Map;

public class EventDispatcher {
	
	private static EventDispatcher instance;
	
	private EventDispatcher() {};
	
	public EventDispatcher getInstace() {
		if (instance == null)
			instance = new EventDispatcher();
		return instance;
	}
	
	public void bind(String eventName, Object obj, Method callFunction) {
		
	}

}
