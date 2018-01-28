package aventurian;

import static aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE.COURAGE;
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
import org.mockito.junit.MockitoJUnitRunner;

import aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE;
import skills.BadProperty;
import skills.Language;
import skills.Property;

@RunWith(MockitoJUnitRunner.class)
public class AventurianTest {

	public static final int AP = 16500;
	public static final String AVENTURIAN_NAME = "testAventurian";
	private Aventurian toTest;
	private Observer mockedObserver;

	@Before
	public void setUp() throws Exception {
		toTest = new Aventurian(AVENTURIAN_NAME, AP);
		mockedObserver = mock(Observer.class);
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
	public void testAddProperty() throws Exception {
		final Property testProp = mock(Property.class);
		toTest.add(testProp);
		assertTrue(toTest.hasSkill(testProp));
		verify(testProp).gain(toTest);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test
	public void testRemoveProperty() throws Exception {
		final Property testProp = mock(Property.class);

		toTest.add(testProp);
		assertTrue(toTest.hasSkill(testProp));
		verify(mockedObserver, atLeastOnce()).update(toTest, null);

		toTest.remove(testProp);
		assertFalse(toTest.hasSkill(testProp));
		verify(testProp).lose(toTest);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test
	public void testAddBadProperty() throws Exception {
		final BadProperty testProp = mock(BadProperty.class);
		toTest.add(testProp);
		assertTrue(toTest.hasSkill(testProp));
		verify(testProp).gain(toTest);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test
	public void testRemoveBadProperty() throws Exception {
		final BadProperty testProp = mock(BadProperty.class);

		toTest.add(testProp);
		assertTrue(toTest.hasSkill(testProp));
		verify(mockedObserver, atLeastOnce()).update(toTest, null);

		toTest.remove(testProp);
		assertFalse(toTest.hasSkill(testProp));
		verify(testProp).lose(toTest);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
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
	public void testAddLanguage() throws Exception {
		final Language testLanguage = mock(Language.class);
		toTest.add(testLanguage);
		assertTrue(toTest.hasSkill(testLanguage));
		verify(testLanguage).gain(toTest);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test
	public void testRemoveLanguage() throws Exception {
		final Language testLanguage = mock(Language.class);

		toTest.add(testLanguage);
		assertTrue(toTest.hasSkill(testLanguage));
		verify(mockedObserver, atLeastOnce()).update(toTest, null);

		toTest.remove(testLanguage);
		assertFalse(toTest.hasSkill(testLanguage));
		verify(testLanguage).lose(toTest);
		verify(mockedObserver, atLeastOnce()).update(toTest, null); // Why
																	// atLeastOnce???
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
		assertFalse(toTest.hasSkill(p));
		toTest.add(p);
		assertTrue(toTest.hasSkill(p));

		final BadProperty bP = mock(BadProperty.class);
		assertFalse(toTest.hasSkill(bP));
		toTest.add(bP);
		assertTrue(toTest.hasSkill(bP));

		final Language l = mock(Language.class);
		assertFalse(toTest.hasSkill(l));
		toTest.add(l);
		assertTrue(toTest.hasSkill(l));
	}

	@Test
	public void testGetSumOfPrimaryAttributes() throws Exception {
		assertEquals(64, toTest.getSumOfPrimaryAttributes());
		toTest.increasePrimaryAttribute(COURAGE);
		toTest.increasePrimaryAttribute(COURAGE);
		toTest.increasePrimaryAttribute(COURAGE);
		assertEquals(67, toTest.getSumOfPrimaryAttributes());
	}

	@Test
	public void testGetPrimaryAttribute() throws Exception {
		assertEquals(8, toTest.getPrimaryAttribute(COURAGE));
	}

	@Test
	public void testGetMaxOfPrimaryAttribute() throws Exception {
		assertEquals(14, toTest.getMaxOfPrimaryAttribute(COURAGE));
	}

	@Test
	public void testIncreasePrimaryAttribute() throws Exception {
		final PrimaryAttributes pri = mock(PrimaryAttributes.class);
		final SecondaryAttributes second = mock(SecondaryAttributes.class);
		toTest = new Aventurian("", 100, pri, second);
		toTest.addObserver(mockedObserver);
		toTest.increasePrimaryAttribute(COURAGE);
		verify(pri).increase(COURAGE);
		verify(second, times(2)).updateValues(pri);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test
	public void testDecrasePrimaryAttribute() throws Exception {
		final PrimaryAttributes pri = mock(PrimaryAttributes.class);
		final SecondaryAttributes second = mock(SecondaryAttributes.class);
		toTest = new Aventurian("", 100, pri, second);
		toTest.addObserver(mockedObserver);
		toTest.decrasePrimaryAttribute(COURAGE);
		verify(pri).decrease(COURAGE);
		verify(second, times(2)).updateValues(pri);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test
	public void testIncreaseMaximumOfPrimaryAttribute() throws Exception {
		final PrimaryAttributes pri = mock(PrimaryAttributes.class);
		toTest = new Aventurian("", 100, pri, null);
		toTest.addObserver(mockedObserver);
		toTest.increaseMaximumOfPrimaryAttribute(COURAGE);
		verify(pri).increaseMaximum(COURAGE);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test
	public void testDecreaseMaximumOfPrimaryAttribute() throws Exception {
		final PrimaryAttributes pri = mock(PrimaryAttributes.class);
		toTest = new Aventurian("", 100, pri, null);
		toTest.addObserver(mockedObserver);
		toTest.decreaseMaximumOfPrimaryAttribute(COURAGE);
		verify(pri).decreaseMaximum(COURAGE);
		verify(mockedObserver, atLeastOnce()).update(toTest, null);
	}

	@Test
	public void testGetPointsInAdvantagesValid() throws Exception {
		assertEquals(0, toTest.getPointsInAdvantages());
		final Property p = mock(Property.class);
		when(p.getCost()).thenReturn(200);
		when(p.isAdvantage()).thenReturn(true);
		toTest.add(p);
		assertEquals(200, toTest.getPointsInAdvantages());
		final Property p2 = mock(Property.class);
		when(p2.getCost()).thenReturn(300);
		when(p2.isAdvantage()).thenReturn(true);
		toTest.add(p2);
		assertEquals(500, toTest.getPointsInAdvantages());
	}

	@Test
	public void testGetPointsOutDisadvantagesValid() throws Exception {
		assertEquals(0, toTest.getPointsOutDisadvantages());
		final Property p = mock(Property.class);
		when(p.getCost()).thenReturn(200);
		when(p.isDisadvantage()).thenReturn(true);
		toTest.add(p);
		assertEquals(200, toTest.getPointsOutDisadvantages());

		final BadProperty bp = mock(BadProperty.class);
		when(bp.getCost()).thenReturn(50);
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
	public void testGetSecondaryAttribute() {
		assertEquals(12, toTest.getSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS));
	}
}