package aventurian;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Observer;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MiscelleanousAventurianManagerTest extends BaseTest {
	private MiscelleanousAventurianManager toTest;
	@Mock
	Observer mockedObserver;

	@Before
	public void setUp() throws Exception {
		toTest = new MiscelleanousAventurianManager(Optional.of(mockedAventurian), mockedDatabase);
	}

	@Test
	public void testSetName() {
		toTest.setName("testName");
		verify(mockedAventurian).setName("testName");
	}

	@Test
	public void testChangeAventurian() {
		testRegisterObserver();

		final Aventurian anotherMockedAventurian = mock(Aventurian.class);
		toTest.changeAventurian(Optional.of(anotherMockedAventurian));
		verify(anotherMockedAventurian).addObserver(mockedObserver);

	}

	@Test
	public void testRegisterObserver() {
		toTest.registerObserver(mockedObserver);
		verify(mockedAventurian).addObserver(mockedObserver);
	}

}
