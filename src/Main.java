import java.io.IOException;
import java.util.HashMap;

import bgm.AudioLoad;
import bgm.Music;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import logic.Controller;
import logic.SceneController;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		SceneController.setWindow(primaryStage);
		primaryStage.setResizable(false);
		HashMap<String, Scene> sceneMap = SceneController.getSceneMap();
		HashMap<String, Parent> rootMap = SceneController.getRootMap();
		HashMap<String, Controller> controlMap = SceneController.getControlMap();
		loadMainMenuFile(sceneMap, controlMap);
		loadTutorial(rootMap, controlMap);
		loadMainMenuBGM();
		loadTutorialBGM();
		loadGameBGM1();
		loadGameBGMlastlong();
		AudioLoad.playMusic("Main_Menu");
		SceneController.loadMainMenu();
	
	}

	public static void main(String[] args) {
		launch(args);
		
	}

	private void loadMainMenuFile(HashMap<String, Scene> sceneMap, HashMap<String, Controller> controlMap)
			throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu2.fxml"));
		Scene sceneToAdd = new Scene(loader.load());
		sceneMap.put("Main_Menu", sceneToAdd);
		controlMap.put("Main_Menu", loader.getController());
	}


	private void loadTutorial(HashMap<String, Parent> rootMap, HashMap<String, Controller> controlMap)
			throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Tutorial.fxml"));
		rootMap.put("Tutorial", loader.load());
		controlMap.put("Tutorial", loader.getController());
	}

	private void loadMainMenuBGM() {
		HashMap<String, MediaPlayer> mediaMap = AudioLoad.getMediaMap();
		MediaPlayer bgm = new MediaPlayer(new Media(getClass().getResource(Music.MAIN_MENU).toString()));
		mediaMap.put("Main_Menu", bgm);
	}

	private void loadTutorialBGM() {
		HashMap<String, MediaPlayer> mediaMap = AudioLoad.getMediaMap();
		MediaPlayer bgm = new MediaPlayer(new Media(getClass().getResource(Music.TUTORIAL).toString()));
		mediaMap.put("Tutorial", bgm);
	}
	
	private void loadGameBGM1() {
		HashMap<String, MediaPlayer> mediaMap = AudioLoad.getMediaMap();
		MediaPlayer bgm = new MediaPlayer(new Media(getClass().getResource(Music.Dungeon).toString()));
		mediaMap.put("Dungeon", bgm);
	}
	
	private void loadGameBGMlastlong() {
		HashMap<String, MediaPlayer> mediaMap = AudioLoad.getMediaMap();
		MediaPlayer bgm = new MediaPlayer(new Media(getClass().getResource(Music.LASTLONG).toString()));
		mediaMap.put("Lastlong", bgm);
	}
}
