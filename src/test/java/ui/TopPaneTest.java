package ui;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TopPaneTest extends BaseGuiTest {

	@Override
	void setUpMocks() {
//		verifyThat("#top", isVisible());
	}
	
	@Test
	public void testName() {
		final TextField name = find("#name");
		verifyThat(name, isNotNull());
		updateGui();
		clickOn(name).write("asdf").press(KeyCode.ENTER);
		verify(mockedAventurianManager).setName("asdf");
	}
	
	

}
