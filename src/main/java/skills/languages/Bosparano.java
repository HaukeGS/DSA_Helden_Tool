package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Bosparano extends Language {

	static final String NAME = "Bosparano";
	static final String DESCRIPTION = "Bosparano ist die Sprache des alten Reiches. Heute findet die Sprache nur noch rituell Anwednung, dort aber recht viel. In vielen Magierakademien ist sie die Lehrsprache und auch in vielen Liturgien ist sie ein groﬂer Bestandteil. \nDie Sprache wird in Kusliker Zeichen oder Imperialen Zeichen geschrieben.";

	public Bosparano() {
		super(NAME, DESCRIPTION, 5, 50);
	}

}