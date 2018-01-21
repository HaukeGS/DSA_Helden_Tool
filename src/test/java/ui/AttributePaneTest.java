package ui;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE;
import aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE;
import javafx.scene.control.Label;

@RunWith(MockitoJUnitRunner.class)
public class AttributePaneTest extends BaseGuiTest {

	@Before
	public void setUp() {
		clickOn("#hyperlinkAttributes");
		verifyThat("#paneAttributes", isVisible());
	}
	@Test
	public void testIncreaseCourage() {
		clickOn("#btnIncreaseCourage");
		verify(mockedAventurianManager).increasePrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE);
	}

	@Test
	public void testDecreaseCourage() {
		clickOn("#btnDecreaseCourage");
		verify(mockedAventurianManager).decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE);
	}
	@Test
	public void testIncreaseIntelligence() {
		clickOn("#btnIncreaseIntelligence");
		verify(mockedAventurianManager).increasePrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE);
	}

	@Test
	public void testDecreaseIntelligence() {
		clickOn("#btnDecreaseIntelligence");
		verify(mockedAventurianManager).decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE);
	}
	@Test
	public void testIncreaseIntuition() {
		clickOn("#btnIncreaseIntuition");
		verify(mockedAventurianManager).increasePrimaryAttribute(PRIMARY_ATTRIBUTE.INTUITION);
	}

	@Test
	public void testDecreaseIntuition() {
		clickOn("#btnDecreaseIntuition");
		verify(mockedAventurianManager).decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.INTUITION);
	}
	@Test
	public void testIncreaseCharisma() {
		clickOn("#btnIncreaseCharisma");
		verify(mockedAventurianManager).increasePrimaryAttribute(PRIMARY_ATTRIBUTE.CHARISMA);
	}

	@Test
	public void testDecreaseCharisma() {
		clickOn("#btnDecreaseCharisma");
		verify(mockedAventurianManager).decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.CHARISMA);
	}
	@Test
	public void testIncreaseDexterity() {
		clickOn("#btnIncreaseDexterity");
		verify(mockedAventurianManager).increasePrimaryAttribute(PRIMARY_ATTRIBUTE.DEXTERITY);
	}

	@Test
	public void testDecreaseDexterity() {
		clickOn("#btnDecreaseDexterity");
		verify(mockedAventurianManager).decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.DEXTERITY);
	}
	@Test
	public void testIncreaseAgility() {
		clickOn("#btnIncreaseAgility");
		verify(mockedAventurianManager).increasePrimaryAttribute(PRIMARY_ATTRIBUTE.AGILITY);
	}

	@Test
	public void testDecreaseAgility() {
		clickOn("#btnDecreaseAgility");
		verify(mockedAventurianManager).decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.AGILITY);
	}
	@Test
	public void testIncreaseConstitution() {
		clickOn("#btnIncreaseConstitution");
		verify(mockedAventurianManager).increasePrimaryAttribute(PRIMARY_ATTRIBUTE.CONSTITUTION);
	}

	@Test
	public void testDecreaseConstitution() {
		clickOn("#btnDecreaseConstitution");
		verify(mockedAventurianManager).decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.CONSTITUTION);
	}
	@Test
	public void testIncreaseStrength() {
		clickOn("#btnIncreaseStrength");
		verify(mockedAventurianManager).increasePrimaryAttribute(PRIMARY_ATTRIBUTE.STRENGTH);
	}

	@Test
	public void testDecreaseStrength() {
		clickOn("#btnDecreaseStrength");
		verify(mockedAventurianManager).decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.STRENGTH);
	}
	
	@Test
	public void testIncreaseHitPoints() {
		clickOn("#btnIncreaseHitPoints");
		verify(mockedAventurianManager).increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS);
	}
	
	@Test
	public void testDecreaseHitPoints() {
		clickOn("#btnDecreaseHitPoints");
		verify(mockedAventurianManager).decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS);
	}
	
	@Test
	public void testIncreaseAstralPoints() {
		clickOn("#btnIncreaseAstralPoints");
		verify(mockedAventurianManager).increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.ASTRALPOINTS);
	}
	
	@Test
	public void testDecreaseAstralPoints() {
		clickOn("#btnDecreaseAstralPoints");
		verify(mockedAventurianManager).decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.ASTRALPOINTS);
	}
	
	@Test
	public void testIncreaseMagicResistance() {
		clickOn("#btnIncreaseMagicResistance");
		verify(mockedAventurianManager).increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.MAGICRESISTANCE);
	}
	
	@Test
	public void testDecreaseMagicResistance() {
		clickOn("#btnDecreaseMagicResistance");
		verify(mockedAventurianManager).decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.MAGICRESISTANCE);
	}

	@Test
	public void testUpdate() {
		final Label lblCourage = find("#labelCourage");
		assertEquals(String.valueOf(10), lblCourage.getText());
		final Label lblIntelligence = find("#labelIntelligence");
		assertEquals(String.valueOf(11), lblIntelligence.getText());
		final Label lblIntuition = find("#labelIntuition");
		assertEquals(String.valueOf(12), lblIntuition.getText());
		final Label lblCharisma = find("#labelCharisma");
		assertEquals(String.valueOf(13), lblCharisma.getText());
		final Label lblDexterity = find("#labelDexterity");
		assertEquals(String.valueOf(14), lblDexterity.getText());
		final Label lblAgility = find("#labelAgility");
		assertEquals(String.valueOf(9), lblAgility.getText());
		final Label lblConstitution = find("#labelConstitution");
		assertEquals(String.valueOf(8), lblConstitution.getText());
		final Label lblStrength = find("#labelStrength");
		assertEquals(String.valueOf(7), lblStrength.getText());
	}

	@Override
	void setUpMocks() {
		when(mockedAventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE)).thenReturn(10);
		when(mockedAventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE)).thenReturn(11);
		when(mockedAventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTUITION)).thenReturn(12);
		when(mockedAventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.CHARISMA)).thenReturn(13);
		when(mockedAventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.DEXTERITY)).thenReturn(14);
		when(mockedAventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.AGILITY)).thenReturn(9);
		when(mockedAventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.CONSTITUTION)).thenReturn(8);
		when(mockedAventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.STRENGTH)).thenReturn(7);
		
		when(mockedAventurian.isPrimaryAttributeIncreasable(any(PRIMARY_ATTRIBUTE.class))).thenReturn(true);
		when(mockedAventurian.isPrimaryAttributeDecreasable(any(PRIMARY_ATTRIBUTE.class))).thenReturn(true);
		when(mockedAventurian.isPrimaryAttributesLowerThanThreshhold()).thenReturn(true);
		
		when(mockedAventurian.isMage()).thenReturn(true);
		when(mockedAventurian.isSecondaryAttributeIncreasableByBuy(any(SECONDARY_ATTRIBUTE.class))).thenReturn(true);
		when(mockedAventurian.isSecondaryAttributeDecreasableByBuy(any(SECONDARY_ATTRIBUTE.class))).thenReturn(true);
	}

}
