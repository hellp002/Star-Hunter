package gui;

import bgm.SFXPlayer;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.entity.Player;
import logic.exception.SkillRequirementException;
import tools.Ability;
import tools.Data;
import tools.SetHealthType;

public class SkillTree extends GridPane{
	private static final Image DOUBLE = new Image(ClassLoader.getSystemResource("pic/Skills/double.png").toString());
	private static final Image TRIPPLE = new Image(ClassLoader.getSystemResource("pic/Skills/tripple.png").toString());
	private static final Image QUADRA = new Image(ClassLoader.getSystemResource("pic/Skills/quadra.png").toString());
	private static final Image ATTACK_SPEED = new Image(ClassLoader.getSystemResource("pic/Skills/AS.png").toString());
	private static final Image ARMOUR = new Image(ClassLoader.getSystemResource("pic/Skills/Armour.png").toString());
	private static final Image HEALTH = new Image(ClassLoader.getSystemResource("pic/Skills/Health.png").toString());
	private static final Image POWER = new Image(ClassLoader.getSystemResource("pic/Skills/Power.png").toString());
	private static final Image AROUNDSHOT = new Image(ClassLoader.getSystemResource("pic/Skills/AroundShot.png").toString());
	private SkillLine doubleShot;
	private SkillLine trippleShot;
	private SkillLine quadraShot;
	private SkillLine attackSpeed;
	private SkillLine armour;
	private SkillLine health;
	private SkillLine power;
	private SkillLine aroundShot;
	private Text result;
	private Text skillPoint;
	
	public SkillTree() {
		this.setHgap(Data.ICONSIZE);
		doubleShot = new SkillLine(DOUBLE,"Double Shot");
		trippleShot = new SkillLine(TRIPPLE,"Tripple Shot");
		quadraShot = new SkillLine(QUADRA,"Quadra Shot");
		attackSpeed = new SkillLine(ATTACK_SPEED,"Attack Speed");
		armour = new SkillLine(ARMOUR,"Armour");
		health = new SkillLine(HEALTH,"Health");
		power = new SkillLine(POWER,"Attack");
		aroundShot = new SkillLine(AROUNDSHOT,"Around Shot");
		result = new Text();
		skillPoint = new Text("Point : " + Player.getInstance().getSkillPoint());
		skillPoint.setFont(Data.FONT24);
		skillPoint.setFill(Color.ORANGE);
		result.setFont(Data.FONT24);
		result.setFill(Color.ORANGE);
		this.setPadding(new Insets(Data.ICONSIZE));
		this.add(doubleShot, 0, 1);
		this.add(trippleShot, 1, 1);
		this.add(quadraShot, 2, 1);
		this.add(attackSpeed, 3, 1);
		this.add(armour, 0, 2);
		this.add(health, 1, 2);
		this.add(power, 2, 2);
		this.add(aroundShot, 3, 2);
		this.add(result, 0, 0);
		this.add(skillPoint, 3, 0);
		GridPane.setColumnSpan(result, 3);
		GridPane.setHalignment(result, HPos.CENTER);
		GridPane.setHalignment(skillPoint, HPos.CENTER);
		this.setStyle("-fx-background-image: url('pic/overlay/skilltable.png');"
				+ "-fx-background-size : 100% 100%;");
		initializeAll();
	}
	
	
	private void initializeAll() {
		doubleShot.getSubmit().setOnAction((ActionEvent Event) -> {
			try {
				doubleShotHandle();
				result.setText("You use the skill point");
			} catch (SkillRequirementException e) {
				result.setText(e.toString());
				SFXPlayer.getSfxMap().get("Error").play();
			}
			SceneController.getGame().requestFocus();
		});
		trippleShot.getSubmit().setOnAction((ActionEvent Event) -> {
			try {
				trippleShotHandle();
				result.setText("You use the skill point");
			} catch (SkillRequirementException e) {
				result.setText(e.toString());
				SFXPlayer.getSfxMap().get("Error").play();
			}
			SceneController.getGame().requestFocus();
		});
		quadraShot.getSubmit().setOnAction((ActionEvent Event) -> {
			try {
				quadShotHandle();
				result.setText("You use the skill point");
			} catch (SkillRequirementException e) {
				result.setText(e.toString());
				SFXPlayer.getSfxMap().get("Error").play();
			}
			SceneController.getGame().requestFocus();
		});
		
		attackSpeed.getSubmit().setOnAction((ActionEvent Event) -> {
			try {
				attackSpeedHandle();
				result.setText("You use the skill point");
			} catch (SkillRequirementException e) {
				result.setText(e.toString());
				SFXPlayer.getSfxMap().get("Error").play();
			}
			SceneController.getGame().requestFocus();
		});
		
		armour.getSubmit().setOnAction((ActionEvent Event) -> {
			try {
				armourHandle();
				result.setText("You use the skill point");
			} catch (SkillRequirementException e) {
				result.setText(e.toString());
				SFXPlayer.getSfxMap().get("Error").play();
			}
			SceneController.getGame().requestFocus();
		});
		
		health.getSubmit().setOnAction((ActionEvent Event) -> {
			try {
				healthHandle();
				result.setText("You use the skill point");
			} catch (SkillRequirementException e) {
				result.setText(e.toString());
				SFXPlayer.getSfxMap().get("Error").play();
			}
			SceneController.getGame().requestFocus();
		});
		power.getSubmit().setOnAction((ActionEvent Event) -> {
			try {
				powerHandle();
				result.setText("You use the skill point");
			} catch (SkillRequirementException e) {
				result.setText(e.toString());
				SFXPlayer.getSfxMap().get("Error").play();
			}
			SceneController.getGame().requestFocus();
		});
		
		aroundShot.getSubmit().setOnAction((ActionEvent Event) -> {
			try {
				aroundShotHandle();
				result.setText("You use the skill point");
			} catch (SkillRequirementException e) {
				result.setText(e.toString());
				SFXPlayer.getSfxMap().get("Error").play();
			}
			SceneController.getGame().requestFocus();
		});
		
		
	}
	
