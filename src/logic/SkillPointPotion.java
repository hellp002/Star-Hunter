package logic;

import bgm.SFXPlayer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tools.Data;

public class SkillPointPotion extends Potion {
	
	private static final int SKILL_POINT = 1;
	private static final Image SPRITE = new Image(ClassLoader.getSystemResource("pic/loot/mana_potion.png").toString());
	
	public SkillPointPotion(Enemy enemy) {
		super(enemy);
	}
	
	@Override
	public void onCollision(GameObject other) {
		if (other instanceof Player && !isDeleted()) {
			Player player = (Player) other;
			SFXPlayer.getSfxMap().get("pickItem").play();
			player.setSkillPoint(player.getSkillPoint() + SKILL_POINT);
			this.visible = false;
			this.deleted = true;
		}
		
	}
	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(SPRITE, this.x - Data.ITEMSIZE/2, this.y - Data.ITEMSIZE/2,Data.ITEMSIZE,Data.ITEMSIZE);
		
	}
}
