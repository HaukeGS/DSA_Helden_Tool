package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class BegabungFuerFertigkeitstalente extends Property {

	private static final String DESCRIPTION = "Wird eine Probe auf ein Fertigkeitstalent geworfen, so darf der Spieler einen Würfel der 3W20 erneut werfen. Das bessere Ergebnis zählt.";
	static final String NAME = "Begabung für Fertigkeitstalente";

	public BegabungFuerFertigkeitstalente() {
		super(NAME, DESCRIPTION, 250);
	}
}
