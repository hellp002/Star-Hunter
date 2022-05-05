package logic;

import java.util.ArrayList;

import bgm.AudioLoad;
import bgm.SFXPlayer;
import gui.Tiles;
import sharedObject.RenderableHolder;
import tools.Data;

public class GameLogic {

	private static GameLogic instance = new GameLogic();
	private ArrayList<GameObject> allEntity;
	private ArrayList<Projectile> allBullet;
	private ArrayList<Enemy> enemys;
	private boolean running,update;
	public boolean isUpdate() {
		return update;
	}

	private int wave;

	public static GameLogic getInstance() {
		return instance;
	}

	public static void newGame() {
		instance = new GameLogic();

	}

	public GameLogic() {
		SceneController.setTimeAdd(0);
		RenderableHolder.getInstance().getEntities().clear();
		allEntity = new ArrayList<GameObject>();
		allBullet = new ArrayList<Projectile>();
		enemys = new ArrayList<Enemy>();
		Tiles tiles = new Tiles();
		running = true;
		wave = 0;
		Player you = Player.getInstance();
		addNewObject(tiles);
		addNewObject(you);
	}




	public boolean isRunning() {
		return running;
	}

	public void addNewObject(Tiles tiles) {
		RenderableHolder.getInstance().add(tiles);
	}

	public void addNewObject(GameObject object) {
		allEntity.add(object);
		RenderableHolder.getInstance().add(object);
	}

	public void addNewEnemy(Enemy enemy) {

		enemys.add(enemy);
		addNewObject(enemy);
	}

	private void callNextWave() {
		if (enemys.size() <= 0) {
			wave++;
			SFXPlayer.getSfxMap().get("spawn").play();
			if (wave >= 10 && !AudioLoad.getCurrentBGM().equals(AudioLoad.getMediaMap().get("Lastlong"))) {
				AudioLoad.playMusic("Lastlong");
			}
			final int divideFactor = 5;
			if ((wave % 5) == 0) {
				for (int i = 0; i < wave / divideFactor; i++) {
					Enemy creep = new ChampionMonster(Data.BASE_HP_CM + Data.HP_PER_WAVE_CM * wave,
							Data.BASE_PW_CM +(int) (Data.PW_PER_WAVE_CM * wave), Data.BASE_S_CM,
							Data.BASE_SP_CM + Data.SP_PER_WAVE_CM * wave);
					addNewEnemy(creep);
				}
				for (int i = 0; i < wave / divideFactor; i++) {
					Enemy creep = new NormalMonster(Data.BASE_HP_NM + Data.HP_PER_WAVE_NM * wave,
							Data.BASE_PW_NM + (int)(Data.PW_PER_WAVE_NM * wave), Data.BASE_S_NM,
							Data.BASE_SP_NM + Data.SP_PER_WAVE_NM * wave);
					addNewEnemy(creep);
				}
				return;
				
			}

			for (int i = 0; i < wave; i++) {
				Enemy creep = new NormalMonster(Data.BASE_HP_NM + Data.HP_PER_WAVE_NM * wave,
						Data.BASE_PW_NM + (int)(Data.PW_PER_WAVE_NM * wave), Data.BASE_S_NM,
						Data.BASE_SP_NM + Data.SP_PER_WAVE_NM * wave);
				addNewEnemy(creep);
			}
		}
	}

	public int getWave() {
		return wave;
	}

	public void logicUpdate() {
		
		update = true;
		callNextWave();
		for (GameObject each : copyOne()) {
			each.update();
		}
		update = false;
		for (int i = 0; i < allEntity.size(); i++) {
			for (int j = i; j < allEntity.size(); j++) {
				GameObject object1 = allEntity.get(i);
				GameObject object2 = allEntity.get(j);
				if (object1.checkCollision(object2)) {
					if (!object1.isDeleted()) {
						object1.onCollision(object2);
					}
					if (!object2.isDeleted()) {
						object2.onCollision(object1);
					}
				}
			}
		}

		for (int i = allEntity.size() - 1; i >= 0; i--) {
			GameObject object1 = allEntity.get(i);
			if (object1.isDeleted()) {
				allEntity.remove(i);
			}
		}

		for (int i = enemys.size() - 1; i >= 0; i--) {
			Enemy enemy = enemys.get(i);
			if (enemy.isDeleted()) {
				enemys.remove(i);
			}
		}
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public ArrayList<Projectile> getAllBullet() {
		return allBullet;
	}

	private ArrayList<GameObject> copyOne() {
		ArrayList<GameObject> copy = new ArrayList<GameObject>();
		for (GameObject e : allEntity) {
			copy.add(e);
		}
		return copy;
	}
}
