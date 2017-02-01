package audio;

import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

public interface AudioMaster {
	
	 void init(); //инициализация
	 public Map<String, Integer> getBuffers(); //вернуть все аудио
	 int getBuffer(String path); //вернуть аудио по расположению 
	 void setListenerData(Vector3f position); //установить слушателя
	 int loadSound(String file); //загрузить аудио
	 void cleanUp(); //очистить аудио-мастер

}
