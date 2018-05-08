package aventurian;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE;
import skills.languages.Language;

@RunWith(MockitoJUnitRunner.class)
public class LanguageAventurianManagerTest extends BaseTest {

	LanguageAventurianManager toTest;

	@Mock
	Aventurian aventurian;

	@Before
	public void setUp() throws Exception {
		when(aventurian.canPay(anyInt())).thenReturn(true);
		toTest = new LanguageAventurianManager(Optional.of(aventurian), mockedDatabase);
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
		when(aventurian.hasSkill(l)).thenReturn(true);
		toTest.addLanguageAsNativeTongue(l);
	}

	@Test
	public void testAddLanguageAllConditionsMet() {
		final Language l = createLanguageMock(true, true);
		when(aventurian.canPay(anyInt())).thenReturn(true);
		when(aventurian.hasSkill(l)).thenReturn(false);
		when(aventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE)).thenReturn(8);
		when(aventurian.getLevelSumOfLanguages()).thenReturn(0);
		toTest.addLanguage(l);

		final InOrder correctOrder = inOrder(aventurian);
		correctOrder.verify(aventurian).add(l);
		correctOrder.verify(aventurian).pay(anyInt());
	}

	@Test(expected = IllegalStateException.class)
	public void testAddLanguageAlreadyHasSkill() {
		final Language l = createLanguageMock(true, true);
		when(aventurian.hasSkill(l)).thenReturn(true);
		toTest.addLanguage(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddLanguageTooExpensive() {
		final Language l = createLanguageMock(true, true);
		when(aventurian.getLevelSumOfLanguages()).thenReturn(0);
		when(aventurian.canPay(anyInt())).thenReturn(false);
		when(aventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE)).thenReturn(8);

		toTest.addLanguage(l);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testAddLanguageExceedsMaxSum() {
		final Language l = createLanguageMock(true, true);
		when(aventurian.getLevelSumOfLanguages()).thenReturn(8);
		when(aventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE)).thenReturn(8);
		
		toTest.addLanguage(l);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testIncreaseLanguageExceedsMaxSum() {
		final Language l = createLanguageMock(true, true);
		when(aventurian.getLevelSumOfLanguages()).thenReturn(8);
		when(aventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE)).thenReturn(8);
		when(aventurian.hasSkill(l)).thenReturn(true);
		when(aventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE)).thenReturn(8);
		when(aventurian.getLevelSumOfLanguages()).thenReturn(8);
		
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

		verify(aventurian, never()).pay(anyInt());
		verify(l, times(3)).increase();
		final InOrder correctOrder = inOrder(aventurian, l);
		correctOrder.verify(l).setNativeTongue(true);
		correctOrder.verify(aventurian).add(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddLanguageAsNativeTongueHasAlreadyNativeTongue() {
		final Language l = createLanguageMock(true, true);
		when(aventurian.hasSkill(l)).thenReturn(false);
		when(l.isAllowedToHave(aventurian)).thenReturn(true);
		when(l.isNativeTongue()).thenReturn(false);
		when(aventurian.hasNativeTongue()).thenReturn(true);

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
		when(l.isAllowedToIncrease(aventurian)).thenReturn(true).thenReturn(true).thenReturn(false);
		toTest.addLanguageAsNativeTongue(l);

		verify(l, times(2)).increase();
		final InOrder correctOrder = inOrder(aventurian, l);
		correctOrder.verify(l).setNativeTongue(true);
		correctOrder.verify(aventurian).add(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testRemoveLanguageNotOwned() {
		final Language l = createLanguageMock(true, true);
		when(aventurian.hasSkill(l)).thenReturn(false);

		toTest.removeLanguage(l);
	}

	@Test
	public void testRemoveLanguageNativeTongue() {
		final Language l = createLanguageMock(true, true);
		when(l.isNativeTongue()).thenReturn(true);
		when(l.getLevel()).thenReturn(5).thenReturn(4).thenReturn(3).thenReturn(2).thenReturn(1);
		when(l.isAllowedToDecrease()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		when(aventurian.hasSkill(l)).thenReturn(true);
		toTest.removeLanguage(l);

		verify(aventurian, times(1)).refund(anyInt());
		verify(aventurian).remove(l);
		verify(aventurian, times(4)).decreaseIncreasableSkill(l);
		verify(l).setNativeTongue(false);
	}

	@Test
	public void testRemoveLanguage() {

		final Language l = createLanguageMock(true, true);
		when(aventurian.hasSkill(l)).thenReturn(true);

		toTest.removeLanguage(l);

		verify(aventurian).remove(l);
		verify(aventurian).refund(anyInt());
	}

	@Test
	public void testDecreaseLanguage() {
		final Language l = createLanguageMock(true, true);
		when(aventurian.hasSkill(l)).thenReturn(true);
		when(l.isAllowedToDecrease()).thenReturn(true).thenReturn(true).thenReturn(false);

		toTest.decreaseLanguage(l);

		verify(aventurian).decreaseIncreasableSkill(l);
		verify(aventurian).refund(anyInt());
		verify(aventurian, never()).remove(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testDecreaseLanguageNotOwned() {
		final Language l = createLanguageMock(true, true);
		when(aventurian.hasSkill(l)).thenReturn(false);
		toTest.decreaseLanguage(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testDecreaseLanguageNotDecreasable() {
		final Language l = createLanguageMock(true, true);
		when(aventurian.hasSkill(l)).thenReturn(true);

		toTest.decreaseLanguage(l);
	}

	@Test
	public void testRemoveIncreasedLanguage() {

		final Language l = createLanguageMock(true, true);
		when(aventurian.hasSkill(l)).thenReturn(true);
		when(l.isAllowedToDecrease()).thenReturn(true).thenReturn(true).thenReturn(false);

		toTest.removeLanguage(l);
		verify(aventurian).decreaseIncreasableSkill(l);
		verify(aventurian).remove(l);
		verify(aventurian, times(2)).refund(anyInt());
	}

	@Test
	public void testIncreaseLanguageAllConditionsMet() {
		final Language l = createLanguageMock(true, true);
		when(aventurian.hasSkill(l)).thenReturn(true);
		when(aventurian.canPay(anyInt())).thenReturn(true);
		when(aventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE)).thenReturn(8);
		when(aventurian.getLevelSumOfLanguages()).thenReturn(0);

		toTest.increaseLanguage(l);
		final InOrder correctOrder = inOrder(aventurian, l);
		correctOrder.verify(aventurian).pay(anyInt());
		correctOrder.verify(aventurian).increaseIncreasableSkill(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseLanguageNotAllowed() {
		final Language l = createLanguageMock(false, true);
		when(aventurian.hasSkill(l)).thenReturn(true);

		toTest.increaseLanguage(l);

	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseLanguageNotIncreasable() {
		final Language l = createLanguageMock(true, false);
		when(aventurian.hasSkill(l)).thenReturn(true);

		toTest.increaseLanguage(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseLanguageTooExpensive() {
		final Language l = createLanguageMock(true, true);
		when(aventurian.hasSkill(l)).thenReturn(true);
		when(aventurian.canPay(anyInt())).thenReturn(false);
		when(aventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE)).thenReturn(8);
		when(aventurian.getLevelSumOfLanguages()).thenReturn(0);

		toTest.increaseLanguage(l);

	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseLanguageDoesNotHaveSkill() {
		final Language l = createLanguageMock(true, true);
		when(aventurian.hasSkill(l)).thenReturn(false);

		toTest.increaseLanguage(l);

	}

	private Language createLanguageMock(boolean isAllowed, boolean isIncreasable) {
		final Language l = mock(Language.class);
		when(l.getName()).thenReturn("testLanguage");
		when(l.isAllowedToHave(aventurian)).thenReturn(isAllowed);
		when(l.getLevel()).thenReturn(5);
		when(l.getLearningCosts()).thenReturn(50);
		when(l.isAllowedToIncrease(aventurian)).thenReturn(isIncreasable);
		when(l.isAllowedToDecrease()).thenReturn(false);

		return l;
	}

}
