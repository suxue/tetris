package tetrisGame;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainFrame extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Tetris");
		Group root = new Group();
		Scene scene = new Scene(root, 600, 500,Color.BLUEVIOLET);
		primaryStage.setScene(scene);
		
		for(int i=1;i<=22;i++){
			for(int j=1;j<=10;j++){
					
				Rectangle r = new Rectangle(j*20, i*20, 20, 20);
				r.setFill(Color.LIGHTGRAY);
				r.setStroke(Color.BLACK);
				r.setStrokeWidth(0.5);
				root.getChildren().add(r);
			}
			Rectangle r2 = new Rectangle(315, 100, 200, 100);
			r2.setFill(Color.LIGHTSEAGREEN);
			r2.setStroke(Color.GRAY);
			r2.setStrokeWidth(2);
			root.getChildren().add(r2);
			
			Text t1 = new Text("TETRIMINO");
			t1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
			t1.setTranslateX(350);
			t1.setTranslateY(85);
			root.getChildren().add(t1);		}
		
			Rectangle r2 = new Rectangle(315, 275, 200, 30);
			r2.setFill(Color.LIGHTSEAGREEN);
			r2.setStroke(Color.GRAY);
			r2.setStrokeWidth(2);
			root.getChildren().add(r2);
			
			Text t2 = new Text("LEVEL");
			t2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
			t2.setTranslateX(370);
			t2.setTranslateY(260);
			root.getChildren().add(t2);	
			
			Rectangle r3 = new Rectangle(315, 350, 200, 30);
			r3.setFill(Color.LIGHTSEAGREEN);
			r3.setStroke(Color.GRAY);
			r3.setStrokeWidth(2);
			root.getChildren().add(r3);
		
			Text t3 = new Text("SCORE");
			t3.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
			t3.setTranslateX(365);
			t3.setTranslateY(340);
			root.getChildren().add(t3);	
		
		primaryStage.show();
	}

	
	public static void main(String[] args) {
	launch(args);

	}

}
