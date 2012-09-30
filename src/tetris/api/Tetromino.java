package tetris.api;

import javafx.scene.shape.Shape;
import tetris.api.game.GameState;
import tetris.tetrominos.TetrisGrid;

// a shape can mostly has four orientation
//  see http://tetrisconcept.net/wiki/Orientation
//  they are: Point UP|RIGHT|DOWN|LEFT
public interface Tetromino {
    enum Orientation {
        UP, LEFT, RIGHT, DOWN
    }


    public void attach(TetrisGrid grid);
    public void detach();
}
