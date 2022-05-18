package gui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tools.Data;

public class Selection extends GridPane {
	
	public Selection() {
		SelectedType choice1 = new SelectedType(100,15,3,5.5,1.3,15,"Balancer","Balance in all thing");
		SelectedType choice2 = new SelectedType(200,12,2.8,5,1.4,20,"Tank","Excel in Vitality but cost with Agility and Power");
		SelectedType choice3 = new SelectedType(50,18,3.8,6,1.2,10,"Speedy","Excel in Agility and Power but cost with Vitality");
		this.add(choice1, 0, 1);
		this.add(choice2, 1, 1);
		this.add(choice3, 2, 1);
		Text text = new Text("Choose your type");
		text.setFont(Data.FONT16);
		text.setFill(Color.ORANGE);
		this.add(text, 0, 0);
		this.setPadding(new Insets(10));
		this.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		GridPane.setColumnSpan(text,3);
		GridPane.setHalignment(text,HPos.CENTER);
	}
	
	
}
