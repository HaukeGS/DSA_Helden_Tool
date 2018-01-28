package ui;

import aventurian.Aventurian;

public class NavigationPaneController extends PaneController {

	enum PAGES {
		ATTRIBUTES, LANGUAGES, PROPERTIES
	};

	private MainController mainController;

	public void showLanguages() {
		mainController.changeTo(PAGES.LANGUAGES);
	}

	public void showAttributes() {
		mainController.changeTo(PAGES.ATTRIBUTES);
	}
	
	public void showProperties() {
		mainController.changeTo(PAGES.PROPERTIES);
	}

	public void init(MainController mainController) {
		this.mainController = mainController;
	}

	@Override
	public void update(Aventurian updatedAventurian) {
		// nothing to do here since we do not display any info about aventurian

	}

	@Override
	void initControllerSpecificStuff() {
		// nothing to here
	}

}
