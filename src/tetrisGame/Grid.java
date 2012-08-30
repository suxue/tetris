package tetrisGame;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Grid {

	Group root =new Group();
	public Group drawGrid(){
		for(int i=1;i<=22;i++)
			for(int j=1;j<=10;j++){
				
				Rectangle r = new Rectangle(j*20, i*20, 20, 20);
				r.setFill(Color.LIGHTGRAY);
				r.setStroke(Color.BLACK);
				r.setStrokeWidth(0.5);
				root.getChildren().add(r);
		}
		return(root);
	}
		

	

}
