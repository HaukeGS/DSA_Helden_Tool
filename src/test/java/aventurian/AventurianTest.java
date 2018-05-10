package aventurian;

import static aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE.COURAGE;
import static aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE.ASTRALPOINTS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Observer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE;
import aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE;
import skills.Skill;
import skills.languages.Language;
import skills.properties.BadProperty;
import skills.properties.Property;

@RunWith(MockitoJUnitRunner.class)
public class AventurianTest {

	public static final int AP = 16500;
	public static final String AVENTURIAN_NAME = "testAventurian";
	private Aventurian toTest;
	@Mock
	private Observer mockedObserver;
	@Mock
	private PrimaryAttributes mockedPrimaryAttributes;
	@Mock
	private SecondaryAttributes mockedSecondaryAttributes;

	@Before
	public void setUp() throws Exception {
		toTest = new Aventurian(AVENTURIAN_NAME, AP, mockedPrimaryAttributes, mockedSecondaryAttributes, Race.DWARF);
		toTest.addObserver(mockedObserver);
	}

	@After
	public void tearDown() throws Exception {
		toTest.deleteObservers();
	}

	@Test
	public void testIsPrimaryAttributeIncreasable() {
		toTest.isPrimaryAttributeIncreasable(COURAGE);
		verify(mockedPrimaryAttributes).isIncreasable(COURAGE);
	}

	@Test
	public void testIsPrimaryAttributeDecreasable() {
		toTest.isPrimaryAttributeDecreasable(COURAGE);
		verify(mockedPrimaryAttributes).isDecreasable(COURAGE);
	}

	@Test
	public void testIsSecondaryAttributeIncreasableByBuy() {
		toTest.isSecondaryAttributeIncreasableByBuy(ASTRALPOINTS);
		verify(mockedSecondaryAttributes).isIncreasableByBuy(ASTRALPOINTS);
	}

	@Test
	public void testIsSecondaryAttributeDecreasableByBuy() {
		toTest.isSecondaryAttributeDecreasableByBuy(ASTRALPOINTS);
		verify(mockedSecondaryAttributes).isDecreasableByBuy(ASTRALPOINTS);
	}

	@Test
	public void testIncreaseSecondaryAttribute() {
		toTest.increaseSecondaryAttribute(ASTRALPOINTS, 1);
		verify(mockedSecondaryAttributes).increaseMod(ASTRALPOINTS, 1);
	}

	@Test
	public void testDecreaseSecondaryAttribute() {
		toTest.decreaseSecondaryAttribute(ASTRALPOINTS, 1);
		verify(mockedSecondaryAttributes).decreaseMod(ASTRALPOINTS, 1);
	}

	@Test
	public void testIncreaseSecondaryAttributeByBuy() {
		toTest.increaseSecondaryAttributeByBuy(ASTRALPOINTS);
		verify(mockedSecondaryAttributes).increaseModBuy(ASTRALPOINTS);
	}

	@Test
	public void testDecreaseSecondaryAttributeByBuy() {
		toTest.decreaseSecondaryAttributeByBuy(ASTRALPOINTS);
		verify(mockedSecondaryAttributes).decreaseModBuy(ASTRALPOINTS);
	}

	@Test
	public void testGetSecondaryAttributeCost() {
		toTest.getSecondaryAttributeCost(ASTRALPOINTS);
		verify(mockedSecondaryAttributes).getCost(ASTRALPOINTS);
	}

