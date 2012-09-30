package tetris.api;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;
import tetris.api.game.GameState;
import tetris.tetrominos.TetrisGrid;

// a shape can mostly has four orientation
//  see http://tetrisconcept.net/wiki/Orientation
//  they are: Point UP|RIGHT|DOWN|LEFT
public interface Tetromino {

    public DoubleProperty xProperty();
    public DoubleProperty yProperty();

    public void attach(TetrisGrid grid);
    public void detach();
    public void setToCanonicalPosition();
    public void setToCanonicalPosition(Point2D upLeftCorner);
}
