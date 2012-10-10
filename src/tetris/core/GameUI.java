/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  all UI components(not includng tetrominos) will be initialized within me
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.core;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import tetris.api.Grid;
import tetris.api.Tetromino;
import tetris.api.game.GameControl;
import tetris.api.game.GameState;
import tetris.tetrominos.*;
import tetris.tetrominos.shape.*;
import tetris.util.Rand;

import static tetris.core.State.*;


enum State {
    ST_PAUSED,
    ST_STARTED,
    ST_SPAWNING,  // auto awake
    ST_DROPPING,
    ST_MOVING_LEFT,
    ST_MOVING_RIGHT,
    ST_ROTATING_RIGHT,
    ST_ROTATING_LEFT,
    ST_LOCKED,
}

// response for drawing the interface
public class GameUI extends HBox {

    /////////////////////////////////////////////////////////////////////
    //             Data Section                                        //
    /////////////////////////////////////////////////////////////////////
    private GameControl gameControl = null;
    private Grid playField = null;
    private Grid previewField = null;
    private int score=0;
    int speedFactor = 1;
    int gravity=1;
    Text t,t1;
    

private void updateScore(int newScore) {
           score = newScore;         
           t.setText("    SCORE \n   "+(score*10)) ;          
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

    private Grid createPlayFieldGrid(final Rectangle background) {
        final int rows = 20;
        final int columns = 10;

        final DoubleProperty width = new SimpleDoubleProperty();
        final DoubleProperty height = new SimpleDoubleProperty();
        DoubleBinding subtract = background.widthProperty().subtract(
                background.heightProperty().multiply(((double) columns) / rows)
        );
        subtract.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
                if (oldVal.doubleValue() < 0 && newVal.doubleValue() > 0) {
                    width.unbind();
                    height.unbind();
                    width.bind(background.heightProperty().multiply(((double) columns) / rows));
                    height.bind(background.heightProperty());
                } else if (oldVal.doubleValue() > 0 && newVal.doubleValue() < 0) {
                    width.unbind();
                    height.unbind();
                    width.bind(background.widthProperty());
                    height.bind(background.widthProperty().multiply(((double)rows)/ columns));
                }
            }
        });



        playField = new TetrisGrid(Color.BLACK, rows, columns,  width, height);
        playField.toJavaFXNode().layoutXProperty().bind(background.widthProperty().subtract(width).divide(2.0));
        playField.toJavaFXNode().layoutYProperty().bind(background.heightProperty().subtract(height).divide(2.0));
        return playField;
    }

    private Grid createPredicationField() {
        return (previewField = new TetrisGrid(Color.BLACK, 2, 4, rightPaneWidthProperty, componentHeightProperty.multiply(TetrominoZoneHeightPercentage)));
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

        // addMino listener to keep padding
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

        final VBox leftPane = new VBox();
        final Rectangle background = new Rectangle();
        Grid playField = createPlayFieldGrid(background);
        background.setFill(Color.LIGHTSEAGREEN);
        background.heightProperty().bind(componentHeightProperty);
        background.widthProperty().bind(mainZoneWidthProperty);
        playField.toJavaFXNode().setManaged(false);
        leftPane.getChildren().addAll(background, playField.toJavaFXNode());
        this.getChildren().add(leftPane);


        final VBox rightPane = new VBox();
        rightPane.spacingProperty().bind(componentHeightProperty
                .multiply(1 - TetrominoZoneHeightPercentage
                        - LevelZoneHeightPercentage - ScoreZoneHeightPercentage).multiply(0.5));

        final Grid tetrominoZone = createPredicationField();

       // final Rectangle levelZone = new Rectangle();
       // final Rectangle scoreZone = new Rectangle();
        t =new Text("    SCORE \n   "+score);       
        t.setFont(Font.font("Ariel Black", FontWeight.BOLD,20));
        t.setTextAlignment(TextAlignment.CENTER);   
        t.setFill(Color.GREEN);
         
         t1=new Text("    LEVEL \n    "+gravity);        
         t1.setFont(Font.font("Ariel Black", FontWeight.BOLD,20));
         t1.setTextAlignment(TextAlignment.CENTER);
         t1.setTextOrigin(VPos.CENTER); 
         t1.setFill(Color.DEEPPINK);

       // levelZone.widthProperty().bind(rightPaneWidthProperty);
       // scoreZone.widthProperty().bind(rightPaneWidthProperty);
       // levelZone.heightProperty().bind(componentHeightProperty.multiply(LevelZoneHeightPercentage));
       // scoreZone.heightProperty().bind(componentHeightProperty.multiply(ScoreZoneHeightPercentage));
        rightPane.getChildren().addAll(tetrominoZone.toJavaFXNode(), t,t1);


        this.getChildren().add(rightPane);

        this.spacingProperty().bind(widthProperty()
                .multiply(1 - MainZoneWidthPercentage - RightPaneWidthPercentage));


        new GameLogic();
    }


    private class GameLogic {
        /////////////////////////////////////////////////////////////////////
        //             Data Section                                        //
        /////////////////////////////////////////////////////////////////////
        private final int frameRate = 60;
        private Rand randGenerator = new Rand();
        private final double frameIntervalInMileSecond = 1000 / frameRate;
        private final Duration frameInterval = Duration.millis(frameIntervalInMileSecond);

        private State state;
        private State oldState;
        private Tetromino dynamicTetromino;
        private Tetromino staticTetromino;
        private int cycleCount;

        // how many cycles are left for sleeping, 0 if awake
        private int sleepCycles;

        // how many cycles has the dynamicTetromino been stopped?
        private int stopCycles;
        // when does the moving(rotating/moving left/moving right) begin?
        private int movingStartingCycle;
        // how many cycles should be wait before locking the dynamicTetromino
        // after it has stopped?
        private int lockDelay = 30; //  frames
        private int movingDelay = 10; // frames
        private int startDelay = 60; // frames

        private double baseSpeed = 1 / 48.0; // how many grids to be moved within a frame
        private double speedFactor = 1;



        private final KeyFrame mainFrame = new KeyFrame(frameInterval,
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if (getState() == ST_PAUSED) {
                            return;
                        }

                        cycleCount++;

                        //
                        // timer related code below
                        //
                        if (sleepCycles != 0) {
                            sleepCycles--; // skip left code
                            return;
                        }


                        // run into state machine
                        runStateMachine();
                    }
                }
        );
        private final Timeline timeline = TimelineBuilder.create().cycleCount(Animation.INDEFINITE)
                .keyFrames(mainFrame).build();


        /////////////////////////////////////////////////////////////////////
        //             Auxiliary Functions                                 //
        /////////////////////////////////////////////////////////////////////

        private void restart() {
            goTo(ST_STARTED);
        }

        private void toggle() {
            if (getState() == ST_PAUSED) {
                System.out.println("resumed");
                goTo(getOldState());
            } else {
                System.out.println("paused");
                goTo(ST_PAUSED);
            }
        }

        private void sleep(int frames) {
            sleepCycles = frames;
        }

        private void goTo(State newState) {
            oldState = getState();
            state = newState;
            //System.out.println("from " + oldState + " to " + newState);
        }

        private State getState() {
            return state;
        }

        private State getOldState() {
            return oldState;
        }

        private Tetromino getNewTetromino() {

            Tetromino t;
            int tetroClass = randGenerator.get();

            switch (tetroClass) {
                case 0:
                    t = new ShapeL(playField);
                    break;
                case 1:
                    t = new ShapeO(playField);
                    break;
                case 2:
                    t = new ShapeI(playField);
                    break;
                case 3:
                    t = new ShapeT(playField);
                    break;
                case 4:
                    t = new ShapeS(playField);
                    break;
                case 5:
                    t = new ShapeZ(playField);
                    break;
                case 6:
                    t = new ShapeJ(playField);
                    break;
                default:
                    throw new RuntimeException();
            }
            return t;
        }

        private double getSpeed() {
            return baseSpeed * speedFactor;
        }

        private void drop() {
            if (dynamicTetromino.canMoveDown(getSpeed())) {
                dynamicTetromino.moveDown(getSpeed());
                stopCycles = 0;
            } else {
                // cannot move, align first
                dynamicTetromino.align();
                if (++stopCycles == lockDelay) {
                    goTo(ST_LOCKED);
                }
            }
        }

        private void rotate(boolean clockwise) {
            if ((cycleCount - movingStartingCycle) % movingDelay == 0) {
                dynamicTetromino.rotate(clockwise, (stopCycles == 0));
            }
        }

        private void moveLeft() {
            if ((cycleCount - movingStartingCycle) % movingDelay == 0) {
                if (dynamicTetromino.canMoveLeft()) {
                    dynamicTetromino.moveLeft();
                }
            }
        }

        private void moveRight() {
            if ((cycleCount - movingStartingCycle) % movingDelay == 0) {
                if (dynamicTetromino.canMoveRight()) {
                    dynamicTetromino.moveRight();
                }
            }
        }


        private void runStateMachine() {
            switch (getState()) {
                case ST_STARTED:
                    //
                    // reset all counters
                    //
                    cycleCount = 0;
                    sleepCycles = 0;

                    // memory recovery
                    playField.recoverAllocatedMinos();

                    // spawn and display a new tetromino in the preview zone
                    staticTetromino = getNewTetromino();
                    dynamicTetromino = null;
                    staticTetromino.attach(previewField);

                    // then sleep for some seconds
                    goTo(ST_SPAWNING);
                    sleep(startDelay);

                    break;
                case ST_SPAWNING:
                    speedFactor = gravity;
                    stopCycles = 0;

                    staticTetromino.detach();
                    dynamicTetromino = staticTetromino;
                    staticTetromino = getNewTetromino();
                    staticTetromino.attach(previewField);
                    dynamicTetromino.attach(playField);

                    // IF reach boundary
                    if (!dynamicTetromino.canMoveDown(0.001)) {
                        goTo(ST_STARTED);
                    } else { // ELSE: beginning dropping
                        goTo(ST_DROPPING);
                    }
                    break;
                case ST_DROPPING:
                    drop();
                    break;
                case ST_MOVING_LEFT:
                    moveLeft();
                    drop();
                    break;
                case ST_MOVING_RIGHT:
                    // move right
                    moveRight();
                    drop();
                    break;
                case ST_ROTATING_RIGHT:
                    // do rotation
                    rotate(true);
                    drop();
                    break;
                case ST_ROTATING_LEFT:
                    rotate(false);
                    drop();
                    break;
                case ST_LOCKED:
                    //  pin every minos to the grid
                    dynamicTetromino.pin();
                    // clear lines
                    if (t > 0) {
                        updateScore(t + score);
                        System.out.println(score);
                    }
                    playField.squeeze();
                    goTo(ST_SPAWNING);
                    break;
                default:  // should not reach here
                    throw new RuntimeException();
            }

        }

        /////////////////////////////////////////////////////////////////////
        //             Constructor                                         //
        /////////////////////////////////////////////////////////////////////
        public GameLogic() {
            gameControl.addStatusListener(new GameControl.StatusListener() {
                @Override
                public void callback(GameControl.Status oldStatus, GameControl.Status newStatus) {
                    switch (newStatus) {
                        case PLAY_GAME:
                            if (timeline.getStatus() == Animation.Status.STOPPED) {
                                gameControl.restart();
                                timeline.play();
                            }

                            if (getState() == ST_PAUSED)
                                toggle();
                            break;
                        case RESTART_GAME:
                            restart();
                            break;
                        case STOP_GAME:
                            goTo(ST_PAUSED);
                            break;
                    }
                }
            });

            GameUI.this.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (getState() == ST_PAUSED) {
                        if (keyEvent.getCode() == KeyCode.P
                                || keyEvent.getCode() == KeyCode.ENTER) {
                            toggle();
                        }
                        return;
                    }

                    switch (keyEvent.getCode()) {
                    case U:
                        gravity++;
                        t1.setText("    LEVEL \n    "+gravity);
                        System.out.println("U pressed"+gravity);
                        break;
                    case D:
                        System.out.println("D pressed"+speedFactor);
                        if(gravity>1)
                       gravity--;
                        t1.setText("    LEVEL \n    "+gravity);
                        break; 
                        case SPACE:
                            speedFactor = 20;
                            break;
                        case P:
                        case ENTER:
                            toggle();
                            break;
                        case R:
                            restart();
                            break;
                        case UP: // rotateLeft
                            if (getState() == ST_DROPPING) {
                                movingStartingCycle = cycleCount;
                                rotate(false);
                                goTo(ST_ROTATING_LEFT);
                            }
                            break;
                        case DOWN: // rotae right
                            if (getState() == ST_DROPPING) {
                                movingStartingCycle = cycleCount;
                                rotate(true);
                                goTo(ST_ROTATING_RIGHT);
                            }
                            break;
                        case LEFT:
                            if (getState() == ST_DROPPING) {
                                movingStartingCycle = cycleCount;
                                moveLeft();
                                goTo(ST_MOVING_LEFT);
                            }
                            break;
                        case RIGHT:
                            if (getState() == ST_DROPPING) {
                                movingStartingCycle = cycleCount;
                                moveRight();
                                goTo(ST_MOVING_RIGHT);
                            }
                            break;
                    }
                }
            });

            GameUI.this.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    switch (keyEvent.getCode()) {
                        case LEFT:
                            if (getState() == ST_MOVING_LEFT) {
                                goTo(ST_DROPPING);
                            }
                            break;
                        case RIGHT:
                            if (getState() == ST_MOVING_RIGHT) {
                                goTo(ST_DROPPING);
                            }
                            break;
                        case UP:
                            if (getState() == ST_ROTATING_LEFT) {
                                goTo(ST_DROPPING);
                            }
                            break;
                        case DOWN:
                            if (getState() == ST_ROTATING_RIGHT) {
                                goTo(ST_DROPPING);
                            }
                            break;
                    }
                }
            });
        } // end GameLogic()
    }  // end GameLogic

}
