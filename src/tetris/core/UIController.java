package tetris.core;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author shellfish
 */
public class UIController implements Initializable {

	@FXML private Region spring;
	@FXML private Button restartButton;
    @FXML private Button optionButton;
    @FXML private ToggleButton fullscreenButton;
	@FXML private Rectangle rect;
    @FXML private BorderPane root;
    @FXML private HBox optionPage;
    @FXML private HBox center;
    @FXML private StackPane previewBox;

    public HBox getCenter() {
        return center;
    }

    public Pane getPreviewBox() {
        return previewBox;
    }

    public ToggleButton getFullscreenButton() {
        return fullscreenButton;
    }


    public void activeFullScreenButton(Stage stage) {
        System.out.println(stage.fullScreenProperty());

    }
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
        // push preview zone to the very right position
        HBox.setHgrow(spring, Priority.ALWAYS);

        optionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (root.getCenter() == optionPage) {
                    root.setCenter(center);
                } else {
                    root.setCenter(optionPage);
                }
            }
        });

	}
}
