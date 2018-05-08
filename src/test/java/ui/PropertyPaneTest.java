package ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.matcher.base.NodeMatchers;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import skills.properties.BadProperty;
import skills.properties.Property;
import testsuites.categories.CannotRunHeadless;

@RunWith(MockitoJUnitRunner.class)
public class PropertyPaneTest extends BaseGuiTest {

	private static final String ID_BTN_ASSIGN_ADVANTAGE = "#btnAssignAdvantage";
	private static final String ID_BTN_UN_ASSIGN_ADVANTAGE = "#btnUnassignAdvantage";
	private static final String ID_LV_UN_ASSIGNED_ADVANTAGES = "#lvUnassignedAdvantages";
	private static final String ID_LV_ASSIGNED_ADVANTAGES = "#lvAssignedAdvantages";
	private static final String ID_LV_UN_ASSIGNED_DISADVANTAGES = "#lvUnassignedDisadvantages";
	private static final String ID_LV_ASSIGNED_DISADVANTAGES = "#lvAssignedDisadvantages";
	private static final String ID_BTN_ASSIGN_DISADVANTAGE = "#btnAssignDisadvantage";
	private static final String ID_BTN_UN_ASSIGN_DISADVANTAGE = "#btnUnassignDisadvantage";

	private Property disadvantageMock;
	private Property royalMock;
	private Property prettyMock;
	private BadProperty badPropertyMock;

	@Before
	public void setUp() throws Exception {
		clickOn("#hyperlinkProperties");
		verifyThat("#paneProperties", isVisible());
	}

	@Test
	public void testAssignAdvantage() {
		verifyThat(royalMock.toString(), NodeMatchers.isNotNull());
		clickOn(royalMock.toString()).clickOn(ID_BTN_ASSIGN_ADVANTAGE);
		verify(mockedAventurianManager).addProperty(any(Property.class));
	}

	@Test
	public void testAssignAdvantageViaDoubleClick() {
		verifyThat(royalMock.toString(), NodeMatchers.isNotNull());
		doubleClickOn(royalMock.toString(), MouseButton.PRIMARY);
		verify(mockedAventurianManager).addProperty(any(Property.class));
	}

	@Test
	public void testUnAssignAdvantage() {
		when(mockedAventurian.getAdvantages()).thenReturn(Arrays.asList(prettyMock));
		updateGui();

		verifyThat(prettyMock.toString(), NodeMatchers.isNotNull());
		clickOn(prettyMock.toString()).clickOn(ID_BTN_UN_ASSIGN_ADVANTAGE);
		verify(mockedAventurianManager).removeProperty(any(Property.class));
	}

	@Test
	public void testUnAssignAdvantageViaDoubleClick() {
		when(mockedAventurian.getAdvantages()).thenReturn(Arrays.asList(prettyMock));
		when(mockedAventurianManager.canRemoveProperty(prettyMock)).thenReturn(true);
		updateGui();

		verifyThat(prettyMock.toString(), NodeMatchers.isNotNull());
		doubleClickOn(prettyMock.toString(), MouseButton.PRIMARY);
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
		when(mockedAventurian.getAdvantages()).thenReturn(Arrays.asList(prettyMock));
		updateGui();

		verifyThat(prettyMock.toString(), NodeMatchers.isNotNull());
		clickOn(prettyMock.toString());
		final ListView<Property> assignedProperties = find(ID_LV_ASSIGNED_ADVANTAGES);
		assertFalse(assignedProperties.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_UN_ASSIGN_ADVANTAGE, (Button b) -> !b.isDisable());
	}

	@Test
	public void testAssignAdvantageButtonIsEnabled() {
		verifyThat(royalMock.toString(), NodeMatchers.isNotNull());
		clickOn(royalMock.toString());
		final ListView<Property> allAdvantages = find(ID_LV_UN_ASSIGNED_ADVANTAGES);
		assertFalse(allAdvantages.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_ASSIGN_ADVANTAGE, (Button b) -> !b.isDisable());
	}

	@Category(CannotRunHeadless.class)
	@Test
	public void testToggleAssignAdvantagesButtonEnabledDisabled() {
		testAssignAdvantageButtonIsDisabled();
		testAssignAdvantageButtonIsEnabled();
		press(KeyCode.CONTROL).clickOn(royalMock.toString()).release(KeyCode.CONTROL);
		verifyThat(ID_BTN_ASSIGN_ADVANTAGE, (Button b) -> b.isDisable());
	}

