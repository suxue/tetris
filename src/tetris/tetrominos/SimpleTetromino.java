package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import tetris.api.Tetromino;

// combination of cells
// maintain my rotation status
abstract public class SimpleTetromino implements Tetromino{

    private DoubleProperty xPropertyImpl;
    private DoubleProperty yPropertyImpl;

    @Override
    public DoubleProperty xProperty() {
        return xPropertyImpl;
    }

    @Override
    public DoubleProperty yProperty() {
        return yPropertyImpl;
    }

    @Override
    public void attach(TetrisGrid grid) {
        grid.simpleTetrominoList.add(this);
    }


    public SimpleTetromino(double x, double y) {
        xPropertyImpl = new SimpleDoubleProperty(x);
        yPropertyImpl = new SimpleDoubleProperty(y);
    }

    public SimpleTetromino() {
        this(0, 0);
    }


    protected abstract double getPivotXshift();

    protected abstract double getPivotYshift();

    @Override
    public void setToCanonicalPosition() {
        setToCanonicalPosition(new Point2D(0, 0));
    }

    @Override
    public void setToCanonicalPosition(Point2D upLeftCorner) {
        xProperty().set(getPivotXshift() + upLeftCorner.getX());
        yProperty().set(getPivotYshift() + upLeftCorner.getY());
    }
}
