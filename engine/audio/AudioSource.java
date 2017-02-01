package audio;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector3f;

public interface AudioSource {

		public String getName(); //вернуть имя аудио
		public void play(); //начать проигрывание адуио
		public void delete();//удалить аудио
		public void pause(); //приостановить проигрывание аудио
		public void continuePlaying(); //продолжить проигрывание
		public void stop(); //остановить проигрывание аудио
		public void setVelocity(Vector3f speed); //установить скорость аудио
		public void setLooping(boolean loop); //установить зацикливание
		public boolean isPlaying(); //вернуть, если проигрывается
		public void setVolume(float volume); 		//установить громкость
		public void setPitch(float pitch); 	//установить высоту звука
		public void setPosition(Vector3f position); //установить позицию звука

}
