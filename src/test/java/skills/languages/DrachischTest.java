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
public class DrachischTest {
	private Drachisch toTest;
	@Mock
	Aventurian av;

	@Before
	public void setUp() throws Exception {
		toTest = new Drachisch();
	}

	@Test
	public void testIsAllowedToHave() {
		when(av.isMage()).thenReturn(false);
		assertFalse(toTest.isAllowedToHave(av));
		when(av.isMage()).thenReturn(true);
		assertTrue(toTest.isAllowedToHave(av));
	}

	@Test
	public void testIsAllowedToAdd() {
		when(av.getLevelSumOfLanguages()).thenReturn(0);
		when(av.getPrimaryAttribute(Klugheit.NAME)).thenReturn(10);
		when(av.isMage()).thenReturn(false);
		assertFalse(toTest.isAllowedToAdd(av));
		
		when(av.getLevelSumOfLanguages()).thenReturn(10);
		when(av.getPrimaryAttribute(Klugheit.NAME)).thenReturn(10);
		when(av.isMage()).thenReturn(false);
		assertFalse(toTest.isAllowedToAdd(av));
		
		when(av.getLevelSumOfLanguages()).thenReturn(10);
		when(av.getPrimaryAttribute(Klugheit.NAME)).thenReturn(10);
		when(av.isMage()).thenReturn(true);
		assertFalse(toTest.isAllowedToAdd(av));

		when(av.getLevelSumOfLanguages()).thenReturn(0);
		when(av.getPrimaryAttribute(Klugheit.NAME)).thenReturn(10);
		when(av.isMage()).thenReturn(true);
		assertTrue(toTest.isAllowedToAdd(av));
	}

}
