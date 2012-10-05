/* Include the shared functions among different tetrominos, every concrete
   tetromino class should inherit me.

   Copyright (C) 2012, thu10e team.
   This file is part of the implementaion of Tetris Game  made by thu10e team
   for the assessment of COMP1110/67 ** 10 assignment.
 */
package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Paint;
import tetris.api.Grid;
import tetris.api.Tetromino;

// combination of cells
// maintain my rotation status
abstract public class SimpleTetromino implements Tetromino {

    protected Mino[] tetrominoMinos;  // all cells included within me
    protected Grid hostGrid = null;

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
    public final void attach(Grid grid) {
        for (Mino c : tetrominoMinos) {
            c.attach(grid);
        }
        setToTopMiddle(grid);
        hostGrid = grid;
    }


    @Override
    public final void detach() {
        for (Mino c : tetrominoMinos) {
            c.detach();
        }
        hostGrid = null;
    }

    protected void setColor(Paint color) {
        for (Mino c : tetrominoMinos)
            c.setFill(color);
    }


    @Override
    public void pin() {
        double y = Math.ceil(yProperty().get() - getPivotYShift());
        yProperty().set(y + getPivotYShift());
        for (Mino c : tetrominoMinos) {

            hostGrid.set((int) c.getMinoXProperty().get()
                    , (int) c.getMinoYProperty().get(), c);
            c.getMinoYProperty().unbind();
            c.getMinoXProperty().unbind();
        }
    }


    protected final void setToTopMiddle(Grid grid) {
        xProperty().set(grid.getColumnNo() / 2);
        yProperty().set(getPivotYShift());
    }

}
