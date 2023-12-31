package tools;

import java.util.ArrayList;

import gui.SceneController;
import javafx.scene.input.KeyCode;
import logic.GameLogic;
import logic.entity.Player;

public class InputUtility {
	private static ArrayList<KeyCode> keyPressed = new ArrayList<>();
	
	
	public static ArrayList<KeyCode> getKeyPressed() {
		return keyPressed;
	}
	
	public static void setKeyPressed(KeyCode keycode,boolean pressed) {
		if(pressed){
			if(!keyPressed.contains(keycode)){
				keyPressed.add(keycode);
			}
		}else{
			keyPressed.remove(keycode);
		}
		
		if (!Player.getInstance().isDead() && !GameLogic.getInstance().isUpdate()) {
			if (keycode.equals(KeyCode.E) && !SceneController.isShowSkill() && pressed) {
				GameLogic.getInstance().setRunning(false);
				SceneController.showSkillTree();
			} else if (SceneController.isShowSkill() && keycode.equals(KeyCode.E) && !pressed){
				GameLogic.getInstance().setRunning(true);
				SceneController.removeSkillTree();
			}
		}
	}
}
