package logic;

import java.util.ArrayList;

import bgm.AudioLoad;
import bgm.SFXPlayer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import tools.Ability;
import tools.Data;
import tools.FireDirection;
import tools.InputUtility;
import tools.MathFunction;
import tools.Move;
import tools.SetHealthType;
import tools.Vector2D;

public class Player extends Entity {

	private static double[] playerLocation = new double[2];

	private long[] lastDamageTaken;
	private boolean immune, maxLevel, fire;

	private ArrayList<Ability> allAbility;
	private Vector2D fireDirection, movingVector, rememberVector;
	private int flashCount, level, bulletCount;
	private static Player instance;
	private int xp, maxXP, armour, maxArmour, skillPoint;
	private static final int MAXLEVEL = Data.LEVELTABLE.length + 1;
	private static Image[] playerModel1 = new Image[6];
	private static Image gun = new Image(ClassLoader.getSystemResource("pic/weapon/pistol.png").toString());
	private FireDirection firePointer;
	private long[] coolDownTime;
	

	static {
		playerModel1[0] = new Image(ClassLoader.getSystemResource("pic/player/standstill/knight_0.png").toString());
		for (int i = 0; i < 4; i++) {
			playerModel1[(i + 1)] = new Image(
					ClassLoader.getSystemResource("pic/player/knight/knight_" + (i + 1) + ".png").toString());
		}
		playerModel1[5] = new Image(ClassLoader.getSystemResource("pic/player/dead/knight_dead.png").toString());
	}

	public static Player getInstance() {
		return instance;
	}

	public static void newPlayer() {
		instance = new Player(100, 99, 3, 5, 0.8, 15);
	}

	public Player(int health, int power, double speed, double shootSpeed, double attackSpeed, int armour) {
		super(health, power);
		dead = false;
		immune = false;
		this.radius = 20;
		this.speed = speed;
		lastDamageTaken = new long[2];
		for (int i = 0; i < 2; i++) {
			lastDamageTaken[i] = System.currentTimeMillis() - SceneController.getTimeAdd(); // slot 0 for damage health
																							// slot 1 for damage armour
		}
		this.shootSpeed = shootSpeed;
		this.attackSpeed = attackSpeed;
		allAbility = new ArrayList<Ability>();
		fireDirection = new Vector2D();
		movingVector = new Vector2D();
		this.radius = 10;
		this.x = Data.WIDTH / 2;
		this.y = Data.HEIGHT / 2;
		playerLocation[0] = this.x;
		playerLocation[1] = this.y;
		flashCount = 0;
		maxHealth = health;
		skillPoint = 0;
		this.xp = 0;
		this.z = 3;
		this.level = 1;
		this.armour = armour;
		this.maxArmour = armour;
		this.maxXP = Data.LEVELTABLE[level - 1];
		movingCounter = 0;
		maxLevel = false;
		coolDownTime = new long[4]; // slot 0 cooldown of firing //slot 1 cooldown of around shot
		for (int i = 0; i < 4; i++) {  // slot 2 for regen armour // slot 3 for multishot
			coolDownTime[i] = System.currentTimeMillis() - SceneController.getTimeAdd();
		}
		bulletCount = 0;
		this.modelSize = 40;
	}

	public int getSkillPoint() {
		return skillPoint;
	}

	public void useSkillPoint(int usedSkillPoint) throws SkillRequirementException {
		if (skillPoint - usedSkillPoint < 0) {
			throw new SkillRequirementException("You need atleast " + usedSkillPoint + " to get this ability");
		}
		setSkillPoint(skillPoint - usedSkillPoint);
	}

	public void setMaxArmour(int maxArmour) {
		this.maxArmour = maxArmour;
	}

	public void setSkillPoint(int skillPoint) {
		this.skillPoint = skillPoint;
	}

	public ArrayList<Ability> getAllAbility() {
		return allAbility;
	}

	public static double[] getPlayerLocation() {
		return playerLocation;
	}

	public boolean isMaxLevel() {
		return maxLevel;
	}

	public int getLevel() {
		return level;
	}

