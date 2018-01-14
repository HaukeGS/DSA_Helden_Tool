package aventurian;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SecondaryAttributeHelperTest {

	private SecondaryAttributeHelper toTest;

	@Before
	public void setUp() throws Exception {
		this.toTest = new SecondaryAttributeHelper();
	}

	@Test
	public void setBasis() throws Exception {
		assertEquals(0, toTest.getActual());
		toTest.setBasis(11);
		assertEquals(11, toTest.getActual());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setBasisNegative() throws Exception {
		assertEquals(0, toTest.getActual());
		toTest.setBasis(-11);
	}

	@Test
	public void increaseMod() throws Exception {
		assertEquals(0, toTest.getActual());
		toTest.increaseMod(11);
		assertEquals(11, toTest.getActual());
	}

	@Test(expected = IllegalArgumentException.class)
	public void increaseModNegative() throws Exception {
		assertEquals(0, toTest.getActual());
		toTest.increaseMod(-11);
	}

	@Test
	public void decreaseMod() throws Exception {
		assertEquals(0, toTest.getActual());
		toTest.decreaseMod(11);
		assertEquals(-11, toTest.getActual());
	}

	@Test(expected = IllegalArgumentException.class)
	public void decreaseModNegative() throws Exception {
		assertEquals(0, toTest.getActual());
		toTest.decreaseMod(-11);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setMaxNotValid() throws Exception {
		toTest.setMax(-4);
	}

	@Test
	public void increaseModBuy() {
		int value = toTest.getActual();
		toTest.increaseModBuy();
		assertEquals(value + 1, toTest.getActual());
	}
	
	@Test
	public void decreaseModBuy() {
		int value = toTest.getActual();
		toTest.decreaseModBuy();
		assertEquals(value - 1, toTest.getActual());
	}
	
	@Test
	public void testIsIncreasableByBuy() {
		assertFalse(toTest.isIncreasableByBuy());
		toTest.setMax(1);
		assertTrue(toTest.isIncreasableByBuy());
		toTest.increaseModBuy();
		assertFalse(toTest.isIncreasableByBuy());
	}
	
	@Test
	public void testIsDecreasableByBuy() {
		assertFalse(toTest.isDecreasableByBuy());
		toTest.increaseModBuy();
		assertTrue(toTest.isDecreasableByBuy());
	}
	
	@Test
	public void testIsDecreasableByBuyNegativeBuyMod() {
		assertFalse(toTest.isDecreasableByBuy());
		toTest.increaseMod(1);
		assertTrue(toTest.isDecreasableByBuy());		
	}

	@Test
	public void getActual() throws Exception {
		assertEquals(0, toTest.getActual());
		toTest.setBasis(2);
		toTest.increaseMod(4);
		assertEquals(6, toTest.getActual());
	}

}