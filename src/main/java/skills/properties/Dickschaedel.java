package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class Dickschaedel extends Property {

	private static final String DESCRIPTION = "Auch am Kopf werden nur 100% TP(B) (anstatt 150%) angerichtet.\r\n" + 
			"Erleichtert das waffenlose Man�ver Kopfsto� um 2 Punkte und verursacht 1 TP(B) mehr.";
	static final String NAME = "Dicksch�del";

	public Dickschaedel() {
		super(NAME, DESCRIPTION, 100);
	}

}
