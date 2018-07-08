package ui;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

import java.util.List;

import aventurian.Aventurian;
import aventurian.AventurianManagerFacade;
import database.Database;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import skills.Skill;

public abstract class PaneController {

	protected AventurianManagerFacade m;
	protected Database db;

	void init(AventurianManagerFacade manager, Database database) {
		m = manager;
		db = database;
		initControllerSpecificStuff();
	}

	protected final boolean isConfirmed(Skill item, List<Skill> dependingSkills) {
		final Alert alert = new Alert(AlertType.WARNING);

		alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
		alert.setTitle(item.toString());
		alert.setHeaderText(
				"Folgende Skills sind abhaengig von " + item.toString() + "\nund werden verringert/entfernt:");
		final ListView<Skill> lv = new ListView<>(FXCollections.observableArrayList(dependingSkills));
		lv.setPrefHeight(100);
		lv.setMaxHeight(USE_PREF_SIZE);
		lv.setStyle("-fx-control-inner-background-alt: -fx-control-inner-background ;");
		alert.getDialogPane().setContent(lv);
		return alert.showAndWait().map(t -> t == ButtonType.OK).orElse(false);

	}

	protected final void askForConfirmation(Skill skill, List<Skill> dependingSkills, Runnable executeWhenConfirmed) {
		if (dependingSkills.isEmpty())
			executeWhenConfirmed.run();
		else {
			if (isConfirmed(skill, dependingSkills)) {
				executeWhenConfirmed.run();
			}
		}
	}

	abstract void initControllerSpecificStuff();

	abstract void update(Aventurian updatedAventurian);

}