	@Override
	public void update() {
		if (dead) {
			long now = System.currentTimeMillis() - SceneController.getTimeAdd();
			movingCounter = 0;
			if (now - lastDead >= Data.CORPSE_DELAY) {
				
				SceneController.getAnimation().stop();
				SceneController.loadMainMenu();
				AudioLoad.playMusic("Main_Menu");
			}
			return;
		}
		if (xp >= maxXP) {
			levelUP();
		}

		if (allAbility.contains(Ability.AROUND_SHOT)) {
			aroundShot();
		}
		regainArmour();
		if (InputUtility.getKeyPressed().size() > 0) {
			updateFireDirection();
			updateMovingVector();
		}
		if (rememberVector != null) {
			fireChangeDirection();
		}

		if (movingVector.getMagnitude() != 0) {
			if (movingVector.getX() >= 0) {
				setCurrentMoveType(Move.RIGHT);
			} else {
				setCurrentMoveType(Move.LEFT);
			}
			move();
			movingCounter++;
		} else {
			movingCounter = 0;
		}
		if (movingCounter >= 99) {
			movingCounter = 0;
		}
		if (fireDirection.getMagnitude() != 0 || fire) {
			shoot();
		}
		flashing();

		fireDirection.reset();

	}

	private void fireChangeDirection() {
		if (rememberVector.getX() > 0) {
			firePointer = FireDirection.RIGHT;
		} else if (rememberVector.getX() < 0) {
			firePointer = FireDirection.LEFT;
		} else if (rememberVector.getY() > 0) {
			firePointer = FireDirection.DOWN;
		} else if (rememberVector.getY() < 0) {
			firePointer = FireDirection.UP;
		}
	}

	public int getArmour() {
		return armour;
	}

	public int getMaxArmour() {
		return maxArmour;
	}

	public int getXp() {
		return xp;
	}

	public int getMaxXP() {
		return maxXP;
	}

	@Override
	public int getZ() {
		return z;
	}

	@Override
	public void draw(GraphicsContext gc) {
		if (dead) {
			gc.drawImage(playerModel1[5], this.x - modelSize / 2, this.y - modelSize / 2, modelSize, modelSize);
			return;
		}

		if (getCurrentMoveType().equals(Move.RIGHT)) {
			gc.drawImage(playerModel1[movingCounter / Data.CHANGE_IMG_COUNTER], this.x - modelSize / 2,
					this.y - modelSize / 2, modelSize, modelSize);
		} else {
			gc.drawImage(playerModel1[movingCounter / Data.CHANGE_IMG_COUNTER], 0, 0,
					playerModel1[movingCounter / Data.CHANGE_IMG_COUNTER].getWidth(),
					playerModel1[movingCounter / Data.CHANGE_IMG_COUNTER].getHeight(), this.x + modelSize / 2,
					this.y - modelSize / 2, -modelSize, modelSize);
		}
		if (firePointer == null) {
			return;
		}
		gc.save();
		switch (firePointer) {
		case UP:
			gc.rotate(-90);
			gc.drawImage(gun, -this.y, this.x, Data.GUNSIZE, Data.GUNSIZE);
			break;
		case DOWN:
			gc.rotate(90);
			gc.drawImage(gun, y, -this.x - Data.GUNSIZE, Data.GUNSIZE, Data.GUNSIZE);
			break;
		case RIGHT:
			gc.drawImage(gun, this.x, this.y, Data.GUNSIZE, Data.GUNSIZE);
			break;

		case LEFT:
			gc.drawImage(gun, 0, 0, gun.getWidth(), gun.getHeight(), this.x, this.y, -Data.GUNSIZE, Data.GUNSIZE);
			break;
		default:
			break;
		}
		gc.restore();

	}

	@Override
	public void onCollision(GameObject other) {
		if (allAbility.contains(Ability.REFLECT) && (other instanceof Enemy)) {
			Enemy enemy = (Enemy) other;
			enemy.setHealth(enemy.getHealth() - enemy.getPower(), SetHealthType.DAMAGE);
		}

	}

	private void shoot() {
		if (allAbility.contains(Ability.QUARDRA_SHOT)) {
			quadraShot();
		} else if (allAbility.contains(Ability.TRIPPLE_SHOT)) {
			trippleShot();
		} else if (allAbility.contains(Ability.DOUBLE_SHOT)) {
			doubleShot();
		} else {
			normalShot();
		}
	}

