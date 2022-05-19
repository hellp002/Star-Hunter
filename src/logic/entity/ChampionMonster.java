package logic.entity;

import java.util.Random;

import bgm.SFXPlayer;
import gui.SceneController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.GameLogic;
import tools.Ability;
import tools.Data;
import tools.MathFunction;
import tools.Move;
import tools.SetHealthType;
import tools.Vector2D;

public class ChampionMonster extends NormalMonster {

	private Ability ability;
	private long[] coolDownTime;
	private final static Image[] CHAMPION_MODEL = new Image[6];
	static {
		CHAMPION_MODEL[0] = new Image(ClassLoader.getSystemResource("pic/monster/standstill/boss.png").toString());
		for (int i = 0; i < 4; i++) {
			CHAMPION_MODEL[i + 1] = new Image(
					ClassLoader.getSystemResource("pic/monster/boss/boss_" + (i + 1) + ".png").toString());
		}
		CHAMPION_MODEL[5] = new Image(ClassLoader.getSystemResource("pic/monster/dead/boss_dead.png").toString());
	}

	public ChampionMonster(int health, int power, double speed, double shootSpeed) {
		super(health, power, speed, shootSpeed);
		ability = randomAbility();
		coolDownTime = new long[3]; // slot 0 - cooldown of around shot // slot 1 - cooldown of decrease your life
									// // slot 2 for normalShot/doubleShot/trippleShot/quadraShot
		for (int i = 0; i < 3; i++) {
			coolDownTime[i] = System.currentTimeMillis() - SceneController.getTimeAdd();
		}
		Random rd = new Random();
		this.attackSpeed = Data.BASE_AS_CM + rd.nextDouble(Data.RAND_RANGE_AS);
		setGiveXP(Data.BASE_XP_CM + Data.XP_PER_WAVE_CM * GameLogic.getInstance().getWave());
		this.z = 10;
		this.modelSize = 60;

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (isDead()) {
			removeCorpse();
			return;
		}
		updateVectorToPlayer();
		if (ability.equals(Ability.REFLECT)) {
			decreaseYourLife();
		}
		shoot();
		move();
		if (ability.equals(Ability.AROUND_SHOT)) {
			autoAroundShot();
		}

	}

	private Ability randomAbility() {
		Random rd = new Random();
		int randomNumber = rd.nextInt(5);
		switch (randomNumber) {
		case 0:
			return Ability.DOUBLE_SHOT;
		case 1:
			return Ability.TRIPPLE_SHOT;
		case 2:
			return Ability.QUARDRA_SHOT;
		case 3:
			return Ability.AROUND_SHOT;
		case 4:
			return Ability.REFLECT;
		default:
			return null;
		}
	}

	private MathFunction randomFunction() {
		Random rd = new Random();
		int randomNumber = rd.nextInt(3);
		switch (randomNumber) {
		case 0:
			return MathFunction.CONST;
		case 1:
			return MathFunction.COS;
		case 2:
			return MathFunction.LINEAR;
		default:
			return null;
		}
	}

	private void decreaseYourLife() {
		long now = System.currentTimeMillis() - SceneController.getTimeAdd();
		if ((now - coolDownTime[1]) >= Data.SECOND) {
			coolDownTime[1] = now;
			super.setHealth(this.getHealth() - (int) ((this.maxHealth * Data.DECREASE_LIFE_PER_SECOND)),
					SetHealthType.DAMAGE);

		}
	}

	private void shoot() {
		if (getVectorToPlayer().getMagnitude() == 0) {
			return;
		}
		switch (ability) {
		case DOUBLE_SHOT:
			doubleShot();
			break;
		case TRIPPLE_SHOT:
			trippleShot();
			break;
		case QUARDRA_SHOT:
			quadraShot();
			break;
		default:
			normalShoot();
		}

	}

	private void doubleShot() {
		MathFunction speed = randomFunction();
		long now = System.currentTimeMillis() - SceneController.getTimeAdd();
		if ((now - coolDownTime[2]) >= ((attackSpeed * Data.ATTACKSPEED_MULTIPLYER_DOUBLE))) {
			coolDownTime[2] = now;
			SFXPlayer.getSfxMap().get("shootBoss").play();
			Vector2D refVector = new Vector2D(this.getVectorToPlayer());
			refVector.setRotatingVector(-30);

			shootBullet(refVector, speed);

			refVector.setRotatingVector(60);

			shootBullet(refVector, speed);

		}

	}

