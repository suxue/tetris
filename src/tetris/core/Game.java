package tetris.core;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends GameState {
    private Stage primaryStage;
    
    public void prepare(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.titleProperty().bind(titleProperty());
        Scene scene = new Scene(new Group(), getWidth(), getHeight());
        
        widthProperty().bind(scene.widthProperty());
        heightProperty().bind(scene.heightProperty());
        primaryStage.setScene(scene);
    }
    
    public void fire() {
        primaryStage.show();
    }
    
    
    
}
