package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class GlueckImSpiel extends Property {

	private static final String DESCRIPTION = "Erleichtert Proben auf Brett-/Kartenspiel um bis zu 7 Punkte, je nachdem wie viel Gl�ck im Spiel vorhanden ist.";
	static final String NAME = "Gl�ck im Spiel";

	public GlueckImSpiel() {
		super(NAME, DESCRIPTION, 100);
	}

}
