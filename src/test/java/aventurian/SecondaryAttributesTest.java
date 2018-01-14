package aventurian;

import org.junit.Before;
import org.junit.Test;

import aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE;

import static aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE.*;
import static aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE.*;
import static org.junit.Assert.*;

public class SecondaryAttributesTest {

	private SecondaryAttributes toTest;

	@Before
	public void setUp() throws Exception {
		this.toTest = new SecondaryAttributes();
	}

	@Test
	public void updateValuesMinimum() throws Exception {
		PrimaryAttributes attributes = new PrimaryAttributes();
		toTest.updateValues(attributes);
		assertHitPointsMinimum();
		assertAstralPointsMinimum();
		assertKarmalPointsMinium();
		assertMagicResistanceMinimum();
		assertExhaustionThreshholdMinimum();
		assertWoundThreshholdMinimum();
		assertInitiativeValueMinimum();
		assertAttackValueMinimum();
		assertDefendValueMinimum();
		assertRangedValueMinimum();
	}

	@Test
	public void updateValuesMaximum() throws Exception {
		PrimaryAttributes attributes = getMaximumPrimaryAttributes();
		toTest.updateValues(attributes);
		assertHitPointsMaximum();
		assertAstralPointsMaximum();
		assertKarmalPointsMaximum();
		assertMagicResistanceMaximum();
		assertExhaustionThreshholdMaximum();
		assertWoundThreshholdMaximum();
		assertInitiativeValueMaximum();
		assertAttackValueMaximum();
		assertDefendValueMaximum();
		assertRangedValueMaximum();
	}

	@Test
	public void testIsIncreasableByBuy() {
		PrimaryAttributes attributes = getMaximumPrimaryAttributes();
		toTest.updateValues(attributes);
		assertTrue(toTest.isIncreasableByBuy(SECONDARY_ATTRIBUTE.HITPOINTS));
		assertTrue(toTest.isIncreasableByBuy(SECONDARY_ATTRIBUTE.ASTRALPOINTS));
		assertTrue(toTest.isIncreasableByBuy(SECONDARY_ATTRIBUTE.MAGICRESISTANCE));

		assertFalse(toTest.isIncreasableByBuy(SECONDARY_ATTRIBUTE.ATTACKVALUE));
		assertFalse(toTest.isIncreasableByBuy(SECONDARY_ATTRIBUTE.DEFENDVALUE));
		assertFalse(toTest.isIncreasableByBuy(SECONDARY_ATTRIBUTE.INITIATIVEVALUE));
		assertFalse(toTest.isIncreasableByBuy(SECONDARY_ATTRIBUTE.RANGEDVALUE));
		assertFalse(toTest.isIncreasableByBuy(SECONDARY_ATTRIBUTE.WOUNDTHRESHHOLD));
		assertFalse(toTest.isIncreasableByBuy(SECONDARY_ATTRIBUTE.EXHAUSTIONTHRESHHOLD));
		assertFalse(toTest.isIncreasableByBuy(SECONDARY_ATTRIBUTE.KARMALPOINTS));
	}

