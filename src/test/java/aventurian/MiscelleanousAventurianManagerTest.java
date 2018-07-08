package aventurian;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Observer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skills.IncreasableSkill;
import skills.Skill;

@RunWith(MockitoJUnitRunner.class)
public class MiscelleanousAventurianManagerTest extends BaseTest {
	private MiscelleanousAventurianManager toTest;
	@Mock
	Observer mockedObserver;

	@Before
	public void setUp() throws Exception {
		toTest = new MiscelleanousAventurianManager(mockedFacade, mockedDatabase, mockedLogger);
		toTest.changeAventurian(mockedAventurian);
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
		toTest.changeAventurian(anotherMockedAventurian);
		verify(anotherMockedAventurian).addObserver(mockedObserver);

	}

	@Test
	public void testRegisterObserver() {
		toTest.registerObserver(mockedObserver);
		verify(mockedAventurian).addObserver(mockedObserver);
	}

	@Test
	public void testGetDependingSkillForDecrease() {
		final IncreasableSkill toDecrease = mock(IncreasableSkill.class);
		toTest.getDependingSkillsForDecrease(toDecrease);
		verify(mockedAventurian).getDependingSkillsForDecrease(toDecrease);
	}

	@Test
	public void testGetDependingSkillForIncrease() {
		final IncreasableSkill toIncrease = mock(IncreasableSkill.class);
		toTest.getDependingSkillsForIncrease(toIncrease);
		verify(mockedAventurian).getDependingSkillsForIncrease(toIncrease);
	}

	@Test
	public void testGetDependingSkillsForAdd() {
		final Skill toAdd = mock(Skill.class);
		toTest.getDependingSkillsForAdd(toAdd);
		verify(mockedAventurian).getDependingSkillsForAdd(toAdd);
	}
	@Test
	public void testGetDependingSkillsForRemove() {
		final Skill toRemove = mock(Skill.class);
		toTest.getDependingSkillsForRemove(toRemove);
		verify(mockedAventurian).getDependingSkillsForRemove(toRemove);
	}
}
