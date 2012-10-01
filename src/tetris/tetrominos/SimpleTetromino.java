package tetris.tetrominos;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import tetris.api.Tetromino;

// combination of cells
// maintain my rotation status
abstract public class SimpleTetromino implements Tetromino{

    private final DoubleProperty xPropertyImpl = new SimpleDoubleProperty(0);
    private final DoubleProperty yPropertyImpl = new SimpleDoubleProperty(0);
    private TetrisGrid     hostGrid = null;

    protected  Cell[] tetrominoCells;
    protected  Paint  tetrominoColor;

    @NotNull
    private final Cell[] getTetrominoCells() {
        return tetrominoCells;
    }

    @Override
    public final DoubleProperty xProperty() {
        return xPropertyImpl;
    }

    @Override
    public final DoubleProperty yProperty() {
        return yPropertyImpl;
    }

    @Override
    public final void sink() {
        assert hostGrid != null;
        for (Cell c: getTetrominoCells()) {
            hostGrid.sink(c);
        }
    }

    @Override
    public final void attach(TetrisGrid grid) {
        for (Cell c: getTetrominoCells()) {
            c.setFill(tetrominoColor);
            c.attach(grid);
        }
        hostGrid  = grid;
    }


    @Override
    public final void detach() {
        for (Cell c : getTetrominoCells()) {
            c.detach(hostGrid);
        }
        hostGrid = null;
    }


    public SimpleTetromino(Paint color) {
        tetrominoColor = color;
        setToCanonicalPosition();
    }


    protected abstract double getPivotXshift();

    protected abstract double getPivotYshift();

    @Override
    public final void setToCanonicalPosition() {
        setToCanonicalPosition(new Point2D(0, 0));
    }

    @Override
    public final void setToCanonicalPosition(Point2D upLeftCorner) {
        xProperty().set(getPivotXshift() + upLeftCorner.getX());
        yProperty().set(getPivotYshift() + upLeftCorner.getY());
    }
}
