package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Bosparano extends Language {

	static final String NAME = "Bosparano";
	static final String DESCRIPTION = "Bosparano ist die Sprache des alten Bosparan. Heute ist sie die Wissenschaftssprache auf Aventurien und wird nur noch von Magiern, Gelehrten und Geweihten gesprochen. In vielen Magierakademien ist sie die Lehrsprache und auch in vielen Liturgien findet die Sprache Anwendung. \nDie Sprache wird in Kusliker Zeichen oder Imperialen Zeichen geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Bosparano() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}

}