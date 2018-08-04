package skills.properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import aventurian.Aventurian;
import aventurian.AventurianManagerFacade;
import database.Database;
import skills.attributes.secondary.SecondaryAttribute;
import skills.attributes.secondary.Wundschwelle;

@RunWith(MockitoJUnitRunner.class)
public class EisernTest {
	private Eisern toTest;
	@Mock
	Aventurian av;
	@Mock
	AventurianManagerFacade avm;
	@Mock
	SecondaryAttribute s;
	@Mock
	Database db;

	@Before
	public void setUp() throws Exception {
		toTest = new Eisern();
		when(db.getSecondaryAttribute(Wundschwelle.NAME)).thenReturn(s);
		when(avm.getDatabase()).thenReturn(db);
	}

	@Test
	public void testIsAllowedToHave() {
		when(av.hasSkill(Glasknochen.NAME)).thenReturn(false);
		assertTrue(toTest.isAllowedToHave(av));
		when(av.hasSkill(Glasknochen.NAME)).thenReturn(true);
		assertFalse(toTest.isAllowedToHave(av));
	}

	@Test
	public void testAtGain() {
		toTest.atGain(avm);
		verify(avm).increaseSecondaryAttributeMod(s, 1);
	}

	@Test
	public void testAtLose() {
		toTest.atLose(avm);
		verify(avm).decreaseSecondaryAttributeMod(s, 1);
	}

}
