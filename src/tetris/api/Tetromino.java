package tetris.api;

import javafx.beans.property.DoubleProperty;
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

    public double getPivotXShift();
    public double getPivotYShift();

    // after attaching, I'll be showed in that grid
    public void attach(TetrisGrid grid);
    public void detach();
    public void pin();

    // movement function family
    public boolean moveDown(double len);
    public boolean moveLeft();
    public boolean moveRight();
}
