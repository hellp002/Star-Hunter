package logic;

import java.net.URL;
import java.util.ResourceBundle;

import bgm.AudioLoad;
import bgm.SFXPlayer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class Controller implements Initializable {
	@FXML
	private Button startBtn, helpBtn, exitBtn;

	@FXML
	private StackPane tutorial;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void enterTheGame() {
		SceneController.loadGame();
		SFXPlayer.getSfxMap().get("btn").play();
		AudioLoad.playMusic("Dungeon");
	}

	@FXML
	public void getTutorial() {
		AudioLoad.playMusic("Tutorial");
		SFXPlayer.getSfxMap().get("btn").play();
		Parent rootToAdd = SceneController.getRootMap().get("Tutorial");
		tutorial.getChildren().add(rootToAdd);
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
		StackPane a = SceneController.getControlMap().get("Main_Menu").tutorial;
		a.getChildren().clear();
		SceneController.getWindow().setTitle("Star Hunter - Main Menu");
	}

}
