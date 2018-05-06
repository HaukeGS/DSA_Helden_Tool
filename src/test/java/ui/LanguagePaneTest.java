package ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

import java.util.Arrays;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.matcher.base.NodeMatchers;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import skills.languages.Language;
import testsuites.categories.CannotRunHeadless;

@RunWith(MockitoJUnitRunner.class)
public class LanguagePaneTest extends BaseGuiTest {

	private static final String ID_BTN_ASSIGN_LANGUAGE = "#btnAssignLanguage";
	private static final String ID_BTN_UN_ASSIGN_LANGUAGE = "#btnUnAssignLanguage";
	private static final String ID_BTN_NATIVE_TONGUE = "#btnNativeTongue";
	private static final String ID_LV_ASSIGNED_LANGUAGES = "#lvAssignedLanguages";
	private static final String ID_LV_UN_ASSIGNED_LANGUAGES = "#lvUnAssignedLanguages";

	private Language garethi;
	private Language blablub;
	private Language assignedLanguage;

	@Before
	public void setUp() {
		clickOn("#hyperlinkLanguages");
		verifyThat("#paneLanguages", isVisible());
	}

	@Test
	public void testAssignLanguage() {
		verifyThat(garethi.toString(), NodeMatchers.isNotNull());
		clickOn(garethi.toString()).clickOn(ID_BTN_ASSIGN_LANGUAGE);
		verify(mockedAventurianManager).addLanguage(any(Language.class));
	}

	@Test
	public void testAssignLanguageAsNativeTongue() {
		verifyThat(garethi.toString(), NodeMatchers.isNotNull());
		final Button btn = find(ID_BTN_NATIVE_TONGUE);
		assertFalse(btn.isDisable());
		clickOn(btn);
		verify(mockedAventurianManager).addLanguageAsNativeTongue(any(Language.class));

	}

	@Test
	public void testNativeTongueButtonsGetDisabled() throws InterruptedException {
		final Set<Button> nativeTongueButtons = findAll(ID_BTN_NATIVE_TONGUE);
		when(mockedAventurianManager.canAddLanguageAsNativeTongue(garethi)).thenReturn(false);
		when(mockedAventurianManager.canAddLanguageAsNativeTongue(blablub)).thenReturn(false);
		updateGui();
		nativeTongueButtons.forEach(b -> assertTrue(b.isDisable()));
	}

	@Test
	public void testAssignLanguageViaDoubleClick() {
		verifyThat(garethi.toString(), NodeMatchers.isNotNull());
		doubleClickOn(garethi.toString(), MouseButton.PRIMARY);
		verify(mockedAventurianManager).addLanguage(any(Language.class));
	}

	@Test
	public void testUnAssignLanguage() {
		when(mockedAventurian.getLanguages()).thenReturn(Arrays.asList(assignedLanguage));
		when(mockedAventurianManager.canRemoveLanguage(assignedLanguage)).thenReturn(true);
		updateGui();

		verifyThat(assignedLanguage.getName(), NodeMatchers.isNotNull());
		clickOn(assignedLanguage.getName()).clickOn(ID_BTN_UN_ASSIGN_LANGUAGE);
		final Button unassign = find(ID_BTN_UN_ASSIGN_LANGUAGE);
		assertFalse(unassign.isDisable());
		verify(mockedAventurianManager).removeLanguage(assignedLanguage);
	}

	@Test
	public void testUnAssignLanguageViaDoubleClick() {
		when(mockedAventurian.getLanguages()).thenReturn(Arrays.asList(assignedLanguage));
		when(mockedAventurianManager.canRemoveLanguage(assignedLanguage)).thenReturn(true);
		updateGui();

		verifyThat(assignedLanguage.getName(), NodeMatchers.isNotNull());
		doubleClickOn(assignedLanguage.getName());
		verify(mockedAventurianManager).removeLanguage(any(Language.class));
	}

	@Test
	public void testAssignLanguageButtonIsDisabled() {
		final ListView<Language> allLanguages = find(ID_LV_UN_ASSIGNED_LANGUAGES);
		assertTrue(allLanguages.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_ASSIGN_LANGUAGE, (Button b) -> b.isDisable());
	}

	@Test
	public void testUnAssignLanguageButtonIsDisabled() {
		final ListView<Language> allLanguages = find(ID_LV_ASSIGNED_LANGUAGES);
		assertTrue(allLanguages.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_UN_ASSIGN_LANGUAGE, (Button b) -> b.isDisable());
	}

	@Test
	public void testUnAssignLanguageButtonIsEnabled() {
		when(mockedAventurian.getLanguages()).thenReturn(Arrays.asList(assignedLanguage));
		when(mockedAventurianManager.canRemoveLanguage(assignedLanguage)).thenReturn(true);
		updateGui();

		verifyThat(assignedLanguage.getName(), NodeMatchers.isNotNull());
		clickOn(assignedLanguage.getName());
		final ListView<Language> assignedLanguages = find(ID_LV_ASSIGNED_LANGUAGES);
		assertFalse(assignedLanguages.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_UN_ASSIGN_LANGUAGE, (Button b) -> !b.isDisable());
	}

