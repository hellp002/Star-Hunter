package gui;

import java.util.HashMap;

import bgm.AudioLoad;
import bgm.SFXPlayer;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import logic.Controller;
import logic.GameLogic;
import sharedObject.RenderableHolder;
import tools.Data;

public class SceneController {

	private static Stage window;
	private static Scene currentScene;
	private static boolean showSkill;
	private static MainGame game;
	private static long lastPause, lastResume, timeAdd;

	private static HashMap<String, Scene> sceneMap = new HashMap<String, Scene>();
	private static HashMap<String, Parent> rootMap = new HashMap<String, Parent>();
	private static HashMap<String, Controller> controlMap = new HashMap<String, Controller>();
	private static AnimationTimer animation;

	public static HashMap<String, Controller> getControlMap() {
		return controlMap;
	}

	public static HashMap<String, Parent> getRootMap() {
		return rootMap;
	}

	public static HashMap<String, Scene> getSceneMap() {
		return sceneMap;
	}

	public static Stage getWindow() {
		return window;
	}

	public static void setWindow(Stage window) {
		SceneController.window = window;
	}

	public static Scene getCurrentScene() {
		return currentScene;
	}

	public static void setCurrentScene(Scene currentScene) {
		SceneController.currentScene = currentScene;
	}

	public static boolean isShowSkill() {
		return showSkill;
	}

	public static AnimationTimer getAnimation() {
		return animation;
	}

	public static long getTimeAdd() {
		return timeAdd;
	}

	public static void setTimeAdd(long timeAdd) {
		SceneController.timeAdd = timeAdd;
	}
	
	public static MainGame getGame() {
		return game;
	}

	public static void changeScene(Scene other) {
		window.setScene(other);
		setCurrentScene(other);

		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		window.show();
		window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
		window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);

	}

	public static void loadMainMenu() {
		Scene chooseOne = sceneMap.get("Main_Menu");
		window.setTitle("Star Hunter - Main Menu");
		changeScene(chooseOne);

	}


	public static void loadGameOver() {
		final int WIDTH = 600;
		final int HEIGHT = 400;
		final int padding = 75;
		VBox root = new VBox(padding);
		root.setPadding(new Insets(padding));
		root.setStyle("-fx-background-color: black;");
		root.setMinWidth(WIDTH);
		root.setMinHeight(HEIGHT);
		root.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root);

		Text gameover = new Text("Game Over !");
		gameover.setFont(Data.FONT48);
		gameover.setFill(Color.RED);

		Text wave = new Text("You Survive " + GameLogic.getInstance().getWave() + " wave(s)");
		wave.setFont(Data.FONT36);
		wave.setFill(Color.WHITE);

		Button title = new Button("To Main Menu");
		title.setOnAction((ActionEvent Event) -> {
			SceneController.loadMainMenu();
			AudioLoad.playMusic("Main_Menu");
			SFXPlayer.getSfxMap().get("btn").play();
		});
		title.setFont(Data.FONT24);
		title.setTextFill(Color.ORANGE);
		window.setTitle("Star Hunter - You Die");

		root.getChildren().addAll(gameover, wave, title);
		AudioLoad.playMusic("Death");

		changeScene(scene);

	}

	public static void showSkillTree() {
		showSkill = true;
		AnchorPane root = (AnchorPane) currentScene.getRoot();
		SkillTree skills = new SkillTree();
		skills.setLayoutX(Data.SKILLTABLE_LOC_X);
		skills.setLayoutY(Data.SKILLTABLE_LOC_Y);
		lastPause = System.currentTimeMillis();
		root.getChildren().add(skills);
	}

	public static void removeSkillTree() {
		showSkill = false;
		AnchorPane root = (AnchorPane) currentScene.getRoot();
		root.getChildren().remove(root.getChildren().size() - 1);
		lastResume = System.currentTimeMillis();
		timeAdd += lastResume - lastPause;
	}

	public static void loadGame() {
		AnchorPane root = new AnchorPane();
		Scene scene = new Scene(root);
		GameLogic.newGame();
		game = new MainGame();
		GameLogic logic = GameLogic.getInstance();
		root.getChildren().add(game);
		window.setTitle("Star Hunter - Enter the dungeon");

		TextInBar hpbar = game.getHpbar();
		TextInBar xpbar = game.getXpbar();
		hpbar.setLayoutX(50);
		hpbar.setLayoutY(12.5);

		xpbar.setLayoutX(50);
		xpbar.setLayoutY(50);

		root.getChildren().addAll(hpbar, xpbar);

		game.requestFocus();

		changeScene(scene);

		animation = new AnimationTimer() {
			public void handle(long now) {
				if (GameLogic.getInstance().isRunning()) {
					game.paintComponent();
					logic.logicUpdate();
					RenderableHolder.getInstance().update();
				}
			}
		};
		animation.start();

	}



}
