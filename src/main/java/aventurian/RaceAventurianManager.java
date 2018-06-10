package aventurian;

import java.util.Optional;

import aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE;
import database.Database;
import logging.Logger;
import skills.attributes.secondary.Lebenspunkte;
import skills.attributes.secondary.Magieresistenz;
import skills.attributes.secondary.SecondaryAttribute;

public class RaceAventurianManager extends BaseAventurianManager {

	private final PropertyAventurianManager propertyManager;
	private final AttributesAventurianManager attributesManager;

	public RaceAventurianManager(Optional<Aventurian> a, Database db, PropertyAventurianManager propertyManager,
			AttributesAventurianManager attributesManager, Logger logger) {
		super(a, db, logger);
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
		if (hitPointsMod > 0)
			increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS, hitPointsMod);
		else
			decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS, Math.abs(hitPointsMod));
		final SecondaryAttribute hitpoints = database.getSecondaryAttributes().stream()
				.filter(p -> Lebenspunkte.NAME.equals(p.getName())).findFirst()
				.orElseThrow(() -> new IllegalStateException("could not find " + Lebenspunkte.NAME));
		attributesManager.applyRaceMod(hitpoints, hitPointsMod);
	}

	public void applyMagicResistenceMod(Race race) {
		final int magicResistanceMod = database.getMagicResistanceModFor(race);
		if (magicResistanceMod > 0)
			increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.MAGICRESISTANCE, magicResistanceMod);
		else
			decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.MAGICRESISTANCE, Math.abs(magicResistanceMod));
		final SecondaryAttribute magicresistance = database.getSecondaryAttributes().stream()
				.filter(p -> Magieresistenz.NAME.equals(p.getName())).findFirst()
				.orElseThrow(() -> new IllegalStateException("could not find " + Magieresistenz.NAME));
		attributesManager.applyRaceMod(magicresistance, magicResistanceMod);
	}

	private void increaseSecondaryAttribute(SECONDARY_ATTRIBUTE a, int mod) {
		// attributesManager.increaseSecondaryAttribute(a);
		aventurian.ifPresent(av -> {
			final int cost = av.getSecondaryAttributeCost(a);
			av.increaseSecondaryAttribute(a, mod);
			pay(cost * mod);
		});
	}

	private void decreaseSecondaryAttribute(SecondaryAttributes.SECONDARY_ATTRIBUTE a, int mod) {
		aventurian.ifPresent(av -> {
			final int cost = av.getSecondaryAttributeCost(a);
			av.decreaseSecondaryAttribute(a, mod);
			refund(cost * mod);
		});
	}

}