	private void normalShot() {
		long now = System.currentTimeMillis() - SceneController.getTimeAdd();
		if (now - coolDownTime[0] >= Math.max((this.attackSpeed * Data.ATTACKSPEED_MULTIPLYER), Data.ATTACKSPEED_CAP)) {
			coolDownTime[0] = now;
			fire = true;
			SFXPlayer.getSfxMap().get("shoot").play();
			Vector2D refVector = new Vector2D(this.fireDirection);
			shootBullet(refVector, MathFunction.CONST);
			fire = false;
		}
	}

	private void doubleShot() {
		long now = System.currentTimeMillis() - SceneController.getTimeAdd();
		if (now - coolDownTime[0] >= (Math.max((this.attackSpeed * Data.ATTACKSPEED_MULTIPLYER_DOUBLE),
				Data.ATTACKSPEED_CAP)) || fire) {
			coolDownTime[0] = now;
			multiFire(2);
		}
	}

	private void multiFire(int bulletCount) {
		long now = System.currentTimeMillis() - SceneController.getTimeAdd();
		if (!fire) {
			rememberVector = new Vector2D(this.fireDirection);
		}
		fire = true;
		if (now - coolDownTime[3] >= (Data.SHOT_DELAY)) {
			coolDownTime[3] = now;
			SFXPlayer.getSfxMap().get("shoot").play();
			Vector2D refVector = rememberVector;
			shootBullet(refVector, MathFunction.CONST);
			this.bulletCount++;
		}
		if (this.bulletCount == bulletCount) {
			rememberVector = null;
			fire = false;
			this.bulletCount = 0;
		}
	}

	private void trippleShot() {
		long now = System.currentTimeMillis() - SceneController.getTimeAdd();
		if (now - coolDownTime[0] >= (Math.max((this.attackSpeed * Data.ATTACKSPEED_MULTIPLYER_DOUBLE),
				Data.ATTACKSPEED_CAP)) || fire) {
			coolDownTime[0] = now;
			multiFire(3);
		}
	}

	private void quadraShot() {
		long now = System.currentTimeMillis() - SceneController.getTimeAdd();
		if (now - coolDownTime[0] >= (Math.max((this.attackSpeed * Data.ATTACKSPEED_MULTIPLYER_DOUBLE),
				Data.ATTACKSPEED_CAP)) || fire) {
			coolDownTime[0] = now;
			multiFire(4);
		}
	}

	private void aroundShot() {
		long now = System.currentTimeMillis() - SceneController.getTimeAdd();
		if (now - coolDownTime[1] >= Data.AROUNDSHOT_CD) {
			coolDownTime[1] = now;
			Vector2D refVector = new Vector2D(1, 0);
			final int ROTAING_VECTOR = 30;
			final int CIRCLE = 360;
			for (int i = 0; i < CIRCLE / ROTAING_VECTOR; i++) {
				refVector.setRotatingVector(ROTAING_VECTOR);
				shootBullet(refVector, MathFunction.CONST);
			}

		}
	}

