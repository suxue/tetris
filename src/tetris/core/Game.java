/* centering game class
 */


package tetris.core;

import javafx.application.Platform;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.SceneBuilder;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuBarBuilder;
import javafx.scene.control.MenuBuilder;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.control.SeparatorMenuItemBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

class GameRoot extends BorderPane {

    private static final double scaleFactor = 0.15;

    private Button _createButton(String id, String text) {
        return ButtonBuilder.create()
                .text(text)
                .id(id)
                .maxHeight(Double.MAX_VALUE)
                .maxWidth(Double.MAX_VALUE)
                .build();
    }

    private class CloseAppHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Platform.exit();
        }
    }

    private class NewGameHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            setCenter(new  Grid());
        }
    }

    GameRoot() {
        Menu fileMenu = MenuBuilder.create()
            .text("Demo")
            .items(   MenuItemBuilder.create().text("New")
                       .onAction(new NewGameHandler()).build()
                   , MenuItemBuilder.create().text("Save").build()
                   , SeparatorMenuItemBuilder.create().build()
                   , MenuItemBuilder.create().text("Exit")
                      .onAction(new CloseAppHandler()).build())
            .build();

        MenuBar menuBar = MenuBarBuilder.create().menus(fileMenu).build();
        menuBar.prefWidthProperty().bind(this.widthProperty());

        Button exitButton = _createButton("exitButton", "Exit");
        Button newButton = _createButton("newButton", "New Game");
        exitButton.setOnAction(new CloseAppHandler());
        newButton.setOnAction(new NewGameHandler());
        final VBox vbox = VBoxBuilder.create()
                .alignment(Pos.CENTER)
                .children(  newButton
                            , _createButton("saveButton", "Save")
                            ,  exitButton )
                .build();
        vbox.maxWidthProperty().bind(this.widthProperty().multiply(0.77));

        this.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                Insets oldInsets = vbox.getPadding(); 
                Double newPadding = newValue.doubleValue() * scaleFactor;
                Insets newInsets = new Insets(oldInsets.getTop(), newPadding,
                        oldInsets.getBottom(), newPadding);
                vbox.setPadding(newInsets);
            }
        });

        this.heightProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                Insets oldInsets = vbox.getPadding(); 
                Double newPadding = newValue.doubleValue() * scaleFactor;
                Insets newInsets = new Insets(newPadding, oldInsets.getLeft(),
                        newPadding, oldInsets.getRight());
                vbox.setPadding(newInsets);
                vbox.setSpacing(newPadding);
            }
        });

        this.setCenter(vbox);
        this.setTop(menuBar);
    }
}

public class Game extends GameState {
    private Stage primaryStage;

    public void prepare(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Scene primaryScene =  SceneBuilder.create()
                            .root(new GameRoot())
                            .stylesheets(
                                 this.getClass().getResource(
                                "/css/stylesheet.css" ).toExternalForm())
                            .width(getWidth())
                            .height(getHeight())
                            .fill(Color.AQUAMARINE)
                            .build();
      
        primaryStage.titleProperty().bindBidirectional(titleProperty());
        widthProperty().bind(primaryStage.widthProperty());
        heightProperty().bind(primaryStage.heightProperty());
        primaryStage.setScene(primaryScene);
    }
    
    public void fire() {
        primaryStage.show();
    }
    
    
    
}
