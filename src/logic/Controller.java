package logic;

import java.net.URL;
import java.util.ResourceBundle;

import bgm.AudioLoad;
import bgm.SFXPlayer;
import gui.SceneController;
import gui.Selection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class Controller implements Initializable {
	@FXML
	private Button startBtn, helpBtn, exitBtn;

	@FXML
	private StackPane pane,buttonZone;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void selectType() {
		Selection selectbox = new Selection();
		pane.getChildren().add(selectbox);
		buttonZone.setDisable(true);
		pane.requestFocus();
		SFXPlayer.getSfxMap().get("btn").play();
		
	}


	@FXML
	public void getTutorial() {
		AudioLoad.playMusic("Tutorial");
		SFXPlayer.getSfxMap().get("btn").play();
		Parent rootToAdd = SceneController.getRootMap().get("Tutorial");
		pane.getChildren().add(rootToAdd);
		SceneController.getWindow().setTitle("Star Hunter - Tutorial Time");

	}

	public void exitGame() {
		SFXPlayer.getSfxMap().get("btn").play();
		SceneController.getWindow().close();
	}

	@FXML
	public void loadMainMenu() {
		AudioLoad.playMusic("Main_Menu");
		SFXPlayer.getSfxMap().get("btn").play();
		StackPane a = SceneController.getControlMap().get("Main_Menu").pane;
		a.getChildren().clear();
		SceneController.getWindow().setTitle("Star Hunter - Main Menu");
	}

	public StackPane getPane() {
		return pane;
	}
	
	public StackPane getButtonZone() {
		return buttonZone;
	}


}
