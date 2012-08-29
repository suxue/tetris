package tetrisGame;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainFrame extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Tetris");
		Group root = new Group();
		Scene scene = new Scene(root, 600, 500);
		primaryStage.setScene(scene);
		
		for(int i=1;i<=22;i++){
			for(int j=1;j<=10;j++){
					
				Rectangle r = new Rectangle(j*20, i*20, 20, 20);
				r.setFill(Color.LIGHTGRAY);
				r.setStroke(Color.BLACK);
				r.setStrokeWidth(0.5);
				root.getChildren().add(r);
			}
		}
		
	
		
		primaryStage.show();
	}

	
	public static void main(String[] args) {
	launch(args);

	}

}
