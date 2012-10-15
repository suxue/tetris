package tetris.core;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;




/**
 *
 * @author shellfish
 */
public class UIController implements Initializable {

    /*
        UI Components
     */
    @FXML private Button newGameButton;
    @FXML private ToggleButton toggleButton;
    @FXML private Pane optionPage;
	@FXML private Button restartButton;
    @FXML private ToggleButton fullscreenButton;
	@FXML private Rectangle rect;
    @FXML private BorderPane root;
    @FXML private HBox center;
    @FXML private StackPane previewBox;
    @FXML private Slider columnNumberSlider;
    @FXML private Slider rowNumberSlider;
    @FXML private Slider frameRateSlider;
    @FXML private Pane   gameOverPage;



    public HBox getCenter() {
        return center;
    }

    public Pane getPreviewBox() {
        return previewBox;
    }

    public ToggleButton getFullscreenButton() {
        return fullscreenButton;
    }

    /*
        UI State machine
     */
    private Game game;
    private Option option;



    @FXML
    private void restartGame() {
        // restart current game
        game.restart();
        root.setCenter(center);
        center.requestFocus();
    }

    @FXML void startNewGame() {
        if (game != null) {
            // delete current game
            game.delete();
            game = null;
        }

        game = new Game(this, option) {
            @Override public void stop() {
                root.setCenter(gameOverPage);
            }
        };

        restartGame();
        toggleButton.setDisable(false);
        toggleButton.setSelected(false);
        restartButton.setDisable(false);
    }

    @FXML
    private void newGame() {
        toggleButton.setDisable(true);
        restartButton.setDisable(true);
        root.setCenter(optionPage);
    }

    @FXML
    private void toggleGame() {
        if (root.getCenter() == center && game != null) {
            // can only toggle when game is on top
            game.toggle();
        }
    }


	@Override
	public void initialize(URL url, ResourceBundle rb) {
        // initialize option
        option = new Option();
        option.frameRateProperty().bind(frameRateSlider.valueProperty());
        option.rowNumberProperty().bind(rowNumberSlider.valueProperty());
        option.columnNumberProperty().bind(columnNumberSlider.valueProperty());
	}
}
