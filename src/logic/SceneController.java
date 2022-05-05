package logic;

import java.util.HashMap;

import gui.MainGame;
import gui.SkillTree;
import gui.TextInBar;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sharedObject.RenderableHolder;
import tools.Data;

public class SceneController {
	
	private static Stage window;
	private static Scene currentScene;
	private static boolean showSkill;
	private static MainGame game;
	private static long lastPause,lastResume,timeAdd;

	
	
	private static HashMap<String,Scene> sceneMap = new HashMap<String,Scene>();
	private static HashMap<String,Parent> rootMap = new HashMap<String,Parent>();
	private static HashMap<String,Controller> controlMap = new HashMap<String,Controller>();
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
	
	public static void changeScene(Scene other) {
		window.setScene(other);
		setCurrentScene(other);

		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		window.show();
		window.setX((primScreenBounds.getWidth() - window.getWidth())/2);
		window.setY((primScreenBounds.getHeight() - window.getHeight())/2);
		
	}
	
	public static void loadMainMenu() {
		Scene chooseOne = sceneMap.get("Main_Menu");
		window.setTitle("Star Hunter - Main Menu");
		changeScene(chooseOne);
		
	}
	
	public static void loadTutorial() {
		Scene chooseOne = sceneMap.get("Tutorial");
		changeScene(chooseOne);
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
		game = MainGame.getInstance();
		GameLogic logic = GameLogic.getInstance();
		root.getChildren().add(game);
		window.setTitle("Star Hunter - Enter the dungeon");
		
		
		TextInBar hpbar = MainGame.getHpbar();
		TextInBar xpbar = MainGame.getXpbar();
		hpbar.setLayoutX(50);
		hpbar.setLayoutY(12.5);
		
		xpbar.setLayoutX(50);
		xpbar.setLayoutY(50);
		
		root.getChildren().addAll(hpbar,xpbar);

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
	public static MainGame getGame() {
		return game;
	}
	
	
	
}