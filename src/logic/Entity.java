package logic;


import javafx.scene.shape.Rectangle;
import tools.MathFunction;
import tools.Move;
import tools.SetHealthType;
import tools.States;
import tools.Vector2D;

public abstract class Entity extends GameObject{
	
	private int power;
	protected double shootSpeed,attackSpeed,speed;
	protected int health,maxHealth,movingCounter,modelSize;
	

	protected long lastDead;
	protected int z;
	protected boolean dead;
	
	private Move currentMoveType;
	

	public Entity(int health,int power){
		visible = true;
		deleted = false;
		this.health = health;
		setPower(power);
		this.z = 5;
		setCurrentMoveType(Move.RIGHT);
	}
	
	public int getHealth() {
		return health;
	}

	public abstract void setHealth(int health,SetHealthType type); 
	

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		if (power < 1) {
			power = 1;
		}
		this.power = power;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}

	protected void shootBullet(Vector2D refVector,MathFunction speed) {
		if (this instanceof Enemy) {
			Projectile eBullet = new Projectile(this,refVector,States.ENEMY,speed);
			GameLogic.getInstance().getAllBullet().add(eBullet);
			GameLogic.getInstance().addNewObject(eBullet);
		} else if (this instanceof Player) {
			Projectile eBullet = new Projectile(this,refVector,States.ALLIES,speed);
			GameLogic.getInstance().getAllBullet().add(eBullet);
			GameLogic.getInstance().addNewObject(eBullet);
			
		}
		
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	public Move getCurrentMoveType() {
		return currentMoveType;
	}

	public void setCurrentMoveType(Move currentMoveType) {
		this.currentMoveType = currentMoveType;
	}

	public double getShootSpeed() {
		return shootSpeed;
	}
	
	@Override
	public int getZ() {
		return z;
	}

	
	public void setShootSpeed(double shootSpeed) {
		this.shootSpeed = shootSpeed;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public double getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(double attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public boolean isDead() {
		return dead;
	}
	
	@Override
	public Rectangle getHitBox() {
		return new Rectangle(this.x - modelSize / 2, this.y - modelSize / 2, modelSize, modelSize);
	}
}
