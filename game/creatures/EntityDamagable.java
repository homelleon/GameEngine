package creatures;

import objects.entities.EntityInterface;

public interface EntityDamagable extends EntityInterface {
	
	int getHelth();
	void setHelth(int value);
	void getDamage(float value);

}
