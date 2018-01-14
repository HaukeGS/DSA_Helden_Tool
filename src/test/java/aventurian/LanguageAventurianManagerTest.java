package aventurian;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skills.Language;

@RunWith(MockitoJUnitRunner.class)
public class LanguageAventurianManagerTest {

	LanguageAventurianManager toTest;

	@Mock
	Aventurian a;

	@Before
	public void setUp() throws Exception {
		when(a.canPay(anyInt())).thenReturn(true);
		toTest = new LanguageAventurianManager(a);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddLanguageAsNativeTongueAlreadyNativeTongue() {
		final Language l = createLanguageMock(true, true);
		when(l.isNativeTongue()).thenReturn(true);
		toTest.addLanguageAsNativeTongue(l);
	}

	@Test
	public void testLanguageAllConditionsMet() {
		final Language l = createLanguageMock(true, true);
		when(a.canPay(anyInt())).thenReturn(true);
		toTest.addLanguage(l);

		verify(a).pay(anyInt());
		verify(a).add(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddLanguageAlreadyHasSkill() {
		final Language l = createLanguageMock(true, true);
		when(a.hasSkill(l)).thenReturn(true);
		toTest.addLanguage(l);
	}

	@Test
	public void testAddLanguageTooExpensive() {
		final Language l = createLanguageMock(true, true);
		when(a.canPay(anyInt())).thenReturn(false);

		toTest.addLanguage(l);

		verify(a, never()).add(l);
		verify(a, never()).pay(anyInt());
		verify(l, never()).gain(a);
	}

	@Test
	public void testAddLanguageNotAllowed() {
		final Language l = createLanguageMock(false, true);
		when(a.canPay(anyInt())).thenReturn(true);

		toTest.addLanguage(l);

		verify(a, never()).add(l);
		verify(a, never()).pay(anyInt());
		verify(l, never()).gain(a);
	}

	@Test
	public void testAddLanguageAsNativeTongue() {
		final Language l = createLanguageMock(true, true);
		when(l.getLevel()).thenReturn(1).thenReturn(2).thenReturn(3).thenReturn(4).thenReturn(5);
		toTest.addLanguageAsNativeTongue(l);

		verify(a, never()).pay(anyInt());
		verify(l, times(3)).increase();
		verify(l).setNativeTongue(true);
		verify(a).add(l);
	}

	@Test
	public void testAddLanguageAsNativeTongueNotAllowed() {
		final Language l = createLanguageMock(false, true);
		toTest.addLanguageAsNativeTongue(l);

		verify(a, never()).add(l);
		verify(l, never()).increase();
		verify(l, never()).setNativeTongue(true);
	}

	@Test
	public void testAddLanguageAsNativeTongueNotIncreasable() {
		final Language l = createLanguageMock(true, true);
		when(l.getLevel()).thenReturn(1).thenReturn(2).thenReturn(3);
		when(l.isIncreasable()).thenReturn(true).thenReturn(true).thenReturn(false);
		toTest.addLanguageAsNativeTongue(l);

		verify(a).add(l);
		verify(l, times(2)).increase();
		verify(l).setNativeTongue(true);
	}

	@Test(expected = IllegalStateException.class)
	public void testRemoveLanguageNotOwned() {
		final Language l = createLanguageMock(true, true);
		when(a.hasSkill(l)).thenReturn(false);

		toTest.removeLanguage(l);
	}

	@Test
	public void testRemoveLanguageNativeTongue() {
		final Language l = createLanguageMock(true, true);
		when(l.isNativeTongue()).thenReturn(true);
		when(l.getLevel()).thenReturn(5).thenReturn(4).thenReturn(3).thenReturn(2).thenReturn(1);
		when(l.isDecreasable()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		when(a.hasSkill(l)).thenReturn(true);
		toTest.removeLanguage(l);

		verify(a, times(1)).refund(anyInt());
		verify(a).remove(l);
		verify(l, times(4)).decrease();
		verify(l).setNativeTongue(false);
	}

	@Test
	public void testRemoveLanguage() {

		final Language l = createLanguageMock(true, true);
		when(a.hasSkill(l)).thenReturn(true);

		toTest.removeLanguage(l);

		verify(a).remove(l);
		verify(a).refund(anyInt());
	}

	@Test
	public void testDecreaseLanguage() {
		final Language l = createLanguageMock(true, true);
		when(a.hasSkill(l)).thenReturn(true);
		when(l.isDecreasable()).thenReturn(true).thenReturn(true).thenReturn(false);

		toTest.decreaseLanguage(l);

		verify(l).decrease();
		verify(a).refund(anyInt());
		verify(a, never()).remove(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testDecreaseLanguageNotOwned() {
		final Language l = createLanguageMock(true, true);
		when(l.isDecreasable()).thenReturn(true);
		when(a.hasSkill(l)).thenReturn(false);
		toTest.decreaseLanguage(l);
	}

	@Test(expected = IllegalStateException.class)
	public void testDecreaseLanguageNotDecreasable() {
		final Language l = createLanguageMock(true, true);

		toTest.decreaseLanguage(l);
	}

	@Test
	public void testRemoveIncreasedLanguage() {

		final Language l = createLanguageMock(true, true);
		when(a.hasSkill(l)).thenReturn(true);
		when(l.isDecreasable()).thenReturn(true).thenReturn(true).thenReturn(false);

		toTest.removeLanguage(l);
		verify(l).decrease();
		verify(a).remove(l);
		verify(a, times(2)).refund(anyInt());
	}

	@Test
	public void testIncreaseLanguageAllConditionsMet() {
		final Language l = createLanguageMock(true, true);
		when(a.hasSkill(l)).thenReturn(true);
		when(a.canPay(anyInt())).thenReturn(true);

		toTest.increaseLanguage(l);

		verify(a).pay(anyInt());
		verify(l).increase();
	}

	@Test
	public void testIncreaseLanguageNotAllowed() {
		final Language l = createLanguageMock(false, true);
		when(a.hasSkill(l)).thenReturn(true);
		when(a.canPay(anyInt())).thenReturn(true);

		toTest.increaseLanguage(l);

		verify(a, never()).pay(anyInt());
		verify(l, never()).increase();
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseLanguageNotIncreasable() {
		final Language l = createLanguageMock(true, false);

		toTest.increaseLanguage(l);
	}

	@Test
	public void testIncreaseLanguageTooExpensive() {
		final Language l = createLanguageMock(true, true);
		when(a.hasSkill(l)).thenReturn(true);
		when(a.canPay(anyInt())).thenReturn(false);

		toTest.increaseLanguage(l);

		verify(a, never()).pay(anyInt());
		verify(l, never()).increase();
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseLanguageDoesNotHaveSkill() {
		final Language l = createLanguageMock(true, true);
		when(a.hasSkill(l)).thenReturn(false);

		toTest.increaseLanguage(l);

	}

	private Language createLanguageMock(boolean isAllowed, boolean isIncreasable) {
		final Language l = mock(Language.class);
		when(l.getName()).thenReturn("testLanguage");
		when(l.isAllowed(a)).thenReturn(isAllowed);
		when(l.getLevel()).thenReturn(5);
		when(l.getLearningCost()).thenReturn(50);
		when(l.isIncreasable()).thenReturn(isIncreasable);
		when(l.isDecreasable()).thenReturn(false);

		return l;
	}

}
