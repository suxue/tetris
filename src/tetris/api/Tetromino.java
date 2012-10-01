package tetris.api;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point2D;
import tetris.tetrominos.CellPool;
import tetris.tetrominos.TetrisGrid;

// a shape can mostly has four orientation
//  see http://tetrisconcept.net/wiki/Orientation
//  they are: Point UP|RIGHT|DOWN|LEFT
public interface Tetromino {

    public DoubleProperty xProperty();
    public DoubleProperty yProperty();

    public void attach(TetrisGrid grid);
    public void detach();


    // all cells will be sunk into the attached grid
    // after sinking, the Tetromino itself can be safely garbage-collected
    public void sink();
    public void release(CellPool cp);


    public void setToCanonicalPosition();
    public void setToCanonicalPosition(Point2D upLeftCorner);

    public void setToTopMiddle();

    public void moveUp(double len);
    public void moveDown(double len);
    public void moveLeft(double len);
    public void moveRight(double len);

    public double getLengthToRightBoundary();
    public double getLengthToLeftBoundary();

    public double getLengthToBottomBoundary();
}
