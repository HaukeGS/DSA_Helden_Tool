package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class BesondererBesitz extends Property {

	private static final String DESCRIPTION = "Dem Charakter steht ein Gegenstand (oder sogar Wissen oder Information) zu, dessen Geldwert schwierig zu bestimmen ist (sonst Ausrüstungsvorteil). Die AP und der genaue Vorteil werden mit dem Spielleiter gemeinsam ermittelt.\r\n" + 
			"Hier ein paar Beispiele an denen man sich orientieren kann:\r\n" + 
			"Ein Bauplan für eine besonders gefertigte Waffe (Wissen das sonst nur den Zunftobersten zugänglich ist) – 100 AP\r\n" + 
			"Eine schriftlich fixierte, besonders detaillierte Thesis für ein Artefakt (Bringt Probenerleichtertung auf die Herstelleung des Artefakts) – 150 AP\r\n" + 
			"Ein Familienamulett, das einmal im Monat einen ARMATRUTZ mit 2 RS wirken kann (persönlicher Wert, Geldwert variiert stark abhängig vom Artefaktmagier) – 200 AP\r\n" + 
			"Für einen Geweihten eine zusätzliche, gut gefertigte Waffe – 250 AP";
	static final String NAME = "Besonderer Besitz";
	
	public BesondererBesitz() {
		super(NAME, DESCRIPTION, 50, 1, 100);
	}

	@Override
	public int getUpgradeCosts() {
		return getLearningCosts();
	}

	@Override
	public int getDowngradeRefund() {
		return getLearningCosts();
	}

	@Override
	public int getTotalCosts() {
		return getLevel() * getLearningCosts();
	}
}
