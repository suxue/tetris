/* all UI components(not includng tetrominos) will be initialized within me
   Copyright (C) 2012, thu10e team.
   This file is part of the implementaion of Tetris Game  made by thu10e team
   for the assessment of COMP1110/67 ** 10 assignment.
 */
package tetris.core;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import tetris.api.Tetromino;
import tetris.api.game.GameControl;
import tetris.api.game.GameState;
import tetris.tetrominos.IShape;
import tetris.tetrominos.OShape;
import tetris.tetrominos.TetrisGrid;

import java.util.Random;

// response for drawing the interface
public class GameUI extends HBox {

    private GameControl gameControl = null;
    private TetrisGrid playField = null;
    private TetrisGrid predicationField = null;


    private class TetrisGameLogic {


        private Tetromino generateNextTetromino() {
            Tetromino t;
            int tetroClass = randGenerator.nextInt() % 2;

            switch (tetroClass) {
                case 0:
                    t = new IShape(playField.getCellPool());
                    break;
                case 1:
                    t = new OShape(playField.getCellPool());
                    break;
                default:
                    assert false;  // should not reach here
                    t = new IShape(playField.getCellPool());
                    break;
            }
            return t;
        }


        private void toggle() { //  PLAY/PAUSE
            if (gameControl.getStatus() == GameControl.Status.PLAY_GAME) {
                System.out.println("Pause");
                gameControl.stop();
            } else {
                System.out.println("resume");
                gameControl.play();
            }
        }


        /////////////////////////////////////////////////////////////////////
        //             MAIN LOOP                                           //
        /////////////////////////////////////////////////////////////////////

        private final int frameRate = 60;
        private final Duration frameInterval = Duration.millis(1000 / frameRate);
        private Random randGenerator = new Random();

        // initial status
        private Tetromino dynamicTetromino;
        private Tetromino staticTetromino;

        private int cycleCount;

        private void prepareNewTetromino() {
            staticTetromino = generateNextTetromino();
            dynamicTetromino = null;
            staticTetromino.attach(predicationField);
        }

        private void startFromBeginning() {
            cycleCount = 0;
            staticTetromino.detach();
            dynamicTetromino = staticTetromino;
            dynamicTetromino.attach(playField);
            staticTetromino = generateNextTetromino();
            staticTetromino.attach(predicationField);
        }

        private void cleanAfterEnd() {
            playField.getCellPool().reInitialize();
        }


