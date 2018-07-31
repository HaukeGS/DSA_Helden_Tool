package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class Glueck extends Property {

	private static final String DESCRIPTION = "Kann pro aventurischem Tag 1W3-1 W�rfelw�rfe wiederholen (oder vom Meister wiederholen lassen) und das bessere Ergebnis w�hlen.";
	static final String NAME = "Gl�ck";

	public Glueck() {
		super(NAME, DESCRIPTION, 600);
	}

}
