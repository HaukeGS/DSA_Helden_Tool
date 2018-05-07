package skills.languages;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class LanguageTest {
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
		assertTrue(toTest.isDecreasable());

		toTest.decrease();

		assertFalse(toTest.isDecreasable());
	}

	@Test
	public void testSetNativeTongue() {
		toTest.setNativeTongue(false);
		assertFalse(toTest.isNativeTongue());

		toTest.setNativeTongue(true);
		assertTrue(toTest.isNativeTongue());
	}

}
