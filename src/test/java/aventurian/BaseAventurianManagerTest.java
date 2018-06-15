package aventurian;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import database.Database;
import logging.Logger;
import skills.IncreasableSkill;
import skills.Skill;

@RunWith(MockitoJUnitRunner.class)
public class BaseAventurianManagerTest {

	private BaseAventurianManager toTest;

	@Mock
	private AventurianManagerFacade avm;
	@Mock
	Database db;
	@Mock
	private Logger log;
	@Mock
	Aventurian av;

	@Before
	public void setUp() throws Exception {
		toTest = new BaseAventurianManager(avm, db, log);
		toTest.changeAventurian(av);
	}

	@Test
	public void testAdd() {
		final Skill s = mock(Skill.class);
		toTest.add(s);
		verify(s).atGain(avm);
		verify(av).add(s);

	}

	@Test
	public void testRemove() {
		final Skill s = mock(Skill.class);
		toTest.remove(s);
		verify(s).atLose(avm);
		verify(av).remove(s);
	}

	@Test
	public void testIncrease() {
		final IncreasableSkill s = mock(IncreasableSkill.class);
		toTest.increase(s);
		verify(av).increaseSkill(s);
	}

	@Test
	public void testDecrease() {
		final IncreasableSkill s = mock(IncreasableSkill.class);
		toTest.decrease(s);
		verify(av).decreaseSkill(s);
	}
	
	@Test
	public void testPay() {
		toTest.pay(anyInt());
		verify(av).pay(anyInt());
	}
	
	@Test
	public void testRefund() {
		toTest.refund(anyInt());
		verify(av).refund(anyInt());
	}

}
