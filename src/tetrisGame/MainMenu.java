package tetrisGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuBarBuilder;
import javafx.scene.control.MenuBuilder;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.control.SeparatorMenuItem;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;

import javafx.scene.paint.Color;

import javafx.stage.Stage;

public class MainMenu extends Application {
	
	static final double scaleFactor = 0.15;
	
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
    	
    	Menu fileMenu = MenuBuilder.create()
    			.text("Demo")
                .items(
                        MenuItemBuilder.create().text("New").build()
                        , MenuItemBuilder.create().text("Save").build()
                        , new SeparatorMenuItem()
                        , MenuItemBuilder.create().text("Exit")
                            .onAction(new _CloseAppHandler()).build()
                      )
    			.build();
    	
    	MenuBar menuBar = MenuBarBuilder.create().menus(fileMenu).build();
    	menuBar.prefWidthProperty().bind(stage.widthProperty());
    	
    	Button exitButton = ButtonBuilder.create()
    			.text("Exit")
    			.id("exitButton")
    			.maxHeight(Double.MAX_VALUE)
    			.onAction(new _CloseAppHandler())
    			.build();
    	exitButton.maxWidthProperty().bind(stage.widthProperty().multiply(0.77));
    	
    	final VBox vbox = VBoxBuilder.create().alignment(Pos.CENTER).children(exitButton).build();
    	vbox.maxWidthProperty().bind(stage.widthProperty().multiply(0.77));
        stage.widthProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
                Insets oldInsets = vbox.getPadding(); 
                Double newPadding = newValue.doubleValue() * scaleFactor;
                Insets newInsets = new Insets(oldInsets.getTop(), newPadding, oldInsets.getBottom(), newPadding);
                vbox.setPadding(newInsets);
			}
        });


        stage.heightProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
                Insets oldInsets = vbox.getPadding(); 
                Double newPadding = newValue.doubleValue() * scaleFactor;
                Insets newInsets = new Insets(newPadding, oldInsets.getLeft(),
                    newPadding, oldInsets.getRight());
                vbox.setPadding(newInsets);
			}
        });



    	BorderPane borderPane = BorderPaneBuilder.create()
    			.center(vbox)
    			.top(menuBar)
    			.build();
    	
    	Group root = GroupBuilder.create().children(borderPane).build();    	
        Scene scene = SceneBuilder.create()
            .root(root)
            .stylesheets(
                    this.getClass().getResource(
                        "/css/stylesheet.css"
                        ).toExternalForm())
            .width(800)
            .height(600)
            .fill(Color.WHITE)
            .build();
        
    	stage.setScene(scene);
    	stage.setTitle("Demo");
        stage.show();
    }
}
