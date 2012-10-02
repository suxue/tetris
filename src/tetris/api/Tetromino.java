package tetris.api;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import tetris.tetrominos.TetrisGrid.CellPool;
import tetris.tetrominos.TetrisGrid;

// a shape can mostly has four orientation
//  see http://tetrisconcept.net/wiki/Orientation
//  they are: Point UP|RIGHT|DOWN|LEFT
public interface Tetromino {


    /*
     *   @xProperty: x-coordinate of my pivot
     *   @yProperty: y-coordinate of my pivot
     */
    public DoubleProperty xProperty();
    public DoubleProperty yProperty();

    // after attaching, I'll be showed in that grid
    public void attach(TetrisGrid grid);
    public void detach();

    public void setToTopMiddle(TetrisGrid grid);

    // movement function family
    public void moveDown(double len);
    public void moveLeft(double len);
    public void moveRight(double len);
}
