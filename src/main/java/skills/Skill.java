package skills;

import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.xml.bind.annotation.XmlAttribute;

import aventurian.Aventurian;

public abstract class Skill {

	protected static final Consumer<Aventurian> EMPTY = (Aventurian a) -> {
	};
	@XmlAttribute
	private final String name;
	private final String description;
	private final Consumer<Aventurian> effectOnGain;
	private final Consumer<Aventurian> effectOnLose;
	private final Predicate<Aventurian> requirement;
	protected final int cost;
	private final boolean isRacialSkill;

	public Skill(String name, String description, Consumer<Aventurian> effectOnGain, Consumer<Aventurian> effectOnLose,
			Predicate<Aventurian> requirement, int cost) {
		this.name = name;
		this.description = description;
		this.effectOnGain = effectOnGain;
		this.effectOnLose = effectOnLose;
		this.requirement = requirement;
		this.cost = cost;
		this.isRacialSkill = false;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getLearningCosts() {
		return cost;
	}

	public void gain(Aventurian t) {
		effectOnGain.accept(t);
	}

	public void lose(Aventurian t) {
		effectOnLose.accept(t);
	}

	public boolean isAllowed(Aventurian t) {
		return requirement.test(t);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Skill))
			return false;

		final Skill skill = (Skill) o;

		return name.equals(skill.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return getName() + " (" + getLearningCosts() + ")";
	}
}
