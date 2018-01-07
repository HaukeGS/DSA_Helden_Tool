package ui;

import aventurian.Aventurian;
import aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AttributesController extends XController {

	@FXML
	Label labelCourage;

	public void increaseCourage() {
		m.increasePrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE);
	}

	public void decreaseCourage() {
		m.decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE);
	}

	@Override
	public void update(Aventurian updatedAventurian) {
		labelCourage.setText(String.valueOf(updatedAventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE)));

	}

	@Override
	void initControllerSpecificStuff() {
		// TODO nothing to do here!?
	}
}
