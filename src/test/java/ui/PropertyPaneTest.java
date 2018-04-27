package ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.matcher.base.NodeMatchers;

import aventurian.Aventurian;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import skills.properties.BadProperty;
import skills.properties.Property;
import testsuites.categories.CannotRunHeadless;

@RunWith(MockitoJUnitRunner.class)
public class PropertyPaneTest extends BaseGuiTest {
	private static final Predicate<Aventurian> NOREQUIREMENT = (Aventurian a) -> true;
	private static final Consumer<Aventurian> EMPTY = (Aventurian a) -> {
	};
	private static final String ID_BTN_ASSIGN_ADVANTAGE = "#btnAssignAdvantage";
	private static final String PROPERTY_NAME_ROYAL = "Adlig";
	private static final String PROPERTY_NAME_DISADVANTAGE = "Disadvantage";
	private static final String PROPERTY_NAME_PRETTY = "Pretty";
	private static final String ID_BTN_UN_ASSIGN_ADVANTAGE = "#btnUnassignAdvantage";
	private static final String ID_LV_UN_ASSIGNED_ADVANTAGES = "#lvUnassignedAdvantages";
	private static final String ID_LV_ASSIGNED_ADVANTAGES = "#lvAssignedAdvantages";
	private static final String ID_LV_UN_ASSIGNED_DISADVANTAGES = "#lvUnassignedDisadvantages";
	private static final String ID_LV_ASSIGNED_DISADVANTAGES = "#lvAssignedDisadvantages";
	private static final String ID_BTN_ASSIGN_DISADVANTAGE = "#btnAssignDisadvantage"; 
	private static final String ID_BTN_UN_ASSIGN_DISADVANTAGE = "#btnUnassignDisadvantage";
	private static final String PROPERTY_NAME_BADPROPERTY = "BadProperty";
	private Property ad2;
	private Property ad1;
	private Property ad3;
	private BadProperty ad4; 

	@Before
	public void setUp() throws Exception {
		clickOn("#hyperlinkProperties");
		verifyThat("#paneProperties", isVisible());
	}

	@Test
	public void testAssignAdvantage() {
		verifyThat(PROPERTY_NAME_ROYAL, NodeMatchers.isNotNull());
		clickOn(PROPERTY_NAME_ROYAL).clickOn(ID_BTN_ASSIGN_ADVANTAGE);
		verify(mockedAventurianManager).addProperty(any(Property.class));
	}

	@Test
	public void testAssignAdvantageViaDoubleClick() {
		verifyThat(PROPERTY_NAME_ROYAL, NodeMatchers.isNotNull());
		doubleClickOn(PROPERTY_NAME_ROYAL, MouseButton.PRIMARY);
		verify(mockedAventurianManager).addProperty(any(Property.class));
	}

	@Test
	public void testUnAssignAdvantage() {
		when(mockedAventurian.getAdvantages()).thenReturn(Arrays.asList(createProperty(PROPERTY_NAME_PRETTY, 50)));
		updateGui();

		verifyThat(PROPERTY_NAME_PRETTY, NodeMatchers.isNotNull());
		clickOn(PROPERTY_NAME_PRETTY).clickOn(ID_BTN_UN_ASSIGN_ADVANTAGE);
		verify(mockedAventurianManager).removeProperty(any(Property.class));
	}

	@Test
	public void testUnAssignAdvantageViaDoubleClick() {
		when(mockedAventurian.getAdvantages()).thenReturn(Arrays.asList(createProperty(PROPERTY_NAME_PRETTY, 50)));
		updateGui();

		verifyThat(PROPERTY_NAME_PRETTY, NodeMatchers.isNotNull());
		doubleClickOn(PROPERTY_NAME_PRETTY, MouseButton.PRIMARY);
		verify(mockedAventurianManager).removeProperty(any(Property.class));
	}

	@Test
	public void testAssignAdvantageButtonIsDisabled() {
		final ListView<Property> allAdvantages = find(ID_LV_UN_ASSIGNED_ADVANTAGES);
		assertTrue(allAdvantages.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_ASSIGN_ADVANTAGE, (Button b) -> b.isDisable());
	}

	@Test
	public void testUnAssignAdvantageButtonIsDisabled() {
		final ListView<Property> assignedAdvantages = find(ID_LV_ASSIGNED_ADVANTAGES);
		assertTrue(assignedAdvantages.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_UN_ASSIGN_ADVANTAGE, (Button b) -> b.isDisable());
	}

