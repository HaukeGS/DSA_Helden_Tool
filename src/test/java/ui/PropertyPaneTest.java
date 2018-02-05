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
import skills.Property;
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
	public void testToggleAssignButtonEnabledDisabled() {
		testAssignAdvantageButtonIsDisabled();
		testAssignAdvantageButtonIsEnabled();
		press(KeyCode.CONTROL).clickOn(PROPERTY_NAME_ROYAL).release(KeyCode.CONTROL);
		verifyThat(ID_BTN_ASSIGN_ADVANTAGE, (Button b) -> b.isDisable());
	}

	@Category(CannotRunHeadless.class)
	@Test
	public void testToggleUnAssignButtonEnabledDisabled() {
		testUnAssignAdvantageButtonIsDisabled();
		testUnAssignAdvantageButtonIsEnabled();
		press(KeyCode.CONTROL).clickOn(PROPERTY_NAME_PRETTY).release(KeyCode.CONTROL);
		verifyThat(ID_BTN_UN_ASSIGN_ADVANTAGE, (Button b) -> b.isDisable());
	}
	@Override
	void setUpMocks() {
		final Property ad1 = createProperty(PROPERTY_NAME_ROYAL, 50);
		final Property ad2 = createProperty(PROPERTY_NAME_DISADVANTAGE, -50);
		final Property ad3 = createProperty(PROPERTY_NAME_PRETTY, 50);
		when(mockedDatabase.getAdvantages()).thenReturn(Arrays.asList(ad1, ad3));
		when(mockedDatabase.getDisadvantages()).thenReturn(Arrays.asList(ad2));

	}

	private Property createProperty(String name, int cost) {
		return new Property(name, "", cost, EMPTY, EMPTY, NOREQUIREMENT);
	}

}
