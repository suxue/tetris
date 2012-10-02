package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Paint;
import tetris.api.Tetromino;

// combination of cells
// maintain my rotation status
abstract public class SimpleTetromino implements Tetromino{

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
    public final void attach(TetrisGrid grid)  {
        for (Cell c: tetrominoCells) {
            c.attach(grid);
        }
        setToTopMiddle(grid);
    }


    @Override
    public final void detach() {
        for (Cell c : tetrominoCells) {
            c.detach();
        }
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


    @Override
    public void pin() {
        double x = Math.floor(xProperty().get() - getPivotXShift());
        double y = Math.floor(yProperty().get() - getPivotYShift());
        xProperty().set(x + getPivotXShift());
        yProperty().set(y + getPivotYShift());
    }

    protected final void setToTopMiddle(TetrisGrid grid) {
        xProperty().set(grid.getColumnNumber() / 2);
        yProperty().set(getPivotYShift());
    }

}
