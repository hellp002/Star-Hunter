package logic.potion;

import bgm.SFXPlayer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.entity.Enemy;
import logic.entity.Player;
import tools.Data;
import tools.Hitable;
import tools.SetHealthType;

public class HealthPotion extends Potion {
	private static final int HEALING_POINT = 10;
	private static final Image SPRITE = new Image(ClassLoader.getSystemResource("pic/loot/health_potion.png").toString());
	
	public HealthPotion(Enemy enemy) {
		super(enemy);
	}
	

	@Override
	public void onCollision(Hitable other) {
		if (other instanceof Player && !isDeleted()) {
			Player player = (Player) other;
			SFXPlayer.getSfxMap().get("pickItem").play();
			player.setHealth(player.getHealth() + HEALING_POINT,SetHealthType.HEAL);
			this.visible = false;
			this.deleted = true;
		}
		
	}
	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(SPRITE, this.x - Data.ITEMSIZE/2, this.y - Data.ITEMSIZE/2,Data.ITEMSIZE,Data.ITEMSIZE);
		
	}

	

}
