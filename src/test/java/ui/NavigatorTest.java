package ui;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javafx.scene.control.Hyperlink;

@RunWith(MockitoJUnitRunner.class)
public class NavigatorTest extends BaseGuiTest {

	@Test
	public void testClickOnLanguages() {
		final Hyperlink languages = find("#hyperlinkLanguages");
		clickOn(languages);
		verifyThat("#paneLanguages", isVisible());
	}

	@Test
	public void testClickOnAttributes() {
		final Hyperlink attributes = find("#hyperlinkAttributes");
		clickOn(attributes);
		verifyThat("#paneAttributes", isVisible());
	}
	
	@Test
	public void testClickOnProperties() {
		final Hyperlink properties = find("#hyperlinkProperties");
		clickOn(properties);
		verifyThat("#paneProperties", isVisible());
	}

	@Override
	void setUpMocks() {
	}
}