	@Category(CannotRunHeadless.class)
	@Test
	public void testToggleUnAssignAdvantagesButtonEnabledDisabled() {
		testUnAssignAdvantageButtonIsDisabled();
		testUnAssignAdvantageButtonIsEnabled();
		press(KeyCode.CONTROL).clickOn(prettyMock.toString()).release(KeyCode.CONTROL);
		verifyThat(ID_BTN_UN_ASSIGN_ADVANTAGE, (Button b) -> b.isDisable());
	}

	@Test
	public void testAssignDisadvantage() {
		verifyThat(disadvantageMock.toString(), NodeMatchers.isNotNull());
		clickOn(disadvantageMock.toString()).clickOn(ID_BTN_ASSIGN_DISADVANTAGE);
		verify(mockedAventurianManager).addProperty(disadvantageMock);
	}

	@Test
	public void testAssignDisadvantageViaDoubleClick() {
		verifyThat(disadvantageMock.toString(), NodeMatchers.isNotNull());
		doubleClickOn(disadvantageMock.toString(), MouseButton.PRIMARY);
		verify(mockedAventurianManager).addProperty(disadvantageMock);
	}

	@Test
	public void testAssignDisadvantageButtonIsDisabled() {
		final ListView<Property> allAdvantages = find(ID_LV_UN_ASSIGNED_DISADVANTAGES);
		assertTrue(allAdvantages.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_ASSIGN_DISADVANTAGE, (Button b) -> b.isDisable());
	}

	@Test
	public void testAssignDisadvantageButtonIsEnabled() {
		verifyThat(disadvantageMock.toString(), NodeMatchers.isNotNull());
		clickOn(disadvantageMock.toString());
		final ListView<Property> allAdvantages = find(ID_LV_UN_ASSIGNED_DISADVANTAGES);
		assertFalse(allAdvantages.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_ASSIGN_DISADVANTAGE, (Button b) -> !b.isDisable());
	}

	@Category(CannotRunHeadless.class)
	@Test
	public void testToggleAssignDisadvantagesButtonEnabledDisabled() {
		testAssignDisadvantageButtonIsDisabled();
		testAssignDisadvantageButtonIsEnabled();
		press(KeyCode.CONTROL).clickOn(disadvantageMock.toString()).release(KeyCode.CONTROL);
		verifyThat(ID_BTN_ASSIGN_DISADVANTAGE, (Button b) -> b.isDisable());
	}

	@Category(CannotRunHeadless.class)
	@Test
	public void testToggleUnAssignDisadvantagesButtonEnabledDisabled() {
		testUnAssignDisadvantageButtonIsDisabled();
		testUnAssignDisadvantageButtonIsEnabled();
		press(KeyCode.CONTROL).clickOn(disadvantageMock.toString()).release(KeyCode.CONTROL);
		verifyThat(ID_BTN_UN_ASSIGN_DISADVANTAGE, (Button b) -> b.isDisable());
	}

	@Test
	public void testUnAssignDisadvantage() {
		when(mockedAventurian.getDisadvantages()).thenReturn(Arrays.asList(disadvantageMock));
		updateGui();

		verifyThat(disadvantageMock.toString(), NodeMatchers.isNotNull());
		clickOn(disadvantageMock.toString()).clickOn(ID_BTN_UN_ASSIGN_DISADVANTAGE);
		verify(mockedAventurianManager).removeProperty(disadvantageMock);

	}

	@Test
	public void testUnAssignDisadvantageViaDoubleClick() {
		when(mockedAventurian.getDisadvantages()).thenReturn(Arrays.asList(disadvantageMock));
		when(mockedAventurianManager.canRemoveProperty(disadvantageMock)).thenReturn(true);
		updateGui();

		verifyThat(disadvantageMock.toString(), NodeMatchers.isNotNull());
		doubleClickOn(disadvantageMock.toString(), MouseButton.PRIMARY);
		verify(mockedAventurianManager).removeProperty(disadvantageMock);
	}

	@Test
	public void testUnAssignDisadvantageButtonIsDisabled() {
		final ListView<Property> assignedDisadvantages = find(ID_LV_ASSIGNED_DISADVANTAGES);
		assertTrue(assignedDisadvantages.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_UN_ASSIGN_DISADVANTAGE, (Button b) -> b.isDisable());
	}

	@Test
	public void testUnAssignDisadvantageButtonIsEnabled() {
		when(mockedAventurian.getDisadvantages()).thenReturn(Arrays.asList(disadvantageMock));
		updateGui();

		verifyThat(disadvantageMock.toString(), NodeMatchers.isNotNull());
		clickOn(disadvantageMock.toString());
		final ListView<Property> assignedProperties = find(ID_LV_ASSIGNED_DISADVANTAGES);
		assertFalse(assignedProperties.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_UN_ASSIGN_DISADVANTAGE, (Button b) -> !b.isDisable());
	}

