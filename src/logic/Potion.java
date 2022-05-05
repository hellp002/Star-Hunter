package logic;

import javafx.scene.shape.Rectangle;
import tools.Data;

public abstract class Potion extends GameObject {
	private long spawnTime;
	private int flashingCounter;

	public Potion(Enemy enemy) {
		this.visible = true;
		this.deleted = false;
		this.x = enemy.x;
		this.y = enemy.y;
		spawnTime = System.currentTimeMillis() - SceneController.getTimeAdd();
		flashingCounter = 0;
	}

	@Override
	public void update() {
		long now = System.currentTimeMillis() - SceneController.getTimeAdd();
		if (now - spawnTime >= Data.ITEM_DELAY_DELETE) {
			this.visible = false;
			this.deleted = true;
		}
		if (now - spawnTime >= Data.ITEM_DELAY_WARNING) {
			if (flashingCounter == 0) {
				flashingCounter = 10;
			} else {
				visible = flashingCounter <= 5;
				flashingCounter--;
			}
		}
	}

	@Override
	public int getZ() {
		return 1;
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
	public Rectangle getHitBox() {
		return new Rectangle(x - Data.ITEMSIZE / 2, y - Data.ITEMSIZE / 2, Data.ITEMSIZE, Data.ITEMSIZE);
	}
}
