package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.BADPROPERTY)
public class Aberglaube extends BadProperty {
	static final String NAME = "Aberglaube";
	static final String DESCRIPTION = "Der Charakter muss eine Beschreibung des Aberglaubens angeben. Meistens sind es Dinge, Ereignisse oder Umstände, die seiner Meinung nach Glück oder Pech bringen oder vor denen er (unbegründet) Angst hat.";

	public Aberglaube() {
		super(NAME, DESCRIPTION, -50);
	}

}
