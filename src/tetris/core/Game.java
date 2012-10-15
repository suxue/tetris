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

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import tetris.tetrominos.*;
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

    private final int frameRate;
    private Rand randGenerator = new Rand();
    private final double frameIntervalInMileSecond;
    private final Duration frameInterval;

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

    private static final double distancePerFrame = 1.25;
    private final double baseSpeed;
    private double speedFactor = 1;


    private final KeyFrame mainFrame;
    private final Timeline timeline;

    /////////////////////////////////////////////////////////////////////
    //             Auxiliary Functions                                 //
    /////////////////////////////////////////////////////////////////////

    public void restart() {
        goTo(ST_STARTED);
        timeline.play();
    }


    public void toggle() {
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
                speedFactor = 1.0;
                stopCycles = 0;

                staticTetromino.detach();
                dynamicTetromino = staticTetromino;
                staticTetromino = getNewTetromino();
                staticTetromino.attach(previewField);
                dynamicTetromino.attach(playField);

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
                playField.squeeze();
                goTo(ST_SPAWNING);
                break;
            case ST_STOPPED:
                timeline.stop();
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

    /////////////////////////////////////////////////////////////////////
    //             Constructor                                         //
    /////////////////////////////////////////////////////////////////////
    public Game(UIController uiController, Option option) {


        parent = uiController.getCenter();

        int columns = option.columnNumberProperty().get();
        int rows = option.rowNumberProperty().get();
        frameRate = option.frameRateProperty().get();
        frameIntervalInMileSecond = ((double) 1000) / frameRate;
        frameInterval = Duration.millis(frameIntervalInMileSecond);
        baseSpeed = distancePerFrame / frameRate;

        playField = new Grid(rows, columns, parent);
        previewField = new Grid(2, 4, uiController.getPreviewBox());


        mainFrame = new KeyFrame(frameInterval,
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


        timeline = TimelineBuilder.create().cycleCount(Animation.INDEFINITE)
                .keyFrames(mainFrame).build();

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
        timeline.stop();
        playField.recoverAllocatedMinos();
        parent.getChildren().remove(wall);
    }

}  // end GameLogic