	@Test
	public void testUnAssignAdvantageButtonIsEnabled() {
		when(mockedAventurian.getAdvantages()).thenReturn(Arrays.asList(createProperty(PROPERTY_NAME_PRETTY, 50)));
		updateGui();

		verifyThat(PROPERTY_NAME_PRETTY, NodeMatchers.isNotNull());
		clickOn(PROPERTY_NAME_PRETTY);
		final ListView<Property> assignedProperties = find(ID_LV_ASSIGNED_ADVANTAGES);
		assertFalse(assignedProperties.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_UN_ASSIGN_ADVANTAGE, (Button b) -> !b.isDisable());
	}
	
	@Test
	public void testAssignAdvantageButtonIsEnabled() {
		verifyThat(PROPERTY_NAME_ROYAL, NodeMatchers.isNotNull());
		clickOn(PROPERTY_NAME_ROYAL);
		final ListView<Property> allAdvantages = find(ID_LV_UN_ASSIGNED_ADVANTAGES);
		assertFalse(allAdvantages.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_ASSIGN_ADVANTAGE, (Button b) -> !b.isDisable());
	}
	@Category(CannotRunHeadless.class)
	@Test
	public void testToggleAssignAdvantagesButtonEnabledDisabled() {
		testAssignAdvantageButtonIsDisabled();
		testAssignAdvantageButtonIsEnabled();
		press(KeyCode.CONTROL).clickOn(PROPERTY_NAME_ROYAL).release(KeyCode.CONTROL);
		verifyThat(ID_BTN_ASSIGN_ADVANTAGE, (Button b) -> b.isDisable());
	}

	@Category(CannotRunHeadless.class)
	@Test
	public void testToggleUnAssignAdvantagesButtonEnabledDisabled() {
		testUnAssignAdvantageButtonIsDisabled();
		testUnAssignAdvantageButtonIsEnabled();
		press(KeyCode.CONTROL).clickOn(PROPERTY_NAME_PRETTY).release(KeyCode.CONTROL);
		verifyThat(ID_BTN_UN_ASSIGN_ADVANTAGE, (Button b) -> b.isDisable());
	}
	
	@Test
	public void testAssignDisadvantage() {
		verifyThat(PROPERTY_NAME_DISADVANTAGE, NodeMatchers.isNotNull());
		clickOn(PROPERTY_NAME_DISADVANTAGE).clickOn(ID_BTN_ASSIGN_DISADVANTAGE);
		verify(mockedAventurianManager).addProperty(ad2);
	}
	
	@Test
	public void testAssignDisadvantageViaDoubleClick() {
		verifyThat(PROPERTY_NAME_DISADVANTAGE, NodeMatchers.isNotNull());
		doubleClickOn(PROPERTY_NAME_DISADVANTAGE, MouseButton.PRIMARY);
		verify(mockedAventurianManager).addProperty(ad2);		
	}

	@Test
	public void testAssignDisadvantageButtonIsDisabled() {
		final ListView<Property> allAdvantages = find(ID_LV_UN_ASSIGNED_DISADVANTAGES);
		assertTrue(allAdvantages.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_ASSIGN_DISADVANTAGE, (Button b) -> b.isDisable());
	}
	
	@Test
	public void testAssignDisadvantageButtonIsEnabled() {
		verifyThat(PROPERTY_NAME_DISADVANTAGE, NodeMatchers.isNotNull());
		clickOn(PROPERTY_NAME_DISADVANTAGE);
		final ListView<Property> allAdvantages = find(ID_LV_UN_ASSIGNED_DISADVANTAGES);
		assertFalse(allAdvantages.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_ASSIGN_DISADVANTAGE, (Button b) -> !b.isDisable());
	}
	@Category(CannotRunHeadless.class)
	@Test
	public void testToggleAssignDisadvantagesButtonEnabledDisabled() {
		testAssignDisadvantageButtonIsDisabled();
		testAssignDisadvantageButtonIsEnabled();
		press(KeyCode.CONTROL).clickOn(PROPERTY_NAME_DISADVANTAGE).release(KeyCode.CONTROL);
		verifyThat(ID_BTN_ASSIGN_DISADVANTAGE, (Button b) -> b.isDisable());
	}

	@Category(CannotRunHeadless.class)
	@Test
	public void testToggleUnAssignDisadvantagesButtonEnabledDisabled() {
		testUnAssignDisadvantageButtonIsDisabled();
		testUnAssignDisadvantageButtonIsEnabled();
		press(KeyCode.CONTROL).clickOn(PROPERTY_NAME_DISADVANTAGE).release(KeyCode.CONTROL);
		verifyThat(ID_BTN_UN_ASSIGN_DISADVANTAGE, (Button b) -> b.isDisable());
	}
	
