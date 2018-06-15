package aventurian;

import database.Database;
import logging.Logger;
import skills.attributes.secondary.Lebenspunkte;
import skills.attributes.secondary.Magieresistenz;
import skills.attributes.secondary.SecondaryAttribute;

public class RaceAventurianManager extends BaseAventurianManager {

	private final PropertyAventurianManager propertyManager;
	private final AttributesAventurianManager attributesManager;

	public RaceAventurianManager(AventurianManagerFacade aventurianManagerFacade, Database db,
			PropertyAventurianManager propertyManager, AttributesAventurianManager attributesManager, Logger logger) {
		super(aventurianManagerFacade, db, logger);
		this.propertyManager = propertyManager;
		this.attributesManager = attributesManager;
	}

	void buyRaceMods(Race race) {
		applyMagicResistenceMod(race);

		applyHitPointsMod(race);

		database.getSkillsFor(race).forEach(propertyManager::addProperty);
	}

	public void applyHitPointsMod(Race race) {
		final int hitPointsMod = database.getHitPointsModFor(race);

		final SecondaryAttribute hitpoints = database.getSecondaryAttributes().stream()
				.filter(p -> Lebenspunkte.NAME.equals(p.getName())).findFirst()
				.orElseThrow(() -> new IllegalStateException("could not find " + Lebenspunkte.NAME));
		attributesManager.applyRaceMod(hitpoints, hitPointsMod);
	}

	public void applyMagicResistenceMod(Race race) {
		final int magicResistanceMod = database.getMagicResistanceModFor(race);

		final SecondaryAttribute magicresistance = database.getSecondaryAttributes().stream()
				.filter(p -> Magieresistenz.NAME.equals(p.getName())).findFirst()
				.orElseThrow(() -> new IllegalStateException("could not find " + Magieresistenz.NAME));
		attributesManager.applyRaceMod(magicresistance, magicResistanceMod);
	}

}
