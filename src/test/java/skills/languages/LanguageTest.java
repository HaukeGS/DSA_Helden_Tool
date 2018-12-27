package skills.languages;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import aventurian.Aventurian;
import skills.attributes.primary.Klugheit;

@RunWith(MockitoJUnitRunner.class)
public class LanguageTest {

	@Mock
	private Aventurian av;
	private Language toTest;

	@Before
	public void setUp() throws Exception {
		this.toTest = new Language("testLanguage", "description", 5, 50);
	}

	@Test
	public void testIsDecreasableNativeTongue() {
		toTest.setNativeTongue(true);
		for (int i = 0; i < Language.NATIVE_TONGUE_LEVEL; i++) {
			toTest.increase();
		}
		assertTrue(toTest.isAllowedToDecrease());

		toTest.decrease();

		assertFalse(toTest.isAllowedToDecrease());
	}

	@Test
	public void testIsDecreasableForeignNativeTongue() {
		assertFalse(toTest.isAllowedToDecrease());
		toTest.increase();
		assertTrue(toTest.isAllowedToDecrease());

		toTest.decrease();

		assertFalse(toTest.isAllowedToDecrease());
	}

	@Test
	public void testSetNativeTongue() {
		toTest.setNativeTongue(false);
		assertFalse(toTest.isNativeTongue());

		toTest.setNativeTongue(true);
		assertTrue(toTest.isNativeTongue());
	}

	@Test
	public void testIsAllowedToHave() {
		when(av.getLevelSumOfLanguages()).thenReturn(1);
		when(av.getPrimaryAttribute(Klugheit.NAME)).thenReturn(0);
		assertFalse(toTest.isAllowedToHave(av));

		when(av.getLevelSumOfLanguages()).thenReturn(0);
		when(av.getPrimaryAttribute(Klugheit.NAME)).thenReturn(0);
		assertTrue(toTest.isAllowedToHave(av));
	}

	@Test
	public void testIsAllowedToAdd() {
		when(av.getLevelSumOfLanguages()).thenReturn(1);
		when(av.getPrimaryAttribute(Klugheit.NAME)).thenReturn(0);
		assertFalse(toTest.isAllowedToAdd(av));

		when(av.getLevelSumOfLanguages()).thenReturn(0);
		when(av.getPrimaryAttribute(Klugheit.NAME)).thenReturn(0);
		assertFalse(toTest.isAllowedToAdd(av));
		
		when(av.getLevelSumOfLanguages()).thenReturn(0);
		when(av.getPrimaryAttribute(Klugheit.NAME)).thenReturn(1);
		assertTrue(toTest.isAllowedToAdd(av));
	}
	
	@Test
	public void testIsAllowedToIncrease() {
		when(av.getLevelSumOfLanguages()).thenReturn(1);
		when(av.getPrimaryAttribute(Klugheit.NAME)).thenReturn(0);
		assertFalse(toTest.isAllowedToIncrease(av));

		when(av.getLevelSumOfLanguages()).thenReturn(0);
		when(av.getPrimaryAttribute(Klugheit.NAME)).thenReturn(0);
		assertFalse(toTest.isAllowedToIncrease(av));
		
		when(av.getLevelSumOfLanguages()).thenReturn(0);
		when(av.getPrimaryAttribute(Klugheit.NAME)).thenReturn(1);
		assertTrue(toTest.isAllowedToIncrease(av));
	}

}
