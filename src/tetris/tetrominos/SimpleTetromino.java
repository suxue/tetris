/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  Include the shared functions among different tetrominos, every
 *  concrete tetromino class should inherit me.
 *  @author: $Author$
 *  @date:   $Date$
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

    protected void setCssClass(String cssClass) {
        for (Mino c : tetrominoMinos)
            c.getStyleClass().add(cssClass);
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
