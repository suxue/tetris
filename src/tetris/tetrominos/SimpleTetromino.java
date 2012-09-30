package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import tetris.api.Tetromino;

// combination of cells
// maintain my rotation status
abstract public class SimpleTetromino implements Tetromino{

    public enum Direction {
        LEFT, RIGHT
    }

    // return whether rotation is successful
    public abstract boolean rotate(Direction dir);

    @Override
    public void attach(TetrisGrid grid) {
        grid.simpleTetrominoList.add(this);
    }

    private DoubleProperty xPropertyImpl;
    private DoubleProperty yPropertyImpl;

    public DoubleProperty xProperty() {
        return xPropertyImpl;
    }

    public DoubleProperty yProperty() {
        return yPropertyImpl;
    }

    public SimpleTetromino(double x, double y) {
        xPropertyImpl = new SimpleDoubleProperty(x);
        yPropertyImpl = new SimpleDoubleProperty(y);
    }

    public SimpleTetromino() {
        this(0, 0);
    }
}
