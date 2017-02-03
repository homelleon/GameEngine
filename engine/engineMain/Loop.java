package engineMain;

import scene.Scene;

public interface Loop extends Runnable { 
	
	void run(); //запуск
	Scene getScene(); //вернуть игровую сцену
	void setTerrainWiredFrame(boolean value); //показать сетку ландшафта
	void setEntityWiredFrame(boolean value); //показать сетку объектов
	void setScenePaused(boolean value); //установка паузы для сцены
}
