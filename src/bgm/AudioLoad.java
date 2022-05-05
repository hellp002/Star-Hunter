package bgm;

import java.util.HashMap;

import javafx.scene.media.MediaPlayer;

public class AudioLoad {

	private static MediaPlayer currentBGM;
	private static HashMap<String,MediaPlayer> mediaMap = new HashMap<>();
	public static HashMap<String, MediaPlayer> getMediaMap() {
		return mediaMap;
	}


	public static MediaPlayer getCurrentBGM() {
		return currentBGM;
	}
	
	public static void playMusic(String name) {
		if (currentBGM != null) {
			currentBGM.stop();
		}
		currentBGM = mediaMap.get(name);
		currentBGM.setCycleCount(MediaPlayer.INDEFINITE);
		currentBGM.play();
	}

	
	
}
