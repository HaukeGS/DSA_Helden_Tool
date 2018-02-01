package skills;

import java.util.function.Consumer;
import java.util.function.Predicate;

import aventurian.Aventurian;

public class Property extends Skill {

	public Property(String name, String description, int cost, Consumer<Aventurian> effectOnGain,
			Consumer<Aventurian> effectOnLose, Predicate<Aventurian> requirement) {
		super(name, description, effectOnGain, effectOnLose, requirement, cost);
	}

	public boolean isAdvantage() {
		return cost > 0;
	}

	public boolean isDisadvantage() {
		return !isAdvantage();
	}

	@Override
	public int getLearningCost() {
		if (isAdvantage())
			return cost;
		return cost * -1;
	}
}