	private void move() {
		movingVector = movingVector.getUnitVector();
		double x = this.x + (movingVector.getX() * speed);
		double y = this.y + (movingVector.getY() * speed);
		if (y < Data.BOARDERTOP + (modelSize / 2)) {
			this.y = Data.BOARDERTOP + (modelSize / 2);
		} else if (y > Data.BOARDERDOWN - (modelSize / 2)) {
			this.y = Data.BOARDERDOWN - (modelSize / 2);
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
		playerLocation[0] = this.x;
		playerLocation[1] = this.y;
		movingVector.reset();
	}

	private void updateMovingVector() {
		ArrayList<KeyCode> kb = InputUtility.getKeyPressed();
		if (kb.contains(KeyCode.A)) {
			movingVector.setX(movingVector.getX() - 1);
		}
		if (kb.contains(KeyCode.D)) {
			movingVector.setX(movingVector.getX() + 1);
		}
		if (kb.contains(KeyCode.W)) {
			movingVector.setY(movingVector.getY() - 1);
		}
		if (kb.contains(KeyCode.S)) {
			movingVector.setY(movingVector.getY() + 1);
		}
	}

	private void updateFireDirection() {
		ArrayList<KeyCode> kb = InputUtility.getKeyPressed();
		if (kb.contains(KeyCode.UP) || kb.contains(KeyCode.DOWN) || kb.contains(KeyCode.LEFT)
				|| kb.contains(KeyCode.RIGHT)) {
			for (int i = kb.size() - 1; i >= 0; i--) {
				KeyCode lastkb = kb.get(i);
				switch (lastkb) {
				case UP:
					fireDirection = new Vector2D(0, -1);
					firePointer = FireDirection.UP;
					return;
				case DOWN:
					fireDirection = new Vector2D(0, 1);
					firePointer = FireDirection.DOWN;
					return;
				case LEFT:
					fireDirection = new Vector2D(-1, 0);
					firePointer = FireDirection.LEFT;
					return;
				case RIGHT:
					fireDirection = new Vector2D(1, 0);
					firePointer = FireDirection.RIGHT;
					return;
				}
			}
		}
	}

	private void levelUP() {
		SFXPlayer.getSfxMap().get("levelUp").play();
		level++;
		skillPoint++;
		xp = 0;
		if (level == MAXLEVEL) {
			maxLevel = true;
			xp = 999999998;
			maxXP = 999999999;
		} else {
			maxXP = Data.LEVELTABLE[level - 1];
		}

	}

	public void flashing() {
		if (!isDead() && isImmune()) {
			long now = System.currentTimeMillis() - SceneController.getTimeAdd();
			if ((now - lastDamageTaken[0]) >= Data.IMUNITYFRAME) {
				flashCount = 0;
				immune = false;
				visible = true;
			} else {
				if (flashCount == 0) {
					flashCount = 10;
				} else {
					visible = flashCount <= 5;
					flashCount--;
				}

			}
		}
	}

	public boolean isImmune() {
		return immune;
	}

	public void setXP(int xp) {
		if (level == MAXLEVEL) {
			return;
		}
		if (xp > maxXP) {
			xp = maxXP;
		}
		this.xp = xp;
	}

	private void regainArmour() {
		long now = System.currentTimeMillis() - SceneController.getTimeAdd();
		if (now - lastDamageTaken[1] >= Data.ARMOUR_COOLDOWN) {
			if (now - coolDownTime[2] >= Data.ARMOUR_COOLDOWN) {
				coolDownTime[2] = now;
				if (maxArmour > 10) {
					setArmour(armour + (int) Math.ceil(maxArmour*Data.PERCENTAGE_REGEN_ARMOUR));
				} else {
					setArmour(armour + 1);
				}
			}
		}
	}

	public void takeDamge(int damage) {
		if (armour > 0) {
			long now = System.currentTimeMillis() - SceneController.getTimeAdd();
			lastDamageTaken[1] = now;
			setArmour(armour - damage);

		} else {
			setHealth(health - damage, SetHealthType.DAMAGE);
		}
	}

	public void setArmour(int armour) {
		if (armour > maxArmour) {
			armour = maxArmour;
		}
		if (armour < 0) {
			armour = 0;
		}
		this.armour = armour;

	}

	@Override
	public void setHealth(int health, SetHealthType type) {
		if (isDead()) {
			return;
		}
		if (type == SetHealthType.DAMAGE) {
			long now = System.currentTimeMillis() - SceneController.getTimeAdd();
			if (now - lastDamageTaken[0] >= Data.IMUNITYFRAME) {
				lastDamageTaken[0] = now;
				if (health <= 0) {
					health = 0;
					this.lastDead = now;
					this.dead = true;
				}
				immune = true;
				this.health = health;
			}
		} else if (type == SetHealthType.HEAL) {
			if (health > maxHealth) {
				health = maxHealth;
			}
			this.health = health;
		}

	}

	@Override
	public Rectangle getHitBox() {
		return new Rectangle(this.x - modelSize / 2, this.y - modelSize / 2, modelSize, modelSize);
	}

	@Override
	public double getY() {
		return y;
	}

}
