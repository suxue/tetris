/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  controller for user interface, internal hold all used ui components
 *           dynamic layout code resides in the initialize() function and will be
 *           executed by the fxml loader when loading completing.
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.core;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import tetris.ui.LargeLabel;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author shellfish
 */
public class UIController implements Initializable {

    /*
        UI Components
     */
    @FXML
    private StackPane window;
    @FXML
    private ToolBar toolbar;
    @FXML
    private ToggleButton toggleButton;
    @FXML
    private Pane optionPage;
    @FXML
    private BorderPane root;
    @FXML
    private HBox center;
    @FXML
    private StackPane previewBox;
    @FXML
    private Slider columnNumberSlider;
    @FXML
    private Slider rowNumberSlider;
    @FXML
    private Slider frameRateSlider;
    @FXML
    private Pane gameOverPage;
    @FXML
    private LargeLabel columnNumberLabel;
    @FXML
    private LargeLabel rowNumberLabel;
    @FXML
    private LargeLabel frameRateLabel;
    @FXML
    private GridPane optionDisplay;
    @FXML
    private WebView helpPage;
    @FXML
    private ToggleButton helpButton;
    @FXML
    private  HBox helpContainer;
    @FXML
    private LargeLabel softDropSpeedLabel;
    @FXML
    private Slider softDropSpeedSlider;
    @FXML
    private Button newGameButton;


    public HBox getCenter() {
        return center;
    }

    public Pane getPreviewBox() {
        return previewBox;
    }


    /*
        UI State machine
     */
    private Game game;
    private Option option;


    public void restartGame() {
        // restart current game
        if (game != null) {
            toggleButton.setDisable(false);
            game.restart();
            root.setCenter(center);
            center.requestFocus();
        }
    }

    private void startNewGame() {
        game = new Game(this, option) {
            @Override
            public void stop() {
                root.setCenter(gameOverPage);
                toggleButton.setDisable(true);
            }
        };

        restartGame();
        toggleButton.setSelected(false);
        newGameButton.setText("New Game");
    }

    public void newGame() {
        if (root.getCenter() != optionPage) {
            if (game != null) {
                // delete current game
                game.delete();
                game = null;
            }
            toggleButton.setDisable(true);
            root.setCenter(optionPage);
            newGameButton.setText("Start");
        } else {
            startNewGame();
        }
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
        option.softDropSpeedProperty().bind(softDropSpeedSlider.valueProperty());


        frameRateLabel.setText(String.valueOf(frameRateSlider.valueProperty().intValue()));
        columnNumberLabel.setText(String.valueOf(columnNumberSlider.valueProperty().intValue()));
        rowNumberLabel.setText(String.valueOf(rowNumberSlider.valueProperty().intValue()));
        softDropSpeedLabel.setText(String.valueOf(softDropSpeedSlider.valueProperty().intValue()) + "x");
        // initialize sliders and their labels
        frameRateSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newVal) {
                frameRateLabel.setText(String.valueOf(newVal.intValue()));

            }
        });

        columnNumberSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newVal) {
                columnNumberLabel.setText(String.format("%1$,.0f", newVal));
            }
        });


        rowNumberSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
                rowNumberLabel.setText(String.format("%1$,.0f", newVal));
                if (oldVal.intValue() == Math.round(softDropSpeedSlider.getValue())) {
                    softDropSpeedSlider.setValue(newVal.intValue());
                }
            }
        });


        softDropSpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newVal) {
                softDropSpeedLabel.setText(String.format("%1$,.0f", newVal) + "x");
            }
        });


        // load help page
        String helpLink = getClass().getResource("/doc/help.html").toExternalForm();
        helpPage.getEngine().load(helpLink);
        helpContainer.maxWidthProperty().bind(window.widthProperty().subtract(toolbar.heightProperty()).multiply(0.8));
        helpContainer.maxHeightProperty().bind(window.heightProperty().subtract(helpContainer.translateYProperty()).multiply(0.8));
        helpContainer.translateYProperty().bind(toolbar.heightProperty().divide(2.0f));

        helpContainer.visibleProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean newVal) {
                helpButton.setSelected(newVal);
            }
        });
        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                helpContainer.setVisible(!helpContainer.isVisible());
            }
        });


    }
}
