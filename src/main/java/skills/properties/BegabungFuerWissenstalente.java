package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class BegabungFuerWissenstalente extends Property {

	private static final String DESCRIPTION = "Wird eine Probe auf ein Wissenstalent geworfen, so darf der Spieler einen W�rfel der 3W20 erneut werfen. Das bessere Ergebnis z�hlt.";
	static final String NAME = "Begabung f�r Wissenstalente";

	public BegabungFuerWissenstalente() {
		super(NAME, DESCRIPTION, 500);
	}
}
