package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.BADPROPERTY)
public class Aberglaube extends BadProperty {
	static final String NAME = "Aberglaube";
	static final String DESCRIPTION = "Der Charakter muss eine Beschreibung des Aberglaubens angeben. Meistens sind es Dinge, Ereignisse oder Umst�nde, die seiner Meinung nach Gl�ck oder Pech bringen oder vor denen er (unbegr�ndet) Angst hat.";

	public Aberglaube() {
		super(NAME, DESCRIPTION, -50);
	}

}