	@Test
	public void testBadPropertyDecreaseButtonIsDisabled() {
		when(mockedAventurianManager.canDecreaseProperty(badPropertyMock)).thenReturn(false);
		when(mockedAventurian.getDisadvantages()).thenReturn(Arrays.asList(badPropertyMock));
		updateGui();

		verifyThat(badPropertyMock.toString(), NodeMatchers.isNotNull());
		final Button b = find("-");
		verifyThat(b, NodeMatchers.isNotNull());
		verifyThat(b, (Button button) -> button.isDisable());
	}

	@Test
	public void testBadPropertyIncreaseButtonIsEnabled() {
		when(mockedAventurian.getDisadvantages()).thenReturn(Arrays.asList(badPropertyMock));
		updateGui();

		verifyThat(badPropertyMock.toString(), NodeMatchers.isNotNull());
		final Button b = find("+");
		verifyThat(b, NodeMatchers.isNotNull());
		verifyThat(b, (Button button) -> !button.isDisable());
	}

	@Test
	public void testBadPropertyIncreaseButtonGetsDisabled() {
		when(mockedAventurianManager.canIncreaseProperty(badPropertyMock)).thenReturn(false);
		when(mockedAventurian.getDisadvantages()).thenReturn(Arrays.asList(badPropertyMock));
		updateGui();

		verifyThat(badPropertyMock.toString(), NodeMatchers.isNotNull());
		final Button b = find("+");
		verifyThat(b, NodeMatchers.isNotNull());
		verifyThat(b, (Button button) -> button.isDisable());
	}

	@Test
	public void testBadPropertyIncrease() {
		testBadPropertyIncreaseButtonIsEnabled();
		clickOn("+");
		verify(mockedAventurianManager).increaseProperty(badPropertyMock);
	}

	@Test
	public void testBadPropertyDecreaseButtonIsEnabled() {
		when(mockedAventurianManager.canDecreaseProperty(badPropertyMock)).thenReturn(true);
		when(mockedAventurian.getDisadvantages()).thenReturn(Arrays.asList(badPropertyMock));
		updateGui();

		final Button b = find("-");
		verifyThat(b, NodeMatchers.isNotNull());
		verifyThat(b, (Button button) -> !button.isDisable());
	}

	@Test
	public void testBadPropertyDecrease() {
		testBadPropertyDecreaseButtonIsEnabled();
		clickOn("-");
		verify(mockedAventurianManager).decreaseProperty(badPropertyMock);
	}

	@Override
	void setUpMocks() {
		royalMock = createProperty("Adlig", 50);
		disadvantageMock = createProperty("Disadvantage", -50);
		prettyMock = createProperty("Pretty", 50);
		badPropertyMock = createBadPropertyMock("badProperty", -75);
		when(mockedDatabase.getAdvantages()).thenReturn(Arrays.asList(royalMock, prettyMock));
		when(mockedDatabase.getDisadvantages()).thenReturn(Arrays.asList(disadvantageMock, badPropertyMock));
		when(mockedAventurianManager.canAddProperty(any(Property.class))).thenReturn(true);
		when(mockedAventurianManager.canIncreaseProperty(any(Property.class))).thenReturn(true);

	}

	private Property createProperty(String name, int cost) {
		final Property p = mock(Property.class);
		when(p.getName()).thenReturn(name);
		when(p.toString()).thenReturn(name + " (" + Math.abs(cost) + ")");
		when(p.isAdvantage()).thenReturn(cost > 0);
		when(p.isDisadvantage()).thenReturn(cost < 0);
		when(p.isIncreasable()).thenReturn(false);
		when(p.getMaxLevel()).thenReturn(1);
		when(p.getMinLevel()).thenReturn(1);
		when(p.getTotalCosts()).thenReturn(cost);
		return new Property(name, "", cost, EMPTY, EMPTY);

	}

	private BadProperty createBadPropertyMock(String name, int cost) {
		final BadProperty bP = Mockito.mock(BadProperty.class);
		when(bP.getName()).thenReturn(name);
		when(bP.toString()).thenReturn(name + " (" + Math.abs(cost) + ")");
		when(bP.getTotalCosts()).thenReturn(50);
		when(bP.isIncreasable()).thenReturn(true);
		when(bP.isDecreasable()).thenReturn(false);
		when(bP.getMaxLevel()).thenReturn(12);
		when(bP.getMinLevel()).thenReturn(5);
		when(bP.getTotalCosts()).thenReturn(50);

		return bP;
	}

}
