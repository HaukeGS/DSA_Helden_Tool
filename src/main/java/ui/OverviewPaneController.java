package ui;

import aventurian.Aventurian;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OverviewPaneController extends PaneController {
	@FXML
	public Label labelRemainingActionPoints;
	@FXML
	public Label labelAttributes;
	@FXML
	public Label labelAdvantages;
	@FXML
	public Label labelDisadvantages;
	@FXML
	public Label labelBadProperties;

	@Override
	void update(Aventurian updatedAventurian) {
		labelRemainingActionPoints.setText(String.valueOf(updatedAventurian.getAdventurePoints()));
		labelAttributes.setText(String.valueOf(updatedAventurian.getAPInAttributes()));
	}

	@Override
	void initControllerSpecificStuff() {
		// TODO Auto-generated method stub
	}

}