	@Test
	public void testUnAssignDisadvantage() {
		when(mockedAventurian.getDisadvantages()).thenReturn(Arrays.asList(createProperty(PROPERTY_NAME_DISADVANTAGE, 50)));
		updateGui();

		verifyThat(PROPERTY_NAME_DISADVANTAGE, NodeMatchers.isNotNull());
		clickOn(PROPERTY_NAME_DISADVANTAGE).clickOn(ID_BTN_UN_ASSIGN_DISADVANTAGE);
		verify(mockedAventurianManager).removeProperty(ad2);
		
	}

	@Test
	public void testUnAssignDisadvantageViaDoubleClick() {
		when(mockedAventurian.getDisadvantages()).thenReturn(Arrays.asList(ad2));
		updateGui();

		verifyThat(PROPERTY_NAME_DISADVANTAGE, NodeMatchers.isNotNull());
		doubleClickOn(PROPERTY_NAME_DISADVANTAGE, MouseButton.PRIMARY);
		verify(mockedAventurianManager).removeProperty(ad2);
	}

	@Test
	public void testUnAssignDisadvantageButtonIsDisabled() {
		final ListView<Property> assignedDisadvantages = find(ID_LV_ASSIGNED_DISADVANTAGES);
		assertTrue(assignedDisadvantages.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_UN_ASSIGN_DISADVANTAGE, (Button b) -> b.isDisable());
	}

	@Test
	public void testUnAssignDisadvantageButtonIsEnabled() {
		when(mockedAventurian.getDisadvantages()).thenReturn(Arrays.asList(createProperty(PROPERTY_NAME_DISADVANTAGE, 50)));
		updateGui();

		verifyThat(PROPERTY_NAME_DISADVANTAGE, NodeMatchers.isNotNull());
		clickOn(PROPERTY_NAME_DISADVANTAGE);
		final ListView<Property> assignedProperties = find(ID_LV_ASSIGNED_DISADVANTAGES);
		assertFalse(assignedProperties.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_UN_ASSIGN_DISADVANTAGE, (Button b) -> !b.isDisable());
	}
	
	@Test
	public void testBadPropertyDecreaseButtonIsDisabled() {
		when(mockedAventurian.getDisadvantages()).thenReturn(Arrays.asList(ad4));
		updateGui();
		
		verifyThat(PROPERTY_NAME_BADPROPERTY, NodeMatchers.isNotNull());
		final Button b = find("-");
		verifyThat(b, NodeMatchers.isNotNull());
		verifyThat(b, (Button button) -> button.isDisable());
	}
	
	@Test
	public void testBadPropertyIncreaseButtonIsEnabled() {
		when(mockedAventurian.getDisadvantages()).thenReturn(Arrays.asList(ad4));
		updateGui();
		
		verifyThat(PROPERTY_NAME_BADPROPERTY, NodeMatchers.isNotNull());
		final Button b = find("+");
		verifyThat(b, NodeMatchers.isNotNull());
		verifyThat(b, (Button button) -> !button.isDisable());
	}
	
	@Test
	public void testBadPropertyIncrease() {
		testBadPropertyIncreaseButtonIsEnabled();
		clickOn("+");
		verify(mockedAventurianManager).increaseProperty(ad4);
	}
	
	@Test
	public void testBadPropertyDecreaseButtonIsEnabled() {
		ad4.increase();
		when(mockedAventurian.getDisadvantages()).thenReturn(Arrays.asList(ad4));
		updateGui();
		
		final Button b = find("-");
		verifyThat(b, NodeMatchers.isNotNull());
		verifyThat(b, (Button button) -> !button.isDisable());		
	}
	
	@Test
	public void testBadPropertyDecrease() {
		testBadPropertyDecreaseButtonIsEnabled();
		clickOn("-");
		verify(mockedAventurianManager).decreaseProperty(ad4);
	}
	
	@Override
	void setUpMocks() {
		ad1 = createProperty(PROPERTY_NAME_ROYAL, 50);
		ad2 = createProperty(PROPERTY_NAME_DISADVANTAGE, -50);
		ad3 = createProperty(PROPERTY_NAME_PRETTY, 50);
		ad4 = createBadProperty(PROPERTY_NAME_BADPROPERTY, -75);
		when(mockedDatabase.getAdvantages()).thenReturn(Arrays.asList(ad1, ad3));
		when(mockedDatabase.getDisadvantages()).thenReturn(Arrays.asList(ad2, ad4));

	}

	private Property createProperty(String name, int cost) {
		return new Property(name, "", cost, EMPTY, EMPTY, NOREQUIREMENT);
	}
	
	private BadProperty createBadProperty(String name, int cost) {
		return new BadProperty(name, "", cost, NOREQUIREMENT);
	}

}
