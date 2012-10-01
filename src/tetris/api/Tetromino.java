package tetris.api;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import tetris.tetrominos.CellPool;
import tetris.tetrominos.TetrisGrid;

// a shape can mostly has four orientation
//  see http://tetrisconcept.net/wiki/Orientation
//  they are: Point UP|RIGHT|DOWN|LEFT
public interface Tetromino {

    public class UnAttachedTetrominoException extends  Exception {}
    public class AttachedTetrominoException extends  Exception {};

    /*
     *   @xProperty: x-coordinate of my pivot
     *   @yProperty: y-coordinate of my pivot
     */
    public DoubleProperty xProperty();
    public DoubleProperty yProperty();

    // after attaching, I'll be showed in that grid
    public void attach(TetrisGrid grid)
            throws AttachedTetrominoException;

    // simply release all cells contained within me
    // detach first if needed
    public void release(CellPool cp)
            throws AttachedTetrominoException;

    public void detach()
            throws UnAttachedTetrominoException;

    // attach me to a grid before sinking
    // after sinking, all cells inside me will continue to be showed on that grid
    //   however, myself can be safely dropped
    public void sink() throws UnAttachedTetrominoException;

    // movement function family
    public void moveDown(double len);
    public void moveLeft(double len);
    public void moveRight(double len);
}
