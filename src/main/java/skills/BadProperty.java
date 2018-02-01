package skills;

import java.util.function.Predicate;

import aventurian.Aventurian;

public class BadProperty extends Property {
	private static final int MAX_LEVEL = 12;
	private static final int MIN_LEVEL = 5;
	private int level;

	public BadProperty(String name, String description, int cost, Predicate<Aventurian> requirement) {
		super(name, description, cost, EMPTY, EMPTY, requirement);
		this.level = MIN_LEVEL;
	}

	public void increase() {
		if (level >= MAX_LEVEL)
			throw new IllegalStateException("Bad Property cannot be over max level!");
		level++;
	}

	public void decrease() {
		if (level <= MIN_LEVEL)
			throw new IllegalStateException("Bad Property level cannot be less than 5!");
		level--;
	}

	public int getLevel() {
		return level;
	}

	public boolean isIncreasable() {
		return level < MAX_LEVEL;
	}

	public boolean isDecreasable() {
		return level > MIN_LEVEL;
	}
}
