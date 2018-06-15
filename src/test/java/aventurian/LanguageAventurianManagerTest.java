package aventurian;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.junit.MockitoJUnitRunner;

import skills.languages.Language;

@RunWith(MockitoJUnitRunner.class)
public class LanguageAventurianManagerTest extends BaseTest {

	LanguageAventurianManager toTest;


	@Before
	public void setUp() throws Exception {
		toTest = new LanguageAventurianManager(mockedFacade, mockedDatabase, mockedLogger);
		toTest.changeAventurian(mockedAventurian);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddLanguageAsNativeTongueAlreadyNativeTongue() {
		final Language l = createLanguageMock(true, true);
		when(l.isNativeTongue()).thenReturn(true);
		toTest.addLanguageAsNativeTongue(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddLanguageAsNativeTongueHasAlreadyLanguage() {
		final Language l = createLanguageMock(true, true);
		when(mockedAventurian.hasSkill(l)).thenReturn(true);
		toTest.addLanguageAsNativeTongue(l);
	}

	@Test
	public void testAddLanguageAllConditionsMet() {
		final Language l = createLanguageMock(true, true);
//		when(aventurian.hasSkill(l)).thenReturn(false);
//		when(aventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE)).thenReturn(8);
//		when(aventurian.getLevelSumOfLanguages()).thenReturn(0);
		toTest.addLanguage(l);

		final InOrder correctOrder = inOrder(mockedAventurian);
		correctOrder.verify(mockedAventurian).add(l);
		correctOrder.verify(mockedAventurian).pay(anyInt());
	}

	@Test(expected = IllegalStateException.class)
	public void testAddLanguageAlreadyHasSkill() {
		final Language l = createLanguageMock(true, true);
		when(mockedAventurian.hasSkill(l)).thenReturn(true);
		toTest.addLanguage(l);
	}

	
	@Test(expected = IllegalStateException.class)
	public void testAddLanguageExceedsMaxSum() {
		final Language l = createLanguageMock(true, true);
		when(l.isAllowedToAdd(mockedAventurian)).thenReturn(false);
		toTest.addLanguage(l);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testIncreaseLanguageExceedsMaxSum() {
		final Language l = createLanguageMock(true, true);
		when(mockedAventurian.hasSkill(l)).thenReturn(true);
		when(l.isAllowedToIncrease(mockedAventurian)).thenReturn(false);
		toTest.increaseLanguage(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddLanguageNotAllowed() {
		final Language l = createLanguageMock(false, true);

		toTest.addLanguage(l);

	}

	@Test
	public void testAddLanguageAsNativeTongue() {
		final Language l = createLanguageMock(true, true);
		when(l.getLevel()).thenReturn(1).thenReturn(2).thenReturn(3).thenReturn(4).thenReturn(5);
		toTest.addLanguageAsNativeTongue(l);

		verify(mockedAventurian, never()).pay(anyInt());
		verify(l, times(3)).increase();
		final InOrder correctOrder = inOrder(mockedAventurian, l);
		correctOrder.verify(l).setNativeTongue(true);
		correctOrder.verify(mockedAventurian).add(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddLanguageAsNativeTongueHasAlreadyNativeTongue() {
		final Language l = createLanguageMock(true, true);
		when(mockedAventurian.hasSkill(l)).thenReturn(false);
		when(l.isNativeTongue()).thenReturn(false);
		when(mockedAventurian.hasNativeTongue()).thenReturn(true);

		toTest.addLanguageAsNativeTongue(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddLanguageAsNativeTongueNotAllowed() {
		final Language l = createLanguageMock(false, true);
		toTest.addLanguageAsNativeTongue(l);
	}

	@Test
	public void testAddLanguageAsNativeTongueNotIncreasable() {
		final Language l = createLanguageMock(true, true);
		when(l.getLevel()).thenReturn(1).thenReturn(2).thenReturn(3);
		when(l.isAllowedToIncrease(mockedAventurian)).thenReturn(true).thenReturn(true).thenReturn(false);
		toTest.addLanguageAsNativeTongue(l);

		verify(l, times(2)).increase();
		final InOrder correctOrder = inOrder(mockedAventurian, l);
		correctOrder.verify(l).setNativeTongue(true);
		correctOrder.verify(mockedAventurian).add(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testRemoveLanguageNotOwned() {
		final Language l = createLanguageMock(true, true);
		when(mockedAventurian.hasSkill(l)).thenReturn(false);

		toTest.removeLanguage(l);
	}

	@Test
	public void testRemoveLanguageNativeTongue() {
		final Language l = createLanguageMock(true, true);
		when(l.isNativeTongue()).thenReturn(true);
		when(l.getLevel()).thenReturn(5).thenReturn(4).thenReturn(3).thenReturn(2).thenReturn(1);
		when(l.isAllowedToDecrease()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		when(mockedAventurian.hasSkill(l)).thenReturn(true);
		toTest.removeLanguage(l);

		verify(mockedAventurian, times(1)).refund(anyInt());
		verify(mockedAventurian).remove(l);
		verify(mockedAventurian, times(4)).decreaseSkill(l);
		verify(l).setNativeTongue(false);
	}

	@Test
	public void testRemoveLanguage() {
		final Language l = createLanguageMock(true, true);
		when(mockedAventurian.hasSkill(l)).thenReturn(true);

		toTest.removeLanguage(l);

		verify(mockedAventurian).remove(l);
		verify(mockedAventurian).refund(anyInt());
	}

	@Test
	public void testDecreaseLanguage() {
		final Language l = createLanguageMock(true, true);
		when(mockedAventurian.hasSkill(l)).thenReturn(true);
		when(l.isAllowedToDecrease()).thenReturn(true).thenReturn(true).thenReturn(false);

		toTest.decreaseLanguage(l);

		verify(mockedAventurian).decreaseSkill(l);
		verify(mockedAventurian).refund(anyInt());
		verify(mockedAventurian, never()).remove(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testDecreaseLanguageNotOwned() {
		final Language l = createLanguageMock(true, true);
		when(mockedAventurian.hasSkill(l)).thenReturn(false);
		toTest.decreaseLanguage(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testDecreaseLanguageNotDecreasable() {
		final Language l = createLanguageMock(true, true);
		when(mockedAventurian.hasSkill(l)).thenReturn(true);

		toTest.decreaseLanguage(l);
	}

	@Test
	public void testRemoveIncreasedLanguage() {

		final Language l = createLanguageMock(true, true);
		when(mockedAventurian.hasSkill(l)).thenReturn(true);
		when(l.isAllowedToDecrease()).thenReturn(true).thenReturn(true).thenReturn(false);

		toTest.removeLanguage(l);
		verify(mockedAventurian).decreaseSkill(l);
		verify(mockedAventurian).remove(l);
		verify(mockedAventurian, times(2)).refund(anyInt());
	}

	@Test
	public void testIncreaseLanguageAllConditionsMet() {
		final Language l = createLanguageMock(true, true);
		when(mockedAventurian.hasSkill(l)).thenReturn(true);

		toTest.increaseLanguage(l);
		final InOrder correctOrder = inOrder(mockedAventurian, l);
		correctOrder.verify(mockedAventurian).pay(anyInt());
		correctOrder.verify(mockedAventurian).increaseSkill(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseLanguageNotAllowed() {
		final Language l = createLanguageMock(false, true);
		when(mockedAventurian.hasSkill(l)).thenReturn(true);

		toTest.increaseLanguage(l);

	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseLanguageNotIncreasable() {
		final Language l = createLanguageMock(true, false);
		when(mockedAventurian.hasSkill(l)).thenReturn(true);

		toTest.increaseLanguage(l);
	}


	@Test(expected = IllegalStateException.class)
	public void testIncreaseLanguageDoesNotHaveSkill() {
		final Language l = createLanguageMock(true, true);
		when(mockedAventurian.hasSkill(l)).thenReturn(false);

		toTest.increaseLanguage(l);

	}

	private Language createLanguageMock(boolean isAllowed, boolean isIncreasable) {
		final Language l = mock(Language.class);
		when(l.getName()).thenReturn("testLanguage");
		when(l.isAllowedToHave(mockedAventurian)).thenReturn(isAllowed);
		when(l.isAllowedToAdd(mockedAventurian)).thenReturn(isAllowed);
		when(l.getLevel()).thenReturn(5);
		when(l.getTotalCosts()).thenReturn(750);
		when(l.isAllowedToIncrease(mockedAventurian)).thenReturn(isIncreasable);
		when(l.isAllowedToDecrease()).thenReturn(false);

		return l;
	}

}
