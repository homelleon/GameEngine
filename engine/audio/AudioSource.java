package audio;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector3f;

public interface AudioSource {

		public String getName(); //������� ��� �����
		public void play(); //������ ������������ �����
		public void delete();//������� �����
		public void pause(); //������������� ������������ �����
		public void continuePlaying(); //���������� ������������
		public void stop(); //���������� ������������ �����
		public void setVelocity(Vector3f speed); //���������� �������� �����
		public void setLooping(boolean loop); //���������� ������������
		public boolean isPlaying(); //�������, ���� �������������
		public void setVolume(float volume); 		//���������� ���������
		public void setPitch(float pitch); 	//���������� ������ �����
		public void setPosition(Vector3f position); //���������� ������� �����

}
