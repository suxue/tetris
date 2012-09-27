package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

// combination of cells
// maintain my rotation status
abstract public class Tetromino {

    public enum Direction {
       LEFT, RIGHT
    }

    // return whether rotation is successful
    public abstract boolean rotate(Direction dir);
    public abstract void attach(TetrisGrid grid);
    public abstract void detach();

    private DoubleProperty xPropertyImpl;
    private DoubleProperty yPropertyImpl;

    public DoubleProperty xProperty() {
        return xPropertyImpl;
    }

    public DoubleProperty yProperty() {
        return yPropertyImpl;
    }

    public Tetromino(double x, double y) {
        xPropertyImpl = new SimpleDoubleProperty(x);
        yPropertyImpl = new SimpleDoubleProperty(y);
    }

    public Tetromino() {
        this(0, 0);
    }
}
