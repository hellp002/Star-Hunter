package gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import logic.GameLogic;
import logic.Player;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;
import tools.Data;
import tools.InputUtility;

public class MainGame extends Canvas {

	private static MainGame instance = new MainGame();
	private static TextInBar hpbar = new TextInBar();
	private static TextInBar xpbar = new TextInBar();

	public static TextInBar getHpbar() {
		return hpbar;
	}

	public static TextInBar getXpbar() {
		return xpbar;
	}

	public MainGame() {
		super(Data.WIDTH, Data.HEIGHT);
		this.setVisible(true);
		addListerner();

	}

	public static MainGame getInstance() {
		return instance;
	}

	private void addListerner() {
		this.setOnKeyPressed((KeyEvent event) -> {
			InputUtility.setKeyPressed(event.getCode(), true);
		});

		this.setOnKeyReleased((KeyEvent event) -> {
			InputUtility.setKeyPressed(event.getCode(), false);
		});
	}

	public void paintComponent() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, Data.WIDTH, Data.HEIGHT);
		drawInfo();
		for (IRenderable entity : RenderableHolder.getInstance().getEntities()) {
			// System.out.println(entity.getZ());
			if (entity.isVisible() && !entity.isDeleted()) {
				entity.draw(gc);
			}
		}
	}

	private void drawInfo() {
		Player information = Player.getInstance();
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setStroke(Color.WHITE);
		final int width = 2;
		final double[][] COORDINATE_BAR = { { 50, 12.5 }, { 50, 50 } };
		final double[] barSize = { 200, 25 };
		gc.setLineWidth(width);
		gc.strokeRect(COORDINATE_BAR[0][0], COORDINATE_BAR[0][1], barSize[0], barSize[1]);
		gc.strokeRect(COORDINATE_BAR[1][0], COORDINATE_BAR[1][1], barSize[0], barSize[1]);

		gc.setFill(Color.RED);
		gc.fillRect(COORDINATE_BAR[0][0] + width / 2, COORDINATE_BAR[0][1] + width / 2,
				(barSize[0] - width / 2) * (information.getHealth() / (double) information.getMaxHealth()),
				barSize[1] - width / 2);

		gc.setFill(Color.GRAY);
		gc.fillRect(COORDINATE_BAR[0][0] + width / 2, COORDINATE_BAR[0][1] + width / 2,
				(barSize[0] - width / 2) * (information.getArmour() / (double) information.getMaxArmour()),
				barSize[1] - width / 2);

		gc.setFill(Color.YELLOW);
		gc.fillRect(COORDINATE_BAR[1][0] + width / 2, COORDINATE_BAR[1][1] + width / 2,
				(barSize[0] - width / 2) * (information.getXp() / (double) information.getMaxXP()),
				barSize[1] - width / 2);

		if (information.getArmour() > 0) {
			hpbar.setText(information.getArmour() + " / " + information.getMaxArmour());
		} else {
			hpbar.setText(information.getHealth() + " / " + information.getMaxHealth());
		}
		if (information.isMaxLevel()) {
			xpbar.setText("You are Max Level");
		} else {
			xpbar.setText(information.getXp() + " / " + information.getMaxXP());
		}

		gc.setFill(Color.WHITE);
		gc.setFont(Data.FONT24);
		final int padding = 25;
		gc.fillText("LV : " + information.getLevel(), COORDINATE_BAR[1][0] + barSize[0] + padding,
				COORDINATE_BAR[1][1] + barSize[1]);
		final int textLength = 75;

		gc.setFill(Color.ORANGE);
		if (information.getSkillPoint() > 0) {
			gc.fillText("Skill Point : " + information.getSkillPoint() + " Hold [E] to get Your Skill!",
					COORDINATE_BAR[1][0] + barSize[0] + padding + textLength + padding,
					COORDINATE_BAR[1][1] + barSize[1]);
		} else {
			gc.fillText("Skill Point : " + information.getSkillPoint(),
					COORDINATE_BAR[1][0] + barSize[0] + padding + textLength + padding,
					COORDINATE_BAR[1][1] + barSize[1]);
		}

		gc.setFill(Color.RED);
		final int TEXTLENGTH = 75;
		gc.fillText("Wave : " + GameLogic.getInstance().getWave(), COORDINATE_BAR[1][0] + barSize[0] + padding,
				COORDINATE_BAR[0][1] + barSize[1]);
		gc.setFill(Color.ORANGE);
		gc.fillText(
				"Attack : " + information.getPower() + "\tAttackSpeed : " + String.format("%.1f",information.getAttackSpeed())
						+ "\tShot Speed : " + information.getShootSpeed() + "\tSpeed : " + information.getSpeed(),
				COORDINATE_BAR[1][0] + barSize[0] + padding + TEXTLENGTH + padding, COORDINATE_BAR[0][1] + barSize[1]);
		gc.drawImage(Data.HEART, 12.5, COORDINATE_BAR[0][1], Data.ICONSIZE, Data.ICONSIZE);
		gc.drawImage(Data.LEVEL, 12.5, COORDINATE_BAR[1][1], Data.ICONSIZE, Data.ICONSIZE);

	}
}
