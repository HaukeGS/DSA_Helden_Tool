package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Hjaldingsch extends Language {
	static final String NAME = "Hjaldingsch";
	static final String DESCRIPTION = "Das Hjaldingsch ist das 'Saga-Thorwalsch'. Es wird von den Skalden und Gelehrten der Thorwaler im Norden gesprochen. \nDie Sprache wird in den Hjaldingschen Runen geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Hjaldingsch() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}

}