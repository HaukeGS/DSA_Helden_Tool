package skills.properties;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.BADPROPERTY)
public class Arkanophobie extends BadProperty {
	static final String NAME = "Arkanophobie";
	static final String DESCRIPTION = "Der Charakter hat panische Angst vor allem Magischen. Er misstraut magisch begabten Kameraden und wird keine magischen Gegenst‰nde verwenden. Auﬂerdem sinkt die MR des Arkanophoben, wenn er weiﬂ, dass er verzaubert werden soll.";

	public Arkanophobie() {
		super(NAME, DESCRIPTION, -75);
	}
	
	@Override
	public boolean isAllowed(Aventurian a) {
		return !a.isMage();
	}

}