	@Test
	public void testPayValid() throws Exception {
		toTest.pay(1000);
		final int expected = AP - 1000;
		final int actual = toTest.getAdventurePoints();
		assertEquals(expected, actual);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPayTooMuch() throws Exception {
		toTest.pay(20000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPayNegative() throws Exception {
		toTest.pay(-20);
	}

	@Test
	public void testRefundValid() throws Exception {
		toTest.refund(500);
		final int expected = AP + 500;
		final int actual = toTest.getAdventurePoints();
		assertEquals(expected, actual);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test
	public void testRefundMuch() throws Exception {
		toTest.refund(20000);
		final int expected = AP + 20000;
		final int actual = toTest.getAdventurePoints();
		assertEquals(expected, actual);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRefundNegative() throws Exception {
		toTest.refund(-20);
	}

	@Test
	public void testCanPay() throws Exception {
		assertFalse(toTest.canPay(AP + 1));
	}

	@Test
	public void testGetBadPropertySum() throws Exception {
		assertEquals(0, toTest.getBadPropertySum());
		final BadProperty bP1 = mock(BadProperty.class);
		when(bP1.getLevel()).thenReturn(7);
		toTest.add(bP1);
		assertEquals(7, toTest.getBadPropertySum());

		final BadProperty bP2 = mock(BadProperty.class);
		when(bP2.getLevel()).thenReturn(5);
		toTest.add(bP2);
		assertEquals(7 + 5, toTest.getBadPropertySum());
	}

	@Test
	public void testAdd() throws Exception {
		final Language testLanguage = mock(Language.class);
		when(testLanguage.getName()).thenReturn("testLanguage");
		toTest.add(testLanguage);
		assertTrue(toTest.hasSkill(testLanguage));
		verify(testLanguage).gain(toTest);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test
	public void testRemoveSkillNoRequirement() throws Exception {
		final Skill s1 = mock(Skill.class);
		when(s1.getName()).thenReturn("testSkill1");
		toTest.add(s1);
		assertTrue(toTest.hasSkill(s1));
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
		
		final Skill s2 = mock(Skill.class);
		when(s2.getName()).thenReturn("testSkill2");
		when(s2.isAllowedToHave(toTest)).thenReturn(true);
		toTest.add(s2);
		assertTrue(toTest.hasSkill(s2));
		verify(mockedObserver, atLeastOnce()).update(toTest, null);

		toTest.remove(s1);
		assertFalse(toTest.hasSkill(s1));
		verify(s1).lose(toTest);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}
	
	@Test
	public void testRemoveSkillIsRequirement() throws Exception {
		final Skill requirementSkill = mock(Skill.class);
		when(requirementSkill.getName()).thenReturn("requirementSkill");
		toTest.add(requirementSkill);
		assertTrue(toTest.hasSkill(requirementSkill));
		verify(mockedObserver, atLeastOnce()).update(toTest, null);

		final Skill dependentSkill = mock(Skill.class);
		when(dependentSkill.getName()).thenReturn("dependentSkill");
		toTest.add(dependentSkill);
		assertTrue(toTest.hasSkill(dependentSkill));
		
		when(dependentSkill.isAllowedToHave(toTest)).thenReturn(false);
		
		toTest.remove(requirementSkill);
		assertFalse(toTest.hasSkill(requirementSkill));
		verify(requirementSkill).lose(toTest);
		verify(mockedObserver, atLeastOnce()).update(toTest, dependentSkill);
	}
	

	@Test
	public void testGetLanguages() {
		assertTrue(toTest.getLanguages().isEmpty());

		final Language l1 = mock(Language.class);
		toTest.add(l1);
		assertEquals(1, toTest.getLanguages().size());

		final Language l2 = mock(Language.class);
		toTest.add(l2);
		assertEquals(2, toTest.getLanguages().size());

		final Property p = mock(Property.class);
		toTest.add(p);
		assertEquals(2, toTest.getLanguages().size());

	}

	@Test
	public void testHasSkill() throws Exception {
		final Property p = mock(Property.class);
		when(p.getName()).thenReturn("p");
		assertFalse(toTest.hasSkill(p));
		toTest.add(p);
		assertTrue(toTest.hasSkill(p));

		final BadProperty bP = mock(BadProperty.class);
		when(bP.getName()).thenReturn("bP");
		assertFalse(toTest.hasSkill(bP));
		toTest.add(bP);
		assertTrue(toTest.hasSkill(bP));

		final Language l = mock(Language.class);
		when(l.getName()).thenReturn("l");
		assertFalse(toTest.hasSkill(l));
		toTest.add(l);
		assertTrue(toTest.hasSkill(l));
	}

	@Test
	public void testHasSkillByName() {
		final Property p = mock(Property.class);
		when(p.getName()).thenReturn("p");
		assertFalse(toTest.hasSkill(p.getName()));
		toTest.add(p);
		assertTrue(toTest.hasSkill(p.getName()));
	}

	@Test
	public void testGetSumOfPrimaryAttributes() throws Exception {
		toTest.getSumOfPrimaryAttributes();
		verify(mockedPrimaryAttributes).getSum();
	}

	@Test
	public void testGetPrimaryAttribute() throws Exception {
		toTest.getPrimaryAttribute(COURAGE);
		verify(mockedPrimaryAttributes).getPrimaryAttribute(COURAGE);
	}

	@Test
	public void testGetMaxOfPrimaryAttribute() throws Exception {
		toTest.getMaxOfPrimaryAttribute(COURAGE);
		verify(mockedPrimaryAttributes).getMaximumOfPrimaryAttribute(COURAGE);
	}

	@Test
	public void testIncreasePrimaryAttribute() throws Exception {
		toTest.increasePrimaryAttribute(COURAGE);
		verify(mockedPrimaryAttributes).increase(COURAGE);
		verify(mockedSecondaryAttributes, times(2)).updateValues(mockedPrimaryAttributes);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test
	public void testDecrasePrimaryAttribute() throws Exception {
		toTest.addObserver(mockedObserver);
		toTest.decrasePrimaryAttribute(COURAGE);
		verify(mockedPrimaryAttributes).decrease(COURAGE);
		verify(mockedSecondaryAttributes, times(2)).updateValues(mockedPrimaryAttributes);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test
	public void testIncreaseMaximumOfPrimaryAttribute() throws Exception {
		toTest.increaseMaximumOfPrimaryAttribute(COURAGE);
		verify(mockedPrimaryAttributes).increaseMaximum(COURAGE);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test
	public void testDecreaseMaximumOfPrimaryAttribute() throws Exception {
		toTest.decreaseMaximumOfPrimaryAttribute(COURAGE);
		verify(mockedPrimaryAttributes).decreaseMaximum(COURAGE);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test
	public void testGetPointsInAdvantagesValid() throws Exception {
		assertEquals(0, toTest.getPointsInAdvantages());
		final Property p = mock(Property.class);
		when(p.getLearningCosts()).thenReturn(200);
		when(p.isAdvantage()).thenReturn(true);
		toTest.add(p);
		assertEquals(200, toTest.getPointsInAdvantages());
		final Property p2 = mock(Property.class);
		when(p2.getLearningCosts()).thenReturn(300);
		when(p2.isAdvantage()).thenReturn(true);
		toTest.add(p2);
		assertEquals(500, toTest.getPointsInAdvantages());
	}

	@Test
	public void testGetPointsOutDisadvantagesValid() throws Exception {
		assertEquals(0, toTest.getPointsOutDisadvantages());
		final Property p = mock(Property.class);
		when(p.getLearningCosts()).thenReturn(200);
		when(p.isDisadvantage()).thenReturn(true);
		toTest.add(p);
		assertEquals(200, toTest.getPointsOutDisadvantages());

		final BadProperty bp = mock(BadProperty.class);
		when(bp.getLearningCosts()).thenReturn(50);
		when(bp.getLevel()).thenReturn(5);

		toTest.add(bp);
		assertEquals(450, toTest.getPointsOutDisadvantages());
	}

	@Test
	public void testSetName() {
		assertEquals(AVENTURIAN_NAME, toTest.getName());
		toTest.setName("newName");
		assertEquals("newName", toTest.getName());
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test
	public void testHasNativeTongue() {
		assertFalse(toTest.hasNativeTongue());
		final Language l = mock(Language.class);
		when(l.isNativeTongue()).thenReturn(true);
		toTest.add(l);
		assertTrue(toTest.hasNativeTongue());
	}

	@Test
	public void testGetRace() {
		assertEquals(toTest.getRace(), Race.DWARF);
	}

	@Test
	public void testGetSecondaryAttribute() {
		toTest.getSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS);
		verify(mockedSecondaryAttributes).getValueOf(SECONDARY_ATTRIBUTE.HITPOINTS);
	}

	@Test
	public void testGetDisadvantages() {
		final Property disadvantage = mock(Property.class);
		when(disadvantage.isDisadvantage()).thenReturn(true);
		final Property advantage = mock(Property.class);
		when(advantage.isDisadvantage()).thenReturn(false);
		final Property badProperty = mock(BadProperty.class);
		when(badProperty.isDisadvantage()).thenReturn(true);

		toTest.add(disadvantage);
		toTest.add(advantage);
		toTest.add(badProperty);

		assertEquals(2, toTest.getDisadvantages().size());
	}

	@Test
	public void testGetAdvantages() {
		final Property disadvantage = mock(Property.class);
		when(disadvantage.isAdvantage()).thenReturn(false);
		final Property advantage = mock(Property.class);
		when(advantage.isAdvantage()).thenReturn(true);
		final Property badProperty = mock(BadProperty.class);
		when(badProperty.isAdvantage()).thenReturn(false);

		toTest.add(disadvantage);
		toTest.add(advantage);
		toTest.add(badProperty);

		assertEquals(1, toTest.getAdvantages().size());
	}

	@Test
	public void testGetLevelSumOfLanguages() {
		assertEquals(0, toTest.getLevelSumOfLanguages());

		final Language l1 = mock(Language.class);
		when(l1.getLevel()).thenReturn(3);
		toTest.add(l1);

		assertEquals(3, toTest.getLevelSumOfLanguages());

		final Language l2 = mock(Language.class);
		when(l2.getLevel()).thenReturn(4);
		toTest.add(l2);

		assertEquals(7, toTest.getLevelSumOfLanguages());

		toTest.remove(l1);

		assertEquals(4, toTest.getLevelSumOfLanguages());
	}

	@Test
	public void testGetAPInAttributes() {
		when(mockedPrimaryAttributes.getPrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE)).thenReturn(8);
		when(mockedPrimaryAttributes.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE)).thenReturn(8);
		when(mockedPrimaryAttributes.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTUITION)).thenReturn(8);
		when(mockedPrimaryAttributes.getPrimaryAttribute(PRIMARY_ATTRIBUTE.CHARISMA)).thenReturn(8);
		when(mockedPrimaryAttributes.getPrimaryAttribute(PRIMARY_ATTRIBUTE.DEXTERITY)).thenReturn(8);
		when(mockedPrimaryAttributes.getPrimaryAttribute(PRIMARY_ATTRIBUTE.AGILITY)).thenReturn(8);
		when(mockedPrimaryAttributes.getPrimaryAttribute(PRIMARY_ATTRIBUTE.CONSTITUTION)).thenReturn(8);
		when(mockedPrimaryAttributes.getPrimaryAttribute(PRIMARY_ATTRIBUTE.STRENGTH)).thenReturn(8);
		assertEquals(0, toTest.getAPinAttributes());

		when(mockedPrimaryAttributes.getPrimaryAttribute(PRIMARY_ATTRIBUTE.DEXTERITY)).thenReturn(9);
		assertEquals(220, toTest.getAPinAttributes());
		when(mockedPrimaryAttributes.getPrimaryAttribute(PRIMARY_ATTRIBUTE.STRENGTH)).thenReturn(9);
		assertEquals(2 * 220, toTest.getAPinAttributes());
		when(mockedPrimaryAttributes.getPrimaryAttribute(PRIMARY_ATTRIBUTE.DEXTERITY)).thenReturn(10);
		assertEquals(2 * 220 + 250, toTest.getAPinAttributes());
	}
}