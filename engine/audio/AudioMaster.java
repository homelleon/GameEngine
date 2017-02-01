package audio;

import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

public interface AudioMaster {
	
	 void init(); //�������������
	 public Map<String, Integer> getBuffers(); //������� ��� �����
	 int getBuffer(String path); //������� ����� �� ������������ 
	 void setListenerData(Vector3f position); //���������� ���������
	 int loadSound(String file); //��������� �����
	 void cleanUp(); //�������� �����-������

}
