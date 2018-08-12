package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class BegabungFuerKoerpertalente extends Property {

	private static final String DESCRIPTION = "Wird eine Probe auf ein K�rpertalent geworfen, so darf der Spieler einen W�rfel der 3W20 erneut werfen. Das bessere Ergebnis z�hlt.";
	static final String NAME = "Begabung f�r K�rpertalente";

	public BegabungFuerKoerpertalente() {
		super(NAME, DESCRIPTION, 500);
	}
}
