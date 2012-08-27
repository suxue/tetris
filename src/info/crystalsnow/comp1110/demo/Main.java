package info.crystalsnow.comp1110.demo;

import javax.swing.border.TitledBorder;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
	private class _CloseAppHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
				Platform.exit();
		}
		
		
	}
    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
    	Scene scene = new Scene(root, 800, 600, Color.WHITE);
    	scene.getStylesheets().add(this.getClass().getResource(
    		      "/resources/stylesheet.css"
    		    ).toExternalForm());

    	
    	// Create Menu Bar and make its width auto-adjustable
    	MenuBar menuBar = new MenuBar();
    	menuBar.prefWidthProperty().bind(stage.widthProperty());
    	
    	Menu fileMenu = new Menu("file");
    	fileMenu.getItems().add(new MenuItem("New"));
    	fileMenu.getItems().add(new MenuItem("Save"));
    	fileMenu.getItems().add(new SeparatorMenuItem());
    	MenuItem exitMenuItem = new MenuItem("Exit");
    	exitMenuItem.setOnAction(new _CloseAppHandler());
    	
    	fileMenu.getItems().add(exitMenuItem);
    	menuBar.getMenus().add(fileMenu);
    
// not work, the image shows like an icons instead of a background image
//		Image imageDecline = new Image(getClass().getResourceAsStream("button.png"));
//    	exitButton.setGraphic(new ImageView(imageDecline));
    	final Button exitButton = new Button("Exit");
    	exitButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    	exitButton.setOnAction(new _CloseAppHandler());
    	exitButton.setId("exitButton");
    	
    	BorderPane borderPane = new BorderPane();
    	borderPane.setCenter(exitButton);
    	borderPane.setTop(menuBar);
    	
    	root.getChildren().add(borderPane);

    	stage.setTitle("Demo");
    	stage.setScene(scene);
        stage.show();
    }
}
