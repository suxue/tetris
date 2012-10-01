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
    protected TetrisGrid     hostGrid = null;

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
    public final void release(CellPool cp) {
        for (Cell c: tetrominoCells) {
            cp.add(c);
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
        if (hostGrid != null) {
            for (Cell c : getTetrominoCells()) {
                c.detach(hostGrid);
            }
            hostGrid = null;
        }
    }


    public SimpleTetromino(Paint color) {
        tetrominoColor = color;
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

    @Override
    public final void setToTopMiddle() {
        assert hostGrid != null;
        setToCanonicalPosition(new Point2D(hostGrid.getColumnNumber() / 2 - getPivotXshift(), 0));
    }

    @Override
    public final void moveUp(double l) {
        yProperty().set(yProperty().get() - l);
    }


    @Override
    public final void moveDown(double l) {
        yProperty().set(yProperty().get() + l);
    }


    @Override
    public final void moveLeft(double l) {
        xProperty().set(xProperty().get() - l);
    }


    @Override
    public final void moveRight(double l) {
        xProperty().set(xProperty().get() + l);
    }


    @Override
    public final double getLengthToRightBoundary() {
        assert hostGrid != null;
        return hostGrid.getColumnNumber() - xProperty().get() - getPivotXshift();
    }

    @Override
    public final double getLengthToLeftBoundary() {
        return xProperty().get() - getPivotXshift();
    }

    @Override
    public double getLengthToBottomBoundary() {
        assert  hostGrid != null;
        return hostGrid.getRowNumber() - yProperty().get() - getPivotYshift();
    }


}
