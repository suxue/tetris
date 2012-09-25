package test_Tetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu extends Application {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hi there!");
        //Group root = new Group();
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        
        Text hi = new Text("Hello World!");
        hi.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        root.getChildren().add(hi);
        
        primaryStage.show();
        
    }

}
