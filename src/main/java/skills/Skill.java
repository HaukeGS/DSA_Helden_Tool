package skills;

import aventurian.Aventurian;

/**
 * @author Jonas
 *
 */
/**
 * @author Jonas
 *
 */
/**
 * @author Jonas
 *
 */
/**
 * @author Jonas
 *
 */
public abstract class Skill implements Comparable<Skill> {

	private final String name;
	private final String description;

	private final boolean isRacialSkill;

	public Skill(String name, String description) {
		this.name = name;
		this.description = description;

		this.isRacialSkill = false;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void atGain(Aventurian t) {
	}

	public void atLose(Aventurian t) {
	}

	/**
	 * 
	 * @param t
	 *            the aventurian which must fulfill the skill's requirements in
	 *            order to keep it
	 * @return true, if this skill can be kept
	 */
	public boolean isAllowedToHave(Aventurian t) {
		return true;
	}

	/**
	 * @param a
	 *            the aventurian which must fulfill the skill's requirements in
	 *            order to buy it
	 * @return true, if this skill can be added
	 */
	public boolean isAllowedToAdd(Aventurian a) {
		return true;
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
		return getName();
	}

	@Override
	public int compareTo(Skill o) {
		return getName().compareTo(o.getName());
	}

	public abstract int getTotalCosts();
}
