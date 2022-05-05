package gui;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import tools.Data;

public class TextInBar extends StackPane{
	private Text text;
	private Rectangle box;
	
	public TextInBar() {
		this.text = new Text();
		text.setFont(Data.FONT24);
		text.setFill(Color.WHITE);
		box = new Rectangle(0,0,Data.BAR_LENGTH,Data.BAR_HEIGHT);
		box.setFill(Color.TRANSPARENT);
		this.getChildren().addAll(box,this.text);
		
	}
	
	public void setText(String text) {
		this.text.setText(text);
	}

}
