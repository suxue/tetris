/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  the core class of whole project, connects EntryPoint to the UI and logic part
 *  @author: $Author$
 *  @date:   $Date$
 */

package tetris.core;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tetris.api.game.GameControl;
import tetris.api.game.GameProperty;
import tetris.api.game.GameState;

import java.net.URL;

import static tetris.api.game.GameControl.Status.*;


class TetrisStatic implements GameProperty {

    private final static SimpleStringProperty _version = new SimpleStringProperty("0.02");
    private final static SimpleStringProperty _name = new SimpleStringProperty("Tetris Game");

    @Override
    public final ReadOnlyStringProperty version() {
        return _version;
    }

    @Override
    public final String getVersion() {
        return version().getValue();
    }

    @Override
    public final ReadOnlyStringProperty name() {
        return _name;
    }

    @Override
    public final String getName() {
        return name().getValue();
    }
}

class TetrisDynamic extends TetrisStatic implements GameState {
    private final SimpleStringProperty _title = new SimpleStringProperty();
    private final SimpleDoubleProperty _width = new SimpleDoubleProperty();
    private final SimpleDoubleProperty _height = new SimpleDoubleProperty();

    TetrisDynamic(double width, double height) {
        super();
        _title.setValue(name().getValue());
        _width.setValue(width);
        _height.setValue(height);
    }

    TetrisDynamic() {
        this(745, 700);
    }

    @Override
    public final StringProperty title() {
        return _title;
    }

    @Override
    public final String getTitle() {
        return title().getValue();
    }

    @Override
    public final void setTitle(String title) {
        title().setValue(title);
    }

    @Override
    public final DoubleProperty widthProperty() {
        return _width;
    }

    @Override
    public final double getWidth() {
        return widthProperty().getValue();
    }

    @Override
    public final void setWidth(double width) {
        widthProperty().setValue(width);
    }

    @Override
    public final DoubleProperty heightProperty() {
        return _height;
    }

    @Override
    public final double getHeight() {
        return heightProperty().getValue();
    }

    @Override
    public final void setHeight(double height) {
        heightProperty().setValue(height);
    }

}


public final class Tetris extends TetrisDynamic implements GameControl {
    private GameControl gameControl = new GameControl() {
        private StatusProperty runningStatus = new StatusProperty(PREPARE_ALL);

        @Override
        public StatusProperty statusProperty() {
            return runningStatus;
        }

        @Override
        public Status getStatus() {
            return StatusProperty.toStatus(runningStatus.getValue());
        }

        @Override
        public void setStatus(Status rs) {
            runningStatus.setValue(rs);
        }


        @Override
        public void play() {
            setStatus(PLAY_GAME);
        }

        @Override
        public void restart() {
            setStatus(RESTART_GAME);
        }

        @Override
        public void stop() {
            setStatus(STOP_GAME);
        }

        @Override
        public void quit() {
            setStatus(END_ALL);
        }

        @Override
        public void show_menu() {
            setStatus(SHOW_MENU);
        }

        @Override
        public void addStatusListener(final StatusListener sl) {
            statusProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
                    sl.callback(StatusProperty.toStatus(oldVal), StatusProperty.toStatus(newVal));
                }
            });
        }
    };

    private Stage primaryStage;
    private boolean isFullScreen = false;
    private double stageXposition;
    private double stageYposition;

    public void toggleFullScreen() {
        if (isFullScreen) {
            primaryStage.setFullScreen(false);
            primaryStage.setX(stageXposition);
            primaryStage.setY(stageYposition);
            isFullScreen = false;
        } else {
            stageXposition = primaryStage.getX();
            stageYposition = primaryStage.getY();
            primaryStage.setFullScreen(true);
            isFullScreen = true;
        }
    }

    public void addStatusListener(StatusListener sl) {
        gameControl.addStatusListener(sl);
    }

    public Tetris() {
        super();

        addStatusListener(new StatusListener() {
            @Override
            public void callback(Status oldStatus, Status newStatus) {
                if (newStatus == END_ALL) {
                    Platform.exit();
                    System.out.println("Tetris ended, bye...");
                }
            }
        });
    }

    public void init(final Stage primaryStage) {

        this.primaryStage = primaryStage;

        URL cssurl = Tetris.class.getResource("/css/stylesheet.bss");
        if (cssurl == null) {
            cssurl = Tetris.class.getResource("/css/stylesheet.css");
        }

        URL fallbackCssUrl = Tetris.class.getResource("/css/fallback-stylesheet.bss");
        if (fallbackCssUrl == null) {
            fallbackCssUrl = Tetris.class.getResource("/css/fallback-stylesheet.css");
        }

        Scene primaryScene = SceneBuilder.create()
                .root(new RootUI(this))
                .stylesheets(fallbackCssUrl.toExternalForm())
                .stylesheets(cssurl.toExternalForm())
                .width(getWidth())
                .height(getHeight())
                .fill(Color.LIGHTSEAGREEN)
                .build();


        primaryStage.titleProperty().bindBidirectional(title());
        widthProperty().bind(primaryStage.widthProperty());
        heightProperty().bind(primaryStage.heightProperty());


        primaryStage.setScene(primaryScene);
        primaryScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.F) {
                    toggleFullScreen();
                    keyEvent.consume();
                } else if (keyEvent.getCode() == KeyCode.Q) {
                    quit();
                } else if (keyEvent.getCode() == KeyCode.M) {
                    stop();
                    show_menu();
                }
            }
        });

        show_menu();
    }

    // mixin GameControl interface
    public Status getStatus() {
        return gameControl.getStatus();
    }

    public void show_menu() {
        gameControl.show_menu();
    }

    public void stop() {
        gameControl.stop();
    }

    public void quit() {
        gameControl.quit();
    }

    public void play() {
        gameControl.play();
    }

    public StatusProperty statusProperty() {
        return gameControl.statusProperty();
    }

    public void setStatus(Status rs) {
        gameControl.setStatus(rs);
    }

    @Override
    public void restart() {
        gameControl.restart();
    }
}