	private void trippleShot() {
		MathFunction speed = randomFunction();
		long now = System.currentTimeMillis() - SceneController.getTimeAdd();
		if ((now - coolDownTime[2]) >= ((attackSpeed * Data.ATTACKSPEED_MULTIPLYER_TRIPPLE))) {
			coolDownTime[2] = now;
			Vector2D refVector = new Vector2D(this.getVectorToPlayer());
			refVector.setRotatingVector(-60);
			SFXPlayer.getSfxMap().get("shootBoss").play();
			shootBullet(refVector, speed);
			refVector.setRotatingVector(60);

			shootBullet(refVector, speed);
			refVector.setRotatingVector(60);

			shootBullet(refVector, speed);
		}
	}

	private void quadraShot() {
		MathFunction speed = randomFunction();
		long now = System.currentTimeMillis() - SceneController.getTimeAdd();
		if ((now - coolDownTime[2]) >= ((attackSpeed * Data.ATTACKSPEED_MULTIPLYER_QUADRA))) {
			coolDownTime[2] = now;
			Vector2D refVector = new Vector2D(this.getVectorToPlayer());
			refVector.setRotatingVector(-60);
			SFXPlayer.getSfxMap().get("shootBoss").play();
			shootBullet(refVector, speed);
			refVector.setRotatingVector(30);

			shootBullet(refVector, speed);
			refVector.setRotatingVector(60);

			shootBullet(refVector, speed);
			refVector.setRotatingVector(30);

			shootBullet(refVector, speed);
		}
	}

	private void aroundShot() {
		MathFunction speed = randomFunction();
		final int ROTAING_VECTOR = 30;
		final int CIRCLE = 360;
		Vector2D refVector = new Vector2D(this.getVectorToPlayer());
		for (int i = 0; i < CIRCLE / ROTAING_VECTOR; i++) {
			refVector.setRotatingVector(ROTAING_VECTOR);
			
			shootBullet(refVector, speed);
		}
	}

	private void autoAroundShot() {
		long now = System.currentTimeMillis() - SceneController.getTimeAdd();
		if ((now - coolDownTime[0]) >= Data.AROUNDSHOT_CD) {
			coolDownTime[0] = now;
			SFXPlayer.getSfxMap().get("shootBoss").play();
			aroundShot();
		}
	}

	@Override
	public void draw(GraphicsContext gc) {

		if (isDead()) {
			if (getCurrentMoveType().equals(Move.RIGHT)) {
				gc.drawImage(CHAMPION_MODEL[5], this.x - modelSize / 2,
						this.y - modelSize / 2, modelSize, modelSize);
			} else {
				gc.drawImage(CHAMPION_MODEL[5], 0, 0,
						CHAMPION_MODEL[5].getWidth(),
						CHAMPION_MODEL[5].getHeight(), this.x + modelSize / 2,
						this.y - modelSize / 2, -modelSize, modelSize);
			}
			return;
		}
		if (getCurrentMoveType().equals(Move.RIGHT)) {
			gc.drawImage(CHAMPION_MODEL[movingCounter / Data.CHANGE_IMG_COUNTER], this.x - modelSize / 2,
					this.y - modelSize / 2, modelSize, modelSize);
		} else {
			gc.drawImage(CHAMPION_MODEL[movingCounter / Data.CHANGE_IMG_COUNTER], 0, 0,
					CHAMPION_MODEL[movingCounter / Data.CHANGE_IMG_COUNTER].getWidth(),
					CHAMPION_MODEL[movingCounter / Data.CHANGE_IMG_COUNTER].getHeight(), this.x + modelSize / 2,
					this.y - modelSize / 2, -modelSize, modelSize);
		}
	}


	@Override
	public void setHealth(int health, SetHealthType type) {
		if (type == SetHealthType.DAMAGE) {
			if (ability.equals(Ability.REFLECT)) {
				aroundShot();
				super.setHealth(health, type);

			} else {
				super.setHealth(health, type);
			}
		}

	}

}
