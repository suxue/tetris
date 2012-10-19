/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  represent a game entity, is (optionaly) associated to a  mino pool
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.core;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import tetris.tetrominos.*;
import tetris.ui.LargeLabel;
import tetris.util.Rand;

import static tetris.core.State.*;

/*
    all states in the game finite autometon
 */
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
    ST_STOPPED,
}

class Game {
    /////////////////////////////////////////////////////////////////////
    //             Data Section                                        //
    /////////////////////////////////////////////////////////////////////

    private Grid playField;
    private Grid previewField;

    private Rand randGenerator = new Rand();

    private State state;
    private State oldState;
    private Tetromino dynamicTetromino;
    private Tetromino staticTetromino;
    private int cycleCount;


    // how many cycles has the dynamicTetromino been stopped?
    private int stopCycles;
    // when does the moving(rotating/moving left/moving right) begin?
    private int movingStartingCycle;
    // how many cycles should be wait before locking the dynamicTetromino
    // after it has stopped?
    private final int lockDelay; //  frames
    private int movingDelay = 10; // frames

    private static final double distancePerFrame = 1.25;
    private final double baseSpeed;
    private double gravity;
    private double softDropFactor;
    private final int softDropBase;

    private IntegerProperty scoreCounter = new SimpleIntegerProperty(-1);
    private LargeLabel   scoreLabel = new LargeLabel();
    private LargeLabel   timerLabel = new LargeLabel();
    private Pane scoreBox;
    private Pane timerBox;
    private final int level;

    {
        scoreLabel.getStyleClass().add("score-label");
        timerLabel.getStyleClass().add("timer-label");

        scoreCounter.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newVal) {
                scoreLabel.setText(String.format("%03d", 100 * newVal.intValue()));
            }
        });
    }


    long runningTimeInSeconds;
    Timer timer = new Timer();


    /////////////////////////////////////////////////////////////////////
    //             Auxiliary Functions                                 //
    /////////////////////////////////////////////////////////////////////

    private void increaseScore(int add) {
        if (add > 0) {
            scoreCounter.set(scoreCounter.get() + add);
        }
    }


    public void restart() {
        goTo(ST_STARTED);
        timer.start();
    }


    protected void toggle() {
        if (getState() == ST_PAUSED) {
            goTo(getOldState());
        } else {
            goTo(ST_PAUSED);
        }
    }


    private void goTo(State newState) {
        oldState = getState();
        state = newState;
    }

    protected  State getState() {
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
        return gravity * (softDropFactor + level);
    }

    private void drop() {
        if (dynamicTetromino.canMoveDown(getSpeed())) {
            dynamicTetromino.moveDown(getSpeed());
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
            dynamicTetromino.rotate(clockwise);
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
                scoreCounter.set(0);
                timer.reset();
                gravity = (1/48.0f);


                // memory recovery
                playField.recoverAllocatedMinos();

                // spawn and display a new tetromino in the preview zone
                staticTetromino = getNewTetromino();
                dynamicTetromino = null;
                staticTetromino.attach(previewField);

                goTo(ST_SPAWNING);

                break;
            case ST_SPAWNING:
                softDropFactor = 0.0;
                stopCycles = 0;

                staticTetromino.detach();
                dynamicTetromino = staticTetromino;
                staticTetromino = getNewTetromino();
                staticTetromino.attach(previewField);
                dynamicTetromino.attach(playField, -2);

                // IF reach boundary
                if (!dynamicTetromino.canMoveDown(0.001)) {
                    goTo(ST_STOPPED);
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
                increaseScore(playField.squeeze());
                goTo(ST_SPAWNING);
                break;
            case ST_STOPPED:
                timer.pause();
                stop();
                break;
            default:  // should not reach here
                throw new RuntimeException();
        }

    }

    // will be executed when game ending, so convinent for customize game over screen
    public void stop() {
    }

    private Pane parent;
    private Rectangle wall;


    private class Timer extends AnimationTimer {
        private LongProperty runningTimeInNanoSecond = new SimpleLongProperty();
        private IntegerProperty runningTimeInSecond = new SimpleIntegerProperty();
        private boolean isPause = true;

        public Timer() {
            runningTimeInSecond.bind(runningTimeInNanoSecond.divide(1000000000));
            runningTimeInSecond.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newVal) {
                    int seconds = newVal.intValue();
                    int minutes = seconds / 60;
                    seconds %= 60;
                    timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
                }
            });
        }


        private long oldTime;
        @Override
        public void handle(long newTime) {
            long interval = newTime - oldTime;
            oldTime = newTime;
            if (getState() == ST_PAUSED) {
                return;
            }

            if (interval < 70000000000l) {
                runningTimeInNanoSecond.set(runningTimeInNanoSecond.get() + interval);
                gravity = baseSpeed * interval;
            }

            myhandler(interval / (100000000.0));
        }

        public void myhandler(double interval) {
            cycleCount++;
            runStateMachine();
        }

        @Override
        public void start() {
            if (isPause) {
                isPause = false;
                super.start();
            }
        }

        public void reset() {
            oldTime = 0;
            runningTimeInNanoSecond.set(0);
        }

        public void pause() {
            isPause = true;
            stop();
        }
    }


    /////////////////////////////////////////////////////////////////////
    //             Constructor                                         //
    /////////////////////////////////////////////////////////////////////
    public Game(UIController uiController, Option option) {


        parent = uiController.getCenter();
        scoreBox = uiController.getScoreBox();
        timerBox = uiController.getTimerBox();
        scoreBox.getChildren().add(scoreLabel);
        timerBox.getChildren().add(timerLabel);

        int columns = option.columnNumberProperty().get();
        int rows = option.rowNumberProperty().get();
        lockDelay = option.lockDelayProperty().get();
        softDropBase = 20;
        baseSpeed = 1.25e-09;
        level = option.levelProperty().get();

        playField = new Grid(rows, columns, parent);
        previewField = new Grid(2, 4, uiController.getPreviewBox());


        wall = new Rectangle();
        wall.setManaged(false);
        wall.getStyleClass().add("wall");
        wall.xProperty().bind(playField.xShiftProperty());
        wall.yProperty().bind(playField.yShiftProperty());
        wall.widthProperty().bind(playField.minoWidthProperty().multiply(playField.getColumnNo()));
        wall.heightProperty().bind(playField.minoHeightProperty().multiply(playField.getRowNo()));
        parent.getChildren().add(wall);


        parent.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
                    case SPACE:
                        softDropFactor = softDropBase * (1+level/10.0f);
                        break;
                    case P:
                    case ENTER:
                        toggle();
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


        parent.setOnKeyReleased(new EventHandler<KeyEvent>() {
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

    public void delete() {
        timer.stop();
        scoreBox.getChildren().remove(scoreLabel);
        timerBox.getChildren().remove(timerLabel);
        playField.recoverAllocatedMinos();
        parent.getChildren().remove(wall);
    }

}  // end Game
