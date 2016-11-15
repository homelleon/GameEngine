package creatures;

import entities.Entity;

public interface EntityDamagable extends Entity {
	
	int getHelth();
	void setHelth(int value);
	void getDamage(float value);

}
