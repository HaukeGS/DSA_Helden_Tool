package ui;

import aventurian.Aventurian;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TopController extends XController {

	@FXML
	public TextField name;

	@Override
	public void update(Aventurian updatedAventurian) {
		name.setText(updatedAventurian.getName());

	}

	public void setName() {
		m.setName(name.getText());
	}

}
