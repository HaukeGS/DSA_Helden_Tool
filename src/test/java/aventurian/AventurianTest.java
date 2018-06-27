package aventurian;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Observer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skills.IncreasableSkill;
import skills.Skill;
import skills.attributes.primary.Agilitaet;
import skills.attributes.primary.Klugheit;
import skills.attributes.primary.Koerperkraft;
import skills.attributes.primary.PrimaryAttribute;
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

	@Before
	public void setUp() throws Exception {
		toTest = new Aventurian(AVENTURIAN_NAME, AP, Race.DWARF);
		toTest.addObserver(mockedObserver);
	}

	@After
	public void tearDown() throws Exception {
		toTest.deleteObservers();
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
		final Skill testSkill = mock(Skill.class);
		when(testSkill.getName()).thenReturn("testSkill");
		toTest.add(testSkill);
		assertTrue(toTest.hasSkill(testSkill));
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
	public void testIncreaseSkill() {
		final IncreasableSkill s = mock(IncreasableSkill.class);
		toTest.increaseSkill(s);
		verify(s).increase();
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test
	public void testDecreaseSkill() {
		final IncreasableSkill s = mock(IncreasableSkill.class);
		toTest.decreaseSkill(s);
		verify(s).decrease();
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test(expected = IllegalStateException.class)
	public void testGetPrimaryAttributeUnknown() {
		toTest.getPrimaryAttribute(Agilitaet.NAME);
	}

	@Test
	public void testGetPrimaryAttribute() {
		final PrimaryAttribute koerperkraft = mock(PrimaryAttribute.class);
		when(koerperkraft.getName()).thenReturn(Koerperkraft.NAME);
		when(koerperkraft.getLevel()).thenReturn(8);
		toTest.add(koerperkraft);
		assertEquals(8, toTest.getPrimaryAttribute(Koerperkraft.NAME));
	}

	@Test
	public void testGetSumOfPrimaryAttribute() {
		final PrimaryAttribute koerperkraft = mock(PrimaryAttribute.class);
		when(koerperkraft.getLevel()).thenReturn(8);
		final PrimaryAttribute agilitaet = mock(PrimaryAttribute.class);
		when(agilitaet.getLevel()).thenReturn(12);
		toTest.add(koerperkraft);
		toTest.add(agilitaet);
		assertEquals(20, toTest.getSumOfPrimaryAttributes());
	}

	@Test
	public void testGetMaxOfPrimaryAttribute() {
		final PrimaryAttribute koerperkraft = mock(PrimaryAttribute.class);
		when(koerperkraft.getLevel()).thenReturn(8);
		when(koerperkraft.getName()).thenReturn(Koerperkraft.NAME);
		final PrimaryAttribute agilitaet = mock(PrimaryAttribute.class);
		when(agilitaet.getLevel()).thenReturn(12);
		when(agilitaet.getName()).thenReturn(Agilitaet.NAME);
		final PrimaryAttribute intelligenz = mock(PrimaryAttribute.class);
		when(intelligenz.getLevel()).thenReturn(9);
		when(intelligenz.getName()).thenReturn(Klugheit.NAME);
		toTest.add(koerperkraft);
		toTest.add(agilitaet);
		toTest.add(intelligenz);
		assertEquals(12, toTest.getMaximumOfPrimaryAttributes(Koerperkraft.NAME, Agilitaet.NAME, Klugheit.NAME));
	}

	@Test
	public void testGetDependingSkillsRemoveRecursive() {
		final Skill toRemove = mock(Skill.class);
		final Skill dependingSkill = mock(Skill.class);
		final Skill independingSkill = mock(Skill.class);
		when(toRemove.getName()).thenReturn("toRemove");
		when(dependingSkill.getName()).thenReturn("dependingSkill");
		when(independingSkill.getName()).thenReturn("independedSkill");
		when(dependingSkill.isAllowedToHave(toTest)).thenAnswer(a -> toTest.hasSkill(toRemove));
		when(independingSkill.isAllowedToHave(toTest)).thenReturn(true);
		toTest.add(toRemove);
		toTest.add(dependingSkill);

		final int expected = 1;
		int actual = toTest.getDependingSkillsForRemove(toRemove).size();
		assertEquals(expected, actual);
		assertTrue(toTest.hasSkill(dependingSkill));
		assertTrue(toTest.hasSkill(toRemove));

		toTest.add(independingSkill);
		actual = toTest.getDependingSkillsForRemove(toRemove).size();
		assertEquals(expected, actual);
		assertTrue(toTest.hasSkill(dependingSkill));
		assertTrue(toTest.hasSkill(toRemove));
		assertTrue(toTest.hasSkill(independingSkill));
	}

	@Test
	public void testGetDependingSkillsDecrease() {
		final IncreasableSkill toDecrease = mock(IncreasableSkill.class);
		final Skill dependingSkill = mock(Skill.class);
		when(toDecrease.getName()).thenReturn("toRemove");
		when(toDecrease.getLevel()).thenReturn(10).thenReturn(9);
		when(toDecrease.isAllowedToHave(toTest)).thenReturn(true);
		when(dependingSkill.getName()).thenReturn("dependingSkill");
		when(dependingSkill.isAllowedToHave(toTest))
				.thenAnswer(a -> toTest.hasSkill(toDecrease) && toDecrease.getLevel() >= 10);
		toTest.add(toDecrease);
		toTest.add(dependingSkill);
		assertEquals(1, toTest.getDependingSkillsForDecrease(toDecrease).size());
	}

	@Test
	public void testGetDependingSkillsAdd() {
		final Skill toAdd = mock(IncreasableSkill.class);
		final Skill dependingSkill = mock(Skill.class);
		when(toAdd.getName()).thenReturn("toRemove");
		when(toAdd.isAllowedToHave(toTest)).thenReturn(true);
		when(dependingSkill.getName()).thenReturn("dependingSkill");
		when(dependingSkill.isAllowedToHave(toTest)).thenAnswer(a -> !toTest.hasSkill(toAdd));
		toTest.add(dependingSkill);
		assertEquals(1, toTest.getDependingSkillsForAdd(toAdd).size());
	}

	@Test
	public void testGetDependingSkillsIncrease() {
		final IncreasableSkill toIncrease = mock(IncreasableSkill.class);
		final Skill dependingSkill = mock(Skill.class);
		when(toIncrease.getName()).thenReturn("toRemove");
		when(toIncrease.getLevel()).thenReturn(9).thenReturn(10);
		when(toIncrease.isAllowedToHave(toTest)).thenReturn(true);
		when(dependingSkill.getName()).thenReturn("dependingSkill");
		when(dependingSkill.isAllowedToHave(toTest))
				.thenAnswer(a -> toTest.hasSkill(toIncrease) && toIncrease.getLevel() < 10);
		toTest.add(toIncrease);
		toTest.add(dependingSkill);
		assertEquals(1, toTest.getDependingSkillsForDecrease(toIncrease).size());
	}

}