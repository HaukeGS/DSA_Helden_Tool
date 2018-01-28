package ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import aventurian.Race;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

@RunWith(MockitoJUnitRunner.class)
public class MenuTest extends BaseGuiTest {

	@Before
	public void setUp() throws Exception {
		verifyThat("#menu", isVisible());
	}

	@Test
	public void testNewDialogVisible() {
		clickOn("#fileMenu").clickOn("#newMenuItem");
		verifyThat("#newAventurianDialog", isVisible());
		clickOn("#btnCancel");
	}

	@Test
	public void testCreateNewAventurianAllConditionsMet() {
		clickOn("#fileMenu").clickOn("#newMenuItem");
		clickOn("#txtFieldName").write("test");
		clickOn("#txtFieldAp").write("1000");
		final Button btnOk = find("#btnOk");
		verifyThat(btnOk, isEnabled());
		clickOn(btnOk);
		verify(mockedAventurianManager).createNewAventurian(any(String.class), any(Integer.class), any(Race.class));
	}

	@Test
	public void testCreateNewAventurianNoNameSet() {
		clickOn("#fileMenu").clickOn("#newMenuItem");
		clickOn("#txtFieldAp").write("1000");
		final Button btnOk = find("#btnOk");
		verifyThat(btnOk, isDisabled());
		clickOn(btnOk);
		verify(mockedAventurianManager, never()).createNewAventurian(any(String.class), any(Integer.class),
				any(Race.class));
		clickOn("#btnCancel");
	}

	@Test
	public void testCreateNewAventurianInvalidApSet() {
		clickOn("#fileMenu").clickOn("#newMenuItem");
		clickOn("#txtFieldName").write("test");
		clickOn("#txtFieldAp").write("ab");
		final Button btnOk = find("#btnOk");
		verifyThat(btnOk, isDisabled());
		clickOn(btnOk);
		verify(mockedAventurianManager, never()).createNewAventurian(any(String.class), any(Integer.class),
				any(Race.class));
		clickOn("#btnCancel");
	}

	@Test
	public void testCreateNewAventurianNoApSet() {
		clickOn("#fileMenu").clickOn("#newMenuItem");
		clickOn("#txtFieldName").write("test");
		clickOn("#txtFieldAp").type(KeyCode.BACK_SPACE).type(KeyCode.BACK_SPACE).type(KeyCode.BACK_SPACE)
				.type(KeyCode.BACK_SPACE).type(KeyCode.BACK_SPACE);
		final Button btnOk = find("#btnOk");
		verifyThat(btnOk, isDisabled());
		clickOn(btnOk);
		verify(mockedAventurianManager, never()).createNewAventurian(any(String.class), any(Integer.class),
				any(Race.class));
		clickOn("#btnCancel");
	}

	@Override
	void setUpMocks() {
		// TODO Auto-generated method stub

	}

}
