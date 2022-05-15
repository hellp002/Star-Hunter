package gui;

import bgm.AudioLoad;
import bgm.SFXPlayer;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.entity.Player;
import tools.Data;

public class SelectedType extends GridPane {
	private int health, power, armour;
	private double speed, shootSpeed, attackSpeed;
	private Text[] info = new Text[7];
	private final static int PADDING = 10;
	private final static int DES_WIDTH = 120;

	public SelectedType(int health, int power, double speed, double shootSpeed, double attackSpeed, int armour,String text,String description) {
		this.health = health;
		this.power = power;
		this.speed = speed;
		this.armour = armour;
		this.shootSpeed = shootSpeed;
		this.attackSpeed = attackSpeed;
		
		info[0] = new Text("Health : " + this.health);
		info[1] = new Text("Attack : " + this.power);
		info[2] = new Text("Speed : " + this.speed);
		info[3] = new Text("Shot Speed : " + this.shootSpeed);
		info[4] = new Text("Attack Speed : " + this.attackSpeed);
		info[5] = new Text("Armour : " + this.armour);
		info[6] = new Text(description);
		info[6].setWrappingWidth(DES_WIDTH);
		for (int i = 0; i < info.length; i++) {
			info[i].setFill(Color.ORANGE);
			info[i].setFont(Data.FONT16);
			this.add(info[i], 0, i+1);
		}
		info[6].setFill(Color.LIME);
		Text type = new Text("Type : " + text);
		this.add(type, 0, 0);
		GridPane.setHalignment(type, HPos.CENTER);
		this.setPadding(new Insets(PADDING));
		this.setHgap(PADDING);
		this.setVgap(PADDING);
		type.setFont(Data.FONT16);
		type.setFill(Color.ORANGE);
		this.setOnMouseClicked((MouseEvent Event) ->{
			onClickHandle();
		});
	
		this.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		this.setOnMouseEntered((MouseEvent Event) -> {
			this.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
			SFXPlayer.getSfxMap().get("enter").play();
		});
		this.setOnMouseExited((MouseEvent Event) -> {
			this.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		});
		

	}
	
	private void onClickHandle() {
		Player.newPlayer(health, power, speed, shootSpeed, attackSpeed, armour);
		StackPane a = SceneController.getControlMap().get("Main_Menu").getPane();
		a.getChildren().clear();
		SceneController.getControlMap().get("Main_Menu").getButtonZone().setDisable(false);
		SceneController.loadGame();
		AudioLoad.playMusic("Dungeon");
		SFXPlayer.getSfxMap().get("btn").play();
	}
}