	@Test
	public void testIsIncreasableByBuyNotValid() {
		PrimaryAttributes attributes = new PrimaryAttributes();
		toTest.updateValues(attributes);
		while (toTest.isIncreasableByBuy(SECONDARY_ATTRIBUTE.HITPOINTS))
			toTest.increaseModBuy(HITPOINTS);
		assertFalse(toTest.isIncreasableByBuy(HITPOINTS));
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseBuyNotValid() {
		PrimaryAttributes attributes = new PrimaryAttributes();
		toTest.updateValues(attributes);
		while (toTest.isIncreasableByBuy(SECONDARY_ATTRIBUTE.HITPOINTS))
			toTest.increaseModBuy(HITPOINTS);
		toTest.increaseModBuy(HITPOINTS);
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseBuyWrongAttribute() {
		PrimaryAttributes attributes = new PrimaryAttributes();
		toTest.updateValues(attributes);
		toTest.increaseModBuy(ATTACKVALUE);
	}

	@Test
    public void testIncreaseBuyValid() {
		PrimaryAttributes attributes = new PrimaryAttributes();
		toTest.updateValues(attributes);
    	int value = toTest.getValueOf(HITPOINTS);
    	assertEquals(12, value);
    	toTest.increaseModBuy(HITPOINTS);
    	assertEquals(value+1, toTest.getValueOf(HITPOINTS));
    }
	
	@Test
	public void testIsDecreasableByBuy() {
		PrimaryAttributes attributes = getMaximumPrimaryAttributes();
		toTest.updateValues(attributes);
		assertTrue(toTest.isDecreasableByBuy(SECONDARY_ATTRIBUTE.HITPOINTS));
		assertTrue(toTest.isDecreasableByBuy(SECONDARY_ATTRIBUTE.ASTRALPOINTS));
		assertTrue(toTest.isDecreasableByBuy(SECONDARY_ATTRIBUTE.MAGICRESISTANCE));

		assertFalse(toTest.isDecreasableByBuy(SECONDARY_ATTRIBUTE.ATTACKVALUE));
		assertFalse(toTest.isDecreasableByBuy(SECONDARY_ATTRIBUTE.DEFENDVALUE));
		assertFalse(toTest.isDecreasableByBuy(SECONDARY_ATTRIBUTE.INITIATIVEVALUE));
		assertFalse(toTest.isDecreasableByBuy(SECONDARY_ATTRIBUTE.RANGEDVALUE));
		assertFalse(toTest.isDecreasableByBuy(SECONDARY_ATTRIBUTE.WOUNDTHRESHHOLD));
		assertFalse(toTest.isDecreasableByBuy(SECONDARY_ATTRIBUTE.EXHAUSTIONTHRESHHOLD));
		assertFalse(toTest.isDecreasableByBuy(SECONDARY_ATTRIBUTE.KARMALPOINTS));
		
	}
	
	@Test
	public void testDecreaseBuyValid() {
		PrimaryAttributes attributes = getMaximumPrimaryAttributes();
		toTest.updateValues(attributes);
		int value = toTest.getValueOf(HITPOINTS);
		toTest.decreaseModBuy(HITPOINTS);
		assertEquals(value - 1, toTest.getValueOf(HITPOINTS));
	}
	
	@Test (expected = IllegalStateException.class)
	public void testDecreaseBuyNotValid() {
		PrimaryAttributes attributes = getMaximumPrimaryAttributes();
		toTest.updateValues(attributes);
		while(toTest.isDecreasableByBuy(HITPOINTS)) toTest.decreaseModBuy(HITPOINTS);
		toTest.decreaseModBuy(HITPOINTS);
	}
	
	@Test (expected = IllegalStateException.class)
	public void testDecreaseBuyWrongAttribute() {
		PrimaryAttributes attributes = getMaximumPrimaryAttributes();
		toTest.updateValues(attributes);
		toTest.decreaseModBuy(DEFENDVALUE);
	}
	
	@Test
	public void testIncreaseMod() {
		int value = toTest.getValueOf(MAGICRESISTANCE);
		toTest.increaseMod(MAGICRESISTANCE, 3);
		assertEquals(value + 3, toTest.getValueOf(MAGICRESISTANCE));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testIncreaseModWrongArgument() {
		toTest.increaseMod(HITPOINTS, -2);
	}
	
	@Test
	public void testDecreaseMod() {
		int value = toTest.getValueOf(EXHAUSTIONTHRESHHOLD);
		toTest.decreaseMod(EXHAUSTIONTHRESHHOLD, 4);
		assertEquals(value - 4, toTest.getValueOf(EXHAUSTIONTHRESHHOLD));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDecreaseModWrongArgument() {
		toTest.decreaseMod(ASTRALPOINTS, -6);
	}
	
	@Test
	public void testGetCost() {
		assertEquals(50, toTest.getCost(HITPOINTS));
		assertEquals(50, toTest.getCost(ASTRALPOINTS));
		assertEquals(100, toTest.getCost(MAGICRESISTANCE));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetCostWrongArgument() {
		toTest.getCost(DEFENDVALUE);
	}

	private PrimaryAttributes getMaximumPrimaryAttributes() {
		PrimaryAttributes attributes = new PrimaryAttributes();
		for (int i = 8; i < 14; i++) {
			attributes.increase(COURAGE);
			attributes.increase(INTELLIGENCE);
			attributes.increase(INTUITION);
			attributes.increase(CHARISMA);
			attributes.increase(DEXTERITY);
			attributes.increase(AGILITY);
			attributes.increase(CONSTITUTION);
			attributes.increase(STRENGTH);
		}
		return attributes;
	}

	private void assertHitPointsMinimum() {
		assertEquals(12, toTest.getValueOf(HITPOINTS));
	}

	private void assertAstralPointsMinimum() {
		assertEquals(12, toTest.getValueOf(ASTRALPOINTS));
	}

	private void assertKarmalPointsMinium() {
		assertEquals(0, toTest.getValueOf(KARMALPOINTS));
	}

	private void assertMagicResistanceMinimum() {
		assertEquals(5, toTest.getValueOf(MAGICRESISTANCE));
	}

	private void assertExhaustionThreshholdMinimum() {
		assertEquals(4, toTest.getValueOf(EXHAUSTIONTHRESHHOLD));
	}

	private void assertWoundThreshholdMinimum() {
		assertEquals(4, toTest.getValueOf(WOUNDTHRESHHOLD));
	}

	private void assertInitiativeValueMinimum() {
		assertEquals(6, toTest.getValueOf(INITIATIVEVALUE));
	}

	private void assertAttackValueMinimum() {
		assertEquals(5, toTest.getValueOf(ATTACKVALUE));
	}

	private void assertDefendValueMinimum() {
		assertEquals(5, toTest.getValueOf(DEFENDVALUE));
	}

	private void assertRangedValueMinimum() {
		assertEquals(5, toTest.getValueOf(RANGEDVALUE));
	}

	private void assertHitPointsMaximum() {
		assertEquals(21, toTest.getValueOf(HITPOINTS));
	}

	private void assertAstralPointsMaximum() {
		assertEquals(21, toTest.getValueOf(ASTRALPOINTS));
	}

	private void assertKarmalPointsMaximum() {
		assertEquals(0, toTest.getValueOf(KARMALPOINTS));
	}

	private void assertMagicResistanceMaximum() {
		assertEquals(8, toTest.getValueOf(MAGICRESISTANCE));
	}

	private void assertExhaustionThreshholdMaximum() {
		assertEquals(7, toTest.getValueOf(EXHAUSTIONTHRESHHOLD));
	}

	private void assertWoundThreshholdMaximum() {
		assertEquals(7, toTest.getValueOf(WOUNDTHRESHHOLD));
	}

	private void assertInitiativeValueMaximum() {
		assertEquals(11, toTest.getValueOf(INITIATIVEVALUE));
	}

	private void assertAttackValueMaximum() {
		assertEquals(8, toTest.getValueOf(ATTACKVALUE));
	}

	private void assertDefendValueMaximum() {
		assertEquals(8, toTest.getValueOf(DEFENDVALUE));
	}

	private void assertRangedValueMaximum() {
		assertEquals(8, toTest.getValueOf(RANGEDVALUE));
	}

	@Test
	public void getValueOf() throws Exception {
		int actual = toTest.getValueOf(HITPOINTS);
		int expected = 0;
		assertEquals(expected, actual);
	}

}