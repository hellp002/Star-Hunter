package gui;

import bgm.SFXPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tools.Data;

public class SkillLine extends VBox{
	private ImageView skill;
	public Button getSubmit() {
		return submit;
	}

	private Button submit;
	
	public SkillLine(Image image,String text) {
		this.setPadding(new Insets(10));
		skill = new ImageView(image);
		skill.setFitHeight(50);
		skill.setFitWidth(50);
		this.setAlignment(Pos.CENTER);
		submit = new Button(text);
		submit.setOnMouseEntered((MouseEvent Event) ->{SFXPlayer.getSfxMap().get("enter").play();submit.setStyle("");});
		submit.setFont(Data.FONT16);
		submit.setTextFill(Color.ORANGE);
		this.getChildren().addAll(skill,submit);
	}

}
