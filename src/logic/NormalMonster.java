package logic;

import java.util.Random;

import bgm.SFXPlayer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tools.Data;
import tools.MathFunction;
import tools.Move;
import tools.SetHealthType;

import tools.Vector2D;

public class NormalMonster extends Enemy {

	private Vector2D vectorToPlayer;
	private long lastShoot;
	private final static Image[] MONSTER_MODEL = new Image[6];

	static {
		MONSTER_MODEL[0] = new Image(ClassLoader.getSystemResource("pic/monster/standstill/gun.png").toString());
		for (int i = 0; i < 4; i++) {
			MONSTER_MODEL[i + 1] = new Image(
					ClassLoader.getSystemResource("pic/monster/gun/gun_" + (i + 1) + ".png").toString());
		}
		MONSTER_MODEL[5] = new Image(ClassLoader.getSystemResource("pic/monster/dead/shoot_dead.png").toString());
	}

	public NormalMonster(int health, int power, double speed, double shootSpeed) {
		super(health, power);
		Random rd = new Random();
		while (true) {
			double x = rd.nextDouble() * Data.WIDTH;
			double y = rd.nextDouble() * Data.HEIGHT;
			double[] playerLocation = Player.getPlayerLocation();
			if (!(((Data.BOARDERLEFT < x) && (x < Data.BOARDERRIGHT))
					&& ((Data.BOARDERTOP < x) && (x < Data.BOARDERDOWN)))) {
				continue;
			}
			if (Math.hypot(x - playerLocation[0], y - playerLocation[1]) >= Data.ACCEPTABLE_RANGE) {
				this.x = x;
				this.y = y;
				break;
			}
		}
		this.shootSpeed = shootSpeed;
		this.speed = speed;
		this.attackSpeed = Data.BASE_AS_NM + rd.nextDouble(Data.RAND_RANGE_AS);
		lastShoot = System.currentTimeMillis() - SceneController.getTimeAdd();
		vectorToPlayer = new Vector2D(0, 0);
		this.dead = false;
		setGiveXP(Data.BASE_XP_NM + (int) (Data.XP_PER_WAVE_NM * GameLogic.getInstance().getWave()));
		this.modelSize = 40;

	}

	public long getLastShoot() {
		return lastShoot;
	}

	@Override
	public void update() {
		if (isDead()) {
			removeCorpse();
			return;
		}
		updateVectorToPlayer();
		if (vectorToPlayer.getMagnitude() != 0) {
			normalShoot();
		}
		move();

	}

	protected void removeCorpse() {
		long now = System.currentTimeMillis() - SceneController.getTimeAdd();
		if (now - lastDead >= Data.CORPSE_DELAY) {
			Random rd = new Random();
			if (rd.nextDouble() < Data.HEALTH_DROP_CHANCE) {
				GameLogic.getInstance().addNewObject(new HealthPotion(this));
			} else if (rd.nextDouble() < Data.SKILL_DROP_CHANCE) {
				GameLogic.getInstance().addNewObject(new SkillPointPotion(this));
			}
			this.deleted = true;
			this.visible = false;
		}
	}

	protected void normalShoot() {
		long now = System.currentTimeMillis() - SceneController.getTimeAdd();
		if ((now - lastShoot) >= (attackSpeed * Data.ATTACKSPEED_MULTIPLYER)) {
			lastShoot = now;
			if (this instanceof ChampionMonster) {
				SFXPlayer.getSfxMap().get("shootBoss").play();
			}
			Vector2D refVector = new Vector2D(vectorToPlayer);
			shootBullet(refVector, MathFunction.CONST);
		}
	}

	public Vector2D getVectorToPlayer() {
		return vectorToPlayer;
	}

	protected void move() {
		if (vectorToPlayer.getX() >= 0) {
			setCurrentMoveType(Move.RIGHT);
		} else {
			setCurrentMoveType(Move.LEFT);
		}
		double x = this.x + (vectorToPlayer.getX() * speed);
		double y = this.y + (vectorToPlayer.getY() * speed);

		if (y < Data.BOARDERTOP + (modelSize / 2)) {
			this.y = Data.BOARDERTOP + (modelSize / 2);
		} else if (y > Data.BOARDERDOWN - modelSize / 2) {
			this.y = Data.BOARDERDOWN - modelSize / 2;
		} else {
			this.y = y;
		}
		if (x < Data.BOARDERLEFT + (modelSize / 2)) {
			this.x = Data.BOARDERLEFT + (modelSize / 2);
		} else if (x > Data.BOARDERRIGHT - (modelSize / 2)) {
			this.x = Data.BOARDERRIGHT - (modelSize / 2);
		} else {
			this.x = x;
		}
		if (movingCounter >= 99) {
			movingCounter = 0;
		}
		movingCounter++;
	}

	protected void updateVectorToPlayer() {
		double[] playerLocation = Player.getPlayerLocation();
		Vector2D refVector;
		if ((Math.abs(playerLocation[0] - x) < Data.ACCEPTABLE_RANGE_NOTUPDATE)
				&& (Math.abs(playerLocation[1] - y) < Data.ACCEPTABLE_RANGE_NOTUPDATE)) {
			refVector = new Vector2D(0, 0);
		} else {
			refVector = new Vector2D(playerLocation[0] - x, playerLocation[1] - y);
		}
		vectorToPlayer = refVector.getUnitVector();

	}


	@Override
	public void setHealth(int health, SetHealthType type) {
		if (type == SetHealthType.DAMAGE) {
			if (health < 0) {
				health = 0;
				giveXPToPlayer();
				this.dead = true;
				lastDead = System.currentTimeMillis() - SceneController.getTimeAdd();
			}
			this.health = health;
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		if (isDead()) {
			if (getCurrentMoveType().equals(Move.RIGHT)) {
				gc.drawImage(MONSTER_MODEL[5], this.x - modelSize / 2, this.y - modelSize / 2, modelSize, modelSize);
			} else {
				gc.drawImage(MONSTER_MODEL[5], 0, 0, MONSTER_MODEL[5].getWidth(), MONSTER_MODEL[5].getHeight(),
						this.x + modelSize / 2, this.y - modelSize / 2, -modelSize, modelSize);
			}

			return;
		}

		if (getCurrentMoveType().equals(Move.RIGHT)) {
			gc.drawImage(MONSTER_MODEL[movingCounter / Data.CHANGE_IMG_COUNTER], this.x - modelSize / 2,
					this.y - modelSize / 2, modelSize, modelSize);
		} else {
			gc.drawImage(MONSTER_MODEL[movingCounter / Data.CHANGE_IMG_COUNTER], 0, 0,
					MONSTER_MODEL[movingCounter / Data.CHANGE_IMG_COUNTER].getWidth(),
					MONSTER_MODEL[movingCounter / Data.CHANGE_IMG_COUNTER].getHeight(), this.x + modelSize / 2,
					this.y - modelSize / 2, -modelSize, modelSize);
		}
	}


}
