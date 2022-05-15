package logic.entity;

import logic.GameObject;

public abstract class Enemy extends Entity {

	private int giveXP;

	public int getGiveXP() {
		return giveXP;
	}

	public void setGiveXP(int giveXP) {
		if (giveXP < 0) {
			giveXP = 0;
		}
		this.giveXP = giveXP;
	}

	protected void giveXPToPlayer() {
		if (isDead()) {
			return;
		}
		Player player = Player.getInstance();
		player.setXP(player.getXp() + giveXP);
	}

	public Enemy(int health, int power) {
		super(health, power);
		this.movingCounter = 0;

	}

	@Override
	public void onCollision(GameObject other) {
		if (isDead()) {
			return;
		}
		if (other instanceof Player) {
			Player player = (Player) other;
			player.takeDamge(this.getPower());
		}

	}

}
