package logic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import tools.Data;
import tools.MathFunction;
import tools.SetHealthType;
import tools.States;
import tools.Vector2D;

public class Projectile extends GameObject {

	private Vector2D directionVector;
	private States state;
	private MathFunction typeBullet;
	private int damage;
	private long shootTime;
	private double shootSpeed;
	private static final Image[] BULLET_IMG = new Image[5];
	private Entity shooter;
	private final double BULLET_SIZE = 20;
	
	static {
		BULLET_IMG[0] = new Image(ClassLoader.getSystemResource("pic/bullet/bullet_boss.png").toString());
		BULLET_IMG[1] = new Image(ClassLoader.getSystemResource("pic/bullet/bullet_monster.png").toString());
		BULLET_IMG[2] = new Image(ClassLoader.getSystemResource("pic/bullet/bullet_pistol.png").toString());
		BULLET_IMG[3] = new Image(ClassLoader.getSystemResource("pic/bullet/bullet_rifle.png").toString());
		BULLET_IMG[4] = new Image(ClassLoader.getSystemResource("pic/bullet/bullet_staff.png").toString());
	}

	public Projectile(Entity shooter, Vector2D fireVector, States state, MathFunction typeBullet) {
		this.x = shooter.x;
		this.y = shooter.y;
		directionVector = new Vector2D(fireVector);
		this.state = state;
		this.typeBullet = typeBullet;
		this.damage = shooter.getPower();
		this.shootTime = System.currentTimeMillis() - SceneController.getTimeAdd();
		this.shootSpeed = shooter.getShootSpeed();
		this.visible = true;
		this.deleted = false;
		this.shooter = shooter;
	}

	@Override
	public void update() {
		switch (typeBullet) {
		case COS:
			updateTypeCos();
			break;
		case LINEAR:
			updateTypeLinear();
			break;
		case CONST:
			updateTypeConst();
			break;
		}
		if (isOutOfFrame()) {
			visible = false;
			deleted = true;
		}

	}

	private void updateTypeCos() {
		long now = System.currentTimeMillis() - SceneController.getTimeAdd();
		final double CIRCLE = Math.PI * 2;
		double xMove = directionVector.getX() * shootSpeed;
		double yMove = directionVector.getY() * shootSpeed;
		final double toDecimal = 0.001;
		x += xMove * Math.abs(Math.cos(CIRCLE * ((now - shootTime) % Data.SECOND) * toDecimal));
		y += yMove * Math.abs(Math.cos(CIRCLE * ((now - shootTime) % Data.SECOND)));
	}
	
	private boolean isOutOfFrame() {
		return ((x < Data.BOARDERLEFT) || (x > Data.BOARDERRIGHT) || (y < Data.BOARDERTOP) || (y > Data.BOARDERDOWN));
	}
	
	private void updateTypeLinear() {
		long now = System.currentTimeMillis() - SceneController.getTimeAdd();
		double xMove = directionVector.getX() * shootSpeed;
		double yMove = directionVector.getY() * shootSpeed;
		final double toDouble = 1.0;
		x += xMove * ((now - shootTime) / (Data.SECOND * toDouble));
		y += yMove * ((now - shootTime) / (Data.SECOND * toDouble));
	}

	private void updateTypeConst() {
		double xMove = directionVector.getX() * shootSpeed;
		double yMove = directionVector.getY() * shootSpeed;
		x += xMove;
		y += yMove;
	}
	
	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void draw(GraphicsContext gc) {
		if (state.equals(States.ALLIES)) {
			gc.drawImage(BULLET_IMG[2], this.x - BULLET_SIZE/2, this.y - BULLET_SIZE/2,BULLET_SIZE,BULLET_SIZE );
		} else if (state.equals(States.ENEMY)) {
			if (shooter instanceof ChampionMonster) {
				if (typeBullet.equals(MathFunction.LINEAR)) {
					gc.drawImage(BULLET_IMG[4], this.x - BULLET_SIZE/2, this.y - BULLET_SIZE/2,BULLET_SIZE,BULLET_SIZE );
				} else if (typeBullet.equals(MathFunction.COS)) {
					gc.drawImage(BULLET_IMG[3], this.x - BULLET_SIZE/2, this.y - BULLET_SIZE/2,BULLET_SIZE,BULLET_SIZE );
				} else {
					gc.drawImage(BULLET_IMG[0], this.x - BULLET_SIZE/2, this.y - BULLET_SIZE/2,BULLET_SIZE,BULLET_SIZE );
				}
				
			} else if (shooter instanceof NormalMonster) {
				gc.drawImage(BULLET_IMG[1], this.x - BULLET_SIZE/2, this.y - BULLET_SIZE/2,BULLET_SIZE,BULLET_SIZE );
			} 
		}

	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public boolean isDeleted() {
		return deleted;
	}

	@Override
	public void onCollision(GameObject other) {
		if (state.equals(States.ALLIES) && (other instanceof Enemy)) {
			Enemy enemy = (Enemy) other;
			if (enemy.isDead()) {
				return;
			}
			enemy.setHealth(enemy.getHealth() - damage,SetHealthType.DAMAGE);
			visible = false;
			deleted = true;
		} else if (state.equals(States.ENEMY) && (other instanceof Player)) {
			Player player = (Player) other;
			player.takeDamge(damage);
			visible = false;
			deleted = true;
		}

	}

	@Override
	public Rectangle getHitBox() {
		// TODO Auto-generated method stub
		return new Rectangle(this.x - BULLET_SIZE/2, this.y - BULLET_SIZE/2,BULLET_SIZE,BULLET_SIZE);
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return y;
	}

}
