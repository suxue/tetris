package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import tetris.api.Tetromino;
import tetris.api.Tetromino.*;

// combination of cells
// maintain my rotation status
abstract public class SimpleTetromino implements Tetromino{

    protected  TetrisGrid   hostGrid;        // the attached grid, null if not attached
    protected  Cell[]       tetrominoCells;  // all cells included within me

    private final DoubleProperty xPropertyImpl = new SimpleDoubleProperty(0);
    private final DoubleProperty yPropertyImpl = new SimpleDoubleProperty(0);
    @Override
    public final DoubleProperty xProperty() {
        return xPropertyImpl;
    }

    @Override
    public final DoubleProperty yProperty() {
        return yPropertyImpl;
    }


    @Override
    public final void sink() throws UnAttachedTetrominoException {
        if (hostGrid == null)
            throw new UnAttachedTetrominoException();

        for (Cell c: tetrominoCells)
            hostGrid.sink(c);
    }


    @Override
    public final void release(CellPool cp) throws  AttachedTetrominoException {
        if (hostGrid != null)
            throw  new AttachedTetrominoException();

        for (Cell c: tetrominoCells)
            cp.add(c);
    }

    @Override
    public final void attach(TetrisGrid grid) throws AttachedTetrominoException {
        if (hostGrid != null)
            throw new AttachedTetrominoException();

        for (Cell c: tetrominoCells) {
            c.attach(grid);
        }
        hostGrid  = grid;
    }


    @Override
    public final void detach() throws  UnAttachedTetrominoException {
        if (hostGrid == null) {
            throw new UnAttachedTetrominoException();
        }
        for (Cell c : tetrominoCells) {
            c.detach(hostGrid);
        }
        hostGrid = null;
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

    protected void setColor(Paint color) {
        for (Cell c: tetrominoCells)
            c.setFill(color);
    }
}
