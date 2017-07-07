package object.trigger;

import java.util.List;

public interface TriggerControllable {
	
	public void addConditions(String name, List<Condition> condList);
	public void addCondition(String name, Condition cond);
	public void deleteConditions(String name);
	public void addActions(String name, List<Action> actList);
	public void addAction(String name, Action cond);
	public void deleteActions(String name);
	public void doActions();
}
