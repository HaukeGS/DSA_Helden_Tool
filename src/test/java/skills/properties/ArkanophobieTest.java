package skills.properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import aventurian.Aventurian;

class ArkanophobieTest {
	Arkanophobie toTest;
	@Mock
	Aventurian a;

	@BeforeEach
	void setUp() throws Exception {
		toTest = new Arkanophobie();
	}

	@Test
	void testIsAllowedToHave() {
		when(a.isMage()).thenReturn(false);
		assertEquals(false, toTest.isAllowedToHave(a));
		when(a.isMage()).thenReturn(true);
		assertEquals(true, toTest.isAllowedToHave(a));
	}
}