	private void doubleShotHandle() throws SkillRequirementException {
		Player player = Player.getInstance();
		if (player.getAllAbility().contains(Ability.DOUBLE_SHOT)) {
			throw new SkillRequirementException("You already get this ability");
		}
		final int REQUIRE_LEVEL = 3;
		if (player.getLevel() < REQUIRE_LEVEL) {
			throw new SkillRequirementException("Need level 3 to upgrade this");
		}
		
		final int USED_POINT = 1;
		player.useSkillPoint(USED_POINT);
		player.getAllAbility().add(Ability.DOUBLE_SHOT);
		skillPoint.setText("Point : " + player.getSkillPoint());
		SFXPlayer.getSfxMap().get("Successful").play();
	}
	
	private void trippleShotHandle() throws SkillRequirementException {
		Player player = Player.getInstance();
		if (player.getAllAbility().contains(Ability.TRIPPLE_SHOT)) {
			throw new SkillRequirementException("You already get this ability");
		}
		final int REQUIRE_LEVEL = 6;
		if (player.getLevel() < REQUIRE_LEVEL) {
			throw new SkillRequirementException("Need level 6 to upgrade this");
		}
		if (!player.getAllAbility().contains(Ability.DOUBLE_SHOT)) {
			throw new SkillRequirementException("Need to get Double Shot before get this ability");
		}
		final int USED_POINT = 2;
		player.useSkillPoint(USED_POINT);
		player.getAllAbility().add(Ability.TRIPPLE_SHOT);
		skillPoint.setText("Point : " + player.getSkillPoint());
		SFXPlayer.getSfxMap().get("Successful").play();
	}
	
	private void quadShotHandle() throws SkillRequirementException {
		Player player = Player.getInstance();
		if (player.getAllAbility().contains(Ability.QUARDRA_SHOT)) {
			throw new SkillRequirementException("You already get this ability");
		}
		final int REQUIRE_LEVEL = 9;
		if (player.getLevel() < REQUIRE_LEVEL) {
			throw new SkillRequirementException("Need level 9 to upgrade this");
		} if (!player.getAllAbility().contains(Ability.TRIPPLE_SHOT)) {
			throw new SkillRequirementException("Need to get Tripple Shot before get this ability");
		}
		final int USED_POINT = 3;
		player.useSkillPoint(USED_POINT);
		player.getAllAbility().add(Ability.QUARDRA_SHOT);
		skillPoint.setText("Point : " + player.getSkillPoint());
		SFXPlayer.getSfxMap().get("Successful").play();
	}
	
	private void attackSpeedHandle() throws SkillRequirementException {
		Player player = Player.getInstance();
		if (player.getAttackSpeed() <= Data.ATTACKSPEED_UPGRADE_CAP) {
			throw new SkillRequirementException("Your attack speed exceeds the limit");
		}
		final int USED_POINT = 2;
		player.useSkillPoint(USED_POINT);
		player.setAttackSpeed(player.getAttackSpeed() - Data.ATTACKSPEED_UP);
		skillPoint.setText("Point : " + player.getSkillPoint());
		SFXPlayer.getSfxMap().get("Successful").play();
		
	}
	
	private void armourHandle() throws SkillRequirementException {
		Player player = Player.getInstance();
		final int USED_POINT = 1;
		player.useSkillPoint(USED_POINT);
		player.setMaxArmour(player.getMaxArmour() + Data.ARMOUR_UP);
		player.setArmour(player.getArmour() + Data.ARMOUR_UP);
		skillPoint.setText("Point : " + player.getSkillPoint());
		SFXPlayer.getSfxMap().get("Successful").play();
	}
	
	private void healthHandle() throws SkillRequirementException {
		Player player = Player.getInstance();
		final int USED_POINT = 1;
		player.useSkillPoint(USED_POINT);
		player.setMaxHealth(player.getMaxHealth() + Data.HEALTH_UP);
		player.setHealth(player.getHealth() + Data.HEALTH_UP,SetHealthType.HEAL);
		skillPoint.setText("Point : " + player.getSkillPoint());
		SFXPlayer.getSfxMap().get("Successful").play();
	}
	
	private void powerHandle() throws SkillRequirementException {
		Player player = Player.getInstance();
		final int USED_POINT = 1;
		player.useSkillPoint(USED_POINT);
		player.setPower(player.getPower() + Data.POWER_UP);
		skillPoint.setText("Point : " + player.getSkillPoint());
		SFXPlayer.getSfxMap().get("Successful").play();
	}
	
	private void aroundShotHandle() throws SkillRequirementException {
		Player player = Player.getInstance();
		if (player.getAllAbility().contains(Ability.AROUND_SHOT)) {
			throw new SkillRequirementException("You already get this ability");
		}
		final int REQUIRE_LEVEL = 5;
		if (player.getLevel() < REQUIRE_LEVEL) {
			throw new SkillRequirementException("Need level 5 to upgrade this");
		}
		final int USED_POINT = 3;
		player.useSkillPoint(USED_POINT);
		player.getAllAbility().add(Ability.AROUND_SHOT);
		skillPoint.setText("Point : " + player.getSkillPoint());
		SFXPlayer.getSfxMap().get("Successful").play();
	}
	 
	
	
}
