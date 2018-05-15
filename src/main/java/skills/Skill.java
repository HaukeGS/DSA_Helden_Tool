package skills;

import aventurian.Aventurian;

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

	public void gain(Aventurian t) {
	}

	public void lose(Aventurian t) {
	}

	/**
	 * 
	 * @param t
	 *            the aventurian which must fulfill the skill's requirements in
	 *            order to buy and keep it
	 * @return
	 */
	public boolean isAllowedToHave(Aventurian t) {
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
	
	abstract int getTotalCosts();
}
