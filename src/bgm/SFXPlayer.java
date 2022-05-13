package bgm;

import java.util.HashMap;

import javafx.scene.media.AudioClip;

public class SFXPlayer {
	
	private static HashMap<String,AudioClip> sfxMap = new HashMap<>();
	
	
	static {
		loadResource();
	}
	
	private static void loadResource() {
		final AudioClip LEVELUP = new AudioClip(ClassLoader.getSystemResource("sfx/levelup.wav").toString());
		final AudioClip SHOOT = new AudioClip(ClassLoader.getSystemResource("sfx/pistol.wav").toString());
		final AudioClip PICKITEM = new AudioClip(ClassLoader.getSystemResource("sfx/pick_up_item.wav").toString());
		final AudioClip UPSUCCESS = new AudioClip(ClassLoader.getSystemResource("sfx/buy_btn.wav").toString());
		final AudioClip UPFAIL = new AudioClip(ClassLoader.getSystemResource("sfx/error.wav").toString());
		final AudioClip SPAWN = new AudioClip(ClassLoader.getSystemResource("sfx/show_up.wav").toString());
		final AudioClip SHOOT_BOSS = new AudioClip(ClassLoader.getSystemResource("sfx/rifle.wav").toString());
		final AudioClip BUTTON = new AudioClip(ClassLoader.getSystemResource("sfx/btn.wav").toString());
		final AudioClip MOUSEENTERBUTTON = new AudioClip(ClassLoader.getSystemResource("sfx/mouse_enter.wav").toString());
		final AudioClip DIE = new AudioClip(ClassLoader.getSystemResource("sfx/die.wav").toString());
		final AudioClip HURT = new AudioClip(ClassLoader.getSystemResource("sfx/damaged.wav").toString());
		final AudioClip SHIELDBREAK = new AudioClip(ClassLoader.getSystemResource("sfx/sheidDown.wav").toString());
		sfxMap.put("levelUp", LEVELUP);
		sfxMap.put("shoot", SHOOT);
		sfxMap.put("pickItem", PICKITEM);
		sfxMap.put("Successful", UPSUCCESS);
		sfxMap.put("Error", UPFAIL);
		sfxMap.put("spawn", SPAWN);
		sfxMap.put("enter", MOUSEENTERBUTTON);
		sfxMap.put("shootBoss", SHOOT_BOSS);
		sfxMap.put("btn", BUTTON);
		sfxMap.put("die", DIE);
		sfxMap.put("hurt", HURT);
		sfxMap.put("shieldBreak", SHIELDBREAK);
	}

	public static HashMap<String, AudioClip> getSfxMap() {
		return sfxMap;
	}
	
	
	
}
