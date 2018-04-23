package object.trigger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trigger implements TriggerControllable {

	private String name;
	private Map<String, List<Condition>> conditions;
	private Map<String, List<Action>> actions;

	public Trigger(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void doActions() {
		if (!checkConditions()) return;
		actions.values().stream()
			.flatMap(list -> list.stream())
			.forEach(Action::doAction);
	}

	private boolean checkConditions() {
		for (List<Condition> batch : conditions.values()) {
			for (Condition cond : batch) {
				if (!cond.check()) return false;
			}
		}
		return true;
	}

	@Override
	public void addConditions(String name, List<Condition> condList) {
		conditions.put(name, condList);
	}

	@Override
	public void addCondition(String name, Condition cond) {
		List<Condition> condList = new ArrayList<Condition>();
		condList.add(cond);
		conditions.put(name, condList);
	}

	@Override
	public void addActions(String name, List<Action> actList) {
		actions.put(name, actList);
	}

	@Override
	public void addAction(String name, Action act) {
		List<Action> actList = new ArrayList<Action>();
		actList.add(act);
		actions.put(name, actList);
	}

	@Override
	public void deleteActions(String name) {
		actions.remove(name);
	}

	@Override
	public void deleteConditions(String name) {
		conditions.remove(name);
	}
}