	@Category(CannotRunHeadless.class)
	@Test
	public void testToggleAssignButtonEnabledDisabled() {
		testAssignLanguageButtonIsDisabled();
		testAssignLanguageButtonIsEnabled();
		press(KeyCode.CONTROL).clickOn(garethi.toString()).release(KeyCode.CONTROL);
		verifyThat(ID_BTN_ASSIGN_LANGUAGE, (Button b) -> b.isDisable());
	}

	@Category(CannotRunHeadless.class)
	@Test
	public void testToggleUnAssignButtonEnabledDisabled() {
		testUnAssignLanguageButtonIsDisabled();
		testUnAssignLanguageButtonIsEnabled();
		press(KeyCode.CONTROL).clickOn(assignedLanguage.getName()).release(KeyCode.CONTROL);
		verifyThat(ID_BTN_UN_ASSIGN_LANGUAGE, (Button b) -> b.isDisable());
	}

	@Test
	public void testAssignLanguageButtonIsEnabled() {
		verifyThat(garethi.toString(), NodeMatchers.isNotNull());
		clickOn(garethi.toString());
		final ListView<Language> allLanguages = find(ID_LV_UN_ASSIGNED_LANGUAGES);
		assertFalse(allLanguages.getSelectionModel().isEmpty());
		verifyThat(ID_BTN_ASSIGN_LANGUAGE, (Button b) -> !b.isDisable());
	}

	@Test
	public void testIncreaseLanguageLevel() {
		when(mockedAventurian.getLanguages()).thenReturn(Arrays.asList(assignedLanguage));
		updateGui();

		verifyThat(assignedLanguage.getName(), NodeMatchers.isNotNull());
		final Button btn = find("+");
		verifyThat(btn, (Button button) -> !button.isDisable());
		clickOn(btn);
		verify(mockedAventurianManager).increaseLanguage(any(Language.class));
	}

	@Test
	public void testIncreaseLanguageLevelButtonGetsDisabled() {
		when(assignedLanguage.isIncreasable()).thenReturn(false);
		when(mockedAventurian.getLanguages()).thenReturn(Arrays.asList(assignedLanguage));
		updateGui();

		verifyThat(assignedLanguage.getName(), NodeMatchers.isNotNull());
		final Button btn = find("+");
		verifyThat(btn, (Button button) -> button.isDisable());
	}

	@Test
	public void testDecreaseLanguageLevel() {
		when(assignedLanguage.isDecreasable()).thenReturn(true);
		when(mockedAventurian.getLanguages()).thenReturn(Arrays.asList(assignedLanguage));
		updateGui();

		verifyThat(assignedLanguage.getName(), NodeMatchers.isNotNull());
		final Button btn = find("-");
		verifyThat(btn, (Button button) -> !button.isDisable());
		clickOn(btn);
		verify(mockedAventurianManager).decreaseLanguage(any(Language.class));
	}

	@Test
	public void testListAreDisjunct() {
		when(mockedAventurian.getLanguages()).thenReturn(Arrays.asList(assignedLanguage));
		updateGui();

		final ListView<Language> lvAssigned = find(ID_LV_ASSIGNED_LANGUAGES);
		final ListView<Language> lvUnAssigned = find(ID_LV_UN_ASSIGNED_LANGUAGES);
		final ObservableList<Language> assignedLanguages = lvAssigned.getItems();
		final ObservableList<Language> unAssignedLanguages = lvUnAssigned.getItems();

		assertTrue(assignedLanguages.stream().noneMatch(l -> unAssignedLanguages.contains(l)));
		assertTrue(unAssignedLanguages.stream().noneMatch(l -> assignedLanguages.contains(l)));

	}

	@Override
	void setUpMocks() {
		// Database
		garethi = createLanguage("Garethi", 100);
		blablub = createLanguage("blablub", 50);
		assignedLanguage = createLanguage("assignedLanguage", 50);
		when(mockedDatabase.getLanguages()).thenReturn(Arrays.asList(garethi, blablub, assignedLanguage));
		when(mockedAventurianManager.canAddLanguage(garethi)).thenReturn(true);
		when(mockedAventurianManager.canAddLanguage(blablub)).thenReturn(true);
		when(mockedAventurianManager.canAddLanguageAsNativeTongue(garethi)).thenReturn(true);
		when(mockedAventurianManager.canAddLanguageAsNativeTongue(blablub)).thenReturn(true);
	}

	private Language createLanguage(String name, int cost) {
		final Language l = Mockito.mock(Language.class);
		when(l.getName()).thenReturn(name);
		when(l.isIncreasable()).thenReturn(true);
		when(l.getMaxLevel()).thenReturn(5);
		when(l.getMinLevel()).thenReturn(1);
		when(l.isNativeTongue()).thenReturn(false);
		when(l.getTotalCosts()).thenReturn(cost);
		when(l.isAllowed(mockedAventurian)).thenReturn(true);
		when(l.toString()).thenReturn(name + " (" + Math.abs(cost) + ")");
		return l;
	}

}