        private final KeyFrame mainFrame = new KeyFrame(frameInterval,
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        if (dynamicTetromino == null) {
                            startFromBeginning();
                        } else {
                            if (!dynamicTetromino.moveDown(0.07)) {
                                // reach boundary
                                dynamicTetromino.pin();
                                playField.squeeze();
                                // swap tetromino
                                staticTetromino.detach();
                                dynamicTetromino = staticTetromino;
                                dynamicTetromino.attach(playField);
                                if (!dynamicTetromino.moveDown(0.001)) { // reach top
                                    gameControl.restart();
                                }

                                staticTetromino = generateNextTetromino();
                                staticTetromino.attach(predicationField);
                            }
                        }

                        cycleCount++;
                    }
                }
        );

        private final Timeline timeline = TimelineBuilder.create()
                .cycleCount(Animation.INDEFINITE)
                .keyFrames(mainFrame)
                .build();

        public TetrisGameLogic() {
            prepareNewTetromino();

            GameUI.this.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    switch (keyEvent.getCode()) {
                        case P:
                            toggle();
                            break;
                        case R:
                            gameControl.restart();
                            break;
                        case LEFT:
                            //if (dynamicTetromino != null && dynamicTetromino.getLengthToLeftBoundary() >= 1) {
                            dynamicTetromino.moveLeft();
                            // }
                            break;
                        case RIGHT:
                            //if (dynamicTetromino != null && dynamicTetromino.getLengthToRightBoundary() >= 1) {
                            dynamicTetromino.moveRight();
                            //}
                            break;
                    }
                }
            });

            gameControl.addStatusListener(new GameControl.StatusListener() {
                @Override
                public void callback(GameControl.Status oldStatus, GameControl.Status newStatus) {
                    // TODO: finish game logic
                    switch (newStatus) {
                        case STOP_GAME:
                            // cannot stop while not playing
                            if (oldStatus == GameControl.Status.PLAY_GAME) {
                                timeline.pause();
                            }
                            break;
                        case PLAY_GAME:
                            timeline.play();
                            break;
                        case RESTART_GAME:
                            gameControl.stop();
                            cleanAfterEnd();
                            prepareNewTetromino();
                            gameControl.play();
                            break;
                    } // end switch
                }
            });

        } // end TetrisGameLogic()
    }

    /* java beans properties */
    private final DoubleProperty componentWidthProperty = new SimpleDoubleProperty();
    private final DoubleProperty componentHeightProperty = new SimpleDoubleProperty();
    private final DoubleProperty topBottomPaddingProperty = new SimpleDoubleProperty();
    private final DoubleProperty leftRightPaddingProperty = new SimpleDoubleProperty();
    private final DoubleProperty mainZoneWidthProperty = new SimpleDoubleProperty();
    private final DoubleProperty rightPaneWidthProperty = new SimpleDoubleProperty();

    /* layout constants */
    static final double ComponentHeightPercentage;
    static final double ComponentWidthPercentage;
    static final double MainZoneWidthPercentage;
    static final double RightPaneWidthPercentage;
    static final double TetrominoZoneHeightPercentage;
    static final double LevelZoneHeightPercentage;
    static final double ScoreZoneHeightPercentage;

    static {
        ComponentHeightPercentage = 0.80;
        ComponentWidthPercentage = 0.77;
        MainZoneWidthPercentage = 0.60;
        RightPaneWidthPercentage = 0.30;
        TetrominoZoneHeightPercentage = 0.15;
        LevelZoneHeightPercentage = 0.30;
        ScoreZoneHeightPercentage = 0.30;
    }

    private TetrisGrid createPlayFieldGrid() {
        playField = new TetrisGrid(Color.BLACK, 20, 10, mainZoneWidthProperty, componentHeightProperty);
        return playField;
    }

    private TetrisGrid createPredicationField() {
        return (predicationField = new TetrisGrid(Color.BLACK, 2, 4, rightPaneWidthProperty, componentHeightProperty.multiply(TetrominoZoneHeightPercentage)));
    }

    public GameUI(GameState gameState) {

        gameControl = (GameControl) gameState;

        componentWidthProperty.bind(gameState.widthProperty().multiply(ComponentWidthPercentage));
        componentHeightProperty.bind(gameState.heightProperty().multiply(ComponentHeightPercentage));
        topBottomPaddingProperty.bind(gameState.heightProperty().subtract(componentHeightProperty).divide(2.0f));
        leftRightPaddingProperty.bind(gameState.widthProperty().subtract(componentWidthProperty).divide(2.0f));
        mainZoneWidthProperty.bind(componentWidthProperty.multiply(MainZoneWidthPercentage));
        rightPaneWidthProperty.bind(componentWidthProperty.multiply(RightPaneWidthPercentage));


        this.setWidth(gameState.getWidth());
        this.setHeight(gameState.getHeight());
        // set initial widthProperty and padding
        this.setPadding(new Insets(
                topBottomPaddingProperty.doubleValue()
                , leftRightPaddingProperty.doubleValue()
                , topBottomPaddingProperty.doubleValue()
                , leftRightPaddingProperty.doubleValue()
        ));

        // add listener to keep padding
        gameState.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue
                    , Number oldVal, Number newVal) {
                final double newPadding = leftRightPaddingProperty.doubleValue();
                final Insets oldInsets = GameUI.this.getPadding();
                final Insets newInsets = new Insets(oldInsets.getTop(), newPadding
                        , oldInsets.getBottom(), newPadding);
                GameUI.this.setPadding(newInsets);
            }
        });


        gameState.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue
                    , Number oldVal, Number newVal) {
                final double newPadding = topBottomPaddingProperty.doubleValue();
                final Insets oldInsets = GameUI.this.getPadding();
                final Insets newInsets = new Insets(newPadding, oldInsets.getRight()
                        , newPadding, oldInsets.getLeft());
                GameUI.this.setPadding(newInsets);
            }
        });

        TetrisGrid playField = createPlayFieldGrid();
        this.getChildren().add(playField);

        final VBox rightPane = new VBox();
        rightPane.spacingProperty().bind(componentHeightProperty
                .multiply(1 - TetrominoZoneHeightPercentage
                        - LevelZoneHeightPercentage - ScoreZoneHeightPercentage).multiply(0.5));

        final TetrisGrid tetrominoZone = createPredicationField();

        final Rectangle levelZone = new Rectangle();
        final Rectangle scoreZone = new Rectangle();
        levelZone.widthProperty().bind(rightPaneWidthProperty);
        scoreZone.widthProperty().bind(rightPaneWidthProperty);
        levelZone.heightProperty().bind(componentHeightProperty.multiply(LevelZoneHeightPercentage));
        scoreZone.heightProperty().bind(componentHeightProperty.multiply(ScoreZoneHeightPercentage));
        rightPane.getChildren().addAll(tetrominoZone, levelZone, scoreZone);


        this.getChildren().add(rightPane);

        this.spacingProperty().bind(widthProperty()
                .multiply(1 - MainZoneWidthPercentage - RightPaneWidthPercentage));


        new TetrisGameLogic();
    }

}
