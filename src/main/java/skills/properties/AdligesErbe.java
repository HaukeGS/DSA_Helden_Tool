package skills.properties;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class AdligesErbe extends Property {

	private static final String DESCRIPTION = "Nur für adlige Charaktere.\r\n" + 
			"Der Charakter ist der erste in der Erbfolge und es ist zu erwarten, dass er das Erbe seines Vaters oder seiner Mutter antritt, sollte diese/r dahinscheiden.";
	static final String NAME = "Adliges Erbe";

	public AdligesErbe() {
		super(NAME, DESCRIPTION, 250, 1, 1);
	}

	@Override
	public boolean isAllowedToHave(Aventurian a) {
		return a.hasSkill(Adlig.NAME);
	}
	
	@Override
	public boolean isAllowedToAdd(Aventurian a) {
		return a.hasSkill(Adlig.NAME);
	}
}
