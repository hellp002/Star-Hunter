package logic.entity;

import tools.Hitable;

public abstract class Enemy extends Entity {

	private int giveXP;
	
	public Enemy(int health, int power) {
		super(health, power);
		this.movingCounter = 0;

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

	

	@Override
	public void onCollision(Hitable other) {
		if (isDead()) {
			return;
		}
		if (other instanceof Player) {
			Player player = (Player) other;
			player.takeDamge(this.getPower());
		}

	}
	
	public int getGiveXP() {
		return giveXP;
	}

}
