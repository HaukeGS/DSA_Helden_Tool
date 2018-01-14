package aventurian;

import java.util.HashMap;
import java.util.Map;

import static aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE.*;
import static aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE.*;

public class SecondaryAttributes {

	private Map<SECONDARY_ATTRIBUTE, SecondaryAttributeHelper> mapping;

	public enum SECONDARY_ATTRIBUTE {
		HITPOINTS, ASTRALPOINTS, KARMALPOINTS, MAGICRESISTANCE, EXHAUSTIONTHRESHHOLD, WOUNDTHRESHHOLD, INITIATIVEVALUE, ATTACKVALUE, DEFENDVALUE, RANGEDVALUE
	}

	public SecondaryAttributes() {
		this.mapping = new HashMap<>();
		mapping.put(HITPOINTS, new SecondaryAttributeHelper());
		mapping.put(ASTRALPOINTS, new SecondaryAttributeHelper());
		mapping.put(KARMALPOINTS, new SecondaryAttributeHelper());
		mapping.put(MAGICRESISTANCE, new SecondaryAttributeHelper());
		mapping.put(EXHAUSTIONTHRESHHOLD, new SecondaryAttributeHelper());
		mapping.put(WOUNDTHRESHHOLD, new SecondaryAttributeHelper());
		mapping.put(INITIATIVEVALUE, new SecondaryAttributeHelper());
		mapping.put(ATTACKVALUE, new SecondaryAttributeHelper());
		mapping.put(DEFENDVALUE, new SecondaryAttributeHelper());
		mapping.put(RANGEDVALUE, new SecondaryAttributeHelper());
	}

	public void updateValues(PrimaryAttributes attributes) {
		calculateHitPoints(attributes);
		calculateAstralPoints(attributes);
		calculateKarmalPoints(attributes);
		calculateMagicResistance(attributes);
		calculateExhaustionThreshhold(attributes);
		calculateWoundThreshhold(attributes);
		calculateInitiativeValue(attributes);
		calculateAttackValue(attributes);
		calculateDefendValue(attributes);
		calculateRangedValue(attributes);
	}

	private void calculateRangedValue(PrimaryAttributes a) {
		int intuition = a.getPrimaryAttribute(INTUITION);
		int dexterity = a.getPrimaryAttribute(DEXTERITY);
		int strength = a.getPrimaryAttribute(STRENGTH);
		get(RANGEDVALUE).setBasis(round((intuition + dexterity + strength) / 5.0));
	}

	private void calculateDefendValue(PrimaryAttributes a) {
		int intuition = a.getPrimaryAttribute(INTUITION);
		int agility = a.getPrimaryAttribute(AGILITY);
		int strength = a.getPrimaryAttribute(STRENGTH);
		get(DEFENDVALUE).setBasis(round((intuition + agility + strength) / 5.0));
	}

	private void calculateAttackValue(PrimaryAttributes a) {
		int courage = a.getPrimaryAttribute(COURAGE);
		int agility = a.getPrimaryAttribute(AGILITY);
		int strength = a.getPrimaryAttribute(STRENGTH);
		get(ATTACKVALUE).setBasis(round((courage + agility + strength) / 5.0));
	}

	private void calculateInitiativeValue(PrimaryAttributes a) {
		int courage = a.getPrimaryAttribute(COURAGE);
		int intuition = a.getPrimaryAttribute(INTUITION);
		int agility = a.getPrimaryAttribute(AGILITY);
		get(INITIATIVEVALUE).setBasis(round((courage * 2 + intuition + agility) / 5.0));
	}

	private void calculateWoundThreshhold(PrimaryAttributes a) {
		int constitution = a.getPrimaryAttribute(CONSTITUTION);
		get(WOUNDTHRESHHOLD).setBasis(round(constitution / 2.0));
	}

	private void calculateExhaustionThreshhold(PrimaryAttributes a) {
		int constitution = a.getPrimaryAttribute(CONSTITUTION);
		get(EXHAUSTIONTHRESHHOLD).setBasis(round(constitution / 2.0));
	}

	private void calculateMagicResistance(PrimaryAttributes a) {
		int courage = a.getPrimaryAttribute(COURAGE);
		int intelligence = a.getPrimaryAttribute(INTELLIGENCE);
		int constitution = a.getPrimaryAttribute(CONSTITUTION);
		get(MAGICRESISTANCE).setBasis(round((courage + intelligence + constitution) / 5.0));
		get(MAGICRESISTANCE).setMax(round(courage / 2.0));
	}

	private void calculateHitPoints(PrimaryAttributes a) {
		int constitution = a.getPrimaryAttribute(CONSTITUTION);
		int strength = a.getPrimaryAttribute(STRENGTH);
		get(HITPOINTS).setBasis(round((constitution * 2 + strength) / 2.0));
		get(HITPOINTS).setMax(round(constitution / 2.0));
	}

	private void calculateAstralPoints(PrimaryAttributes a) {
		int courage = a.getPrimaryAttribute(COURAGE);
		int intuition = a.getPrimaryAttribute(INTUITION);
		int charisma = a.getPrimaryAttribute(CHARISMA);
		get(ASTRALPOINTS).setBasis(round((courage + intuition + charisma) / 2.0));
		get(ASTRALPOINTS).setMax(round(charisma / 2.0));
	}

	private void calculateKarmalPoints(PrimaryAttributes a) {
		get(KARMALPOINTS).setBasis(0);
	}

	private int round(double d) {
		return (int) Math.round(d);
	}

	private SecondaryAttributeHelper get(SECONDARY_ATTRIBUTE a) {
		return mapping.get(a);
	}

	int getValueOf(SECONDARY_ATTRIBUTE a) {
		return mapping.get(a).getActual();
	}

	boolean isIncreasableByBuy(SECONDARY_ATTRIBUTE a) {
		return mapping.get(a).isIncreasableByBuy();
	}
	
	void increaseMod(SECONDARY_ATTRIBUTE a, int mod) {
		mapping.get(a).increaseMod(mod);
	}
	
	void increaseModBuy(SECONDARY_ATTRIBUTE a) {
		if (!isIncreasableByBuy(a)) throw new IllegalStateException("SecondaryAttribute is not increasable by buy! " + a.toString());
		mapping.get(a).increaseModBuy();
	}
	
	boolean isDecreasableByBuy(SECONDARY_ATTRIBUTE a) {
		if (a == HITPOINTS || a == ASTRALPOINTS || a == MAGICRESISTANCE)
			return mapping.get(a).isDecreasableByBuy();
		return false;
	}
	
	void decreaseMod(SECONDARY_ATTRIBUTE a, int mod) {
		mapping.get(a).decreaseMod(mod);
	}
	
	void decreaseModBuy(SECONDARY_ATTRIBUTE a) {
		if (!isDecreasableByBuy(a)) throw new IllegalStateException("SecondaryAttribute is not decreasable by buy! " + a.toString());
		mapping.get(a).decreaseModBuy();
	}

	int getCost(SECONDARY_ATTRIBUTE a) {
		switch (a) {
			case HITPOINTS: 		return 50;
			case ASTRALPOINTS:		return 50;
			case MAGICRESISTANCE: 	return 100;
			default:				throw new IllegalArgumentException("Only HitPoints, AstralPoints and MagicResistance have costs!");
		}
	}
}
