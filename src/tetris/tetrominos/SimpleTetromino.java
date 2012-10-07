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
import tetris.api.Grid;
import tetris.api.Tetromino;

// combination of cells
// maintain my rotation status
abstract public class SimpleTetromino implements Tetromino {

    protected Mino[] allMinos;  // all cells included within me
    private  Grid hostGrid = null;
    private  int  status;

    protected  boolean hasBound = false;

    @Override
    public int getStatus() {
        return status;
    }

    protected void setStatus(int s) {
        if (s > 3 || s < 0)
            throw  new RuntimeException();

        this.status = s;
    }

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

    private void setToTopMiddle(Grid grid) {
    }

    @Override
    public final void attach(Grid grid) {
        for (Mino c : allMinos) {
            c.attach(grid);
        }
        // set to the top middle position
        setBoundingBox(new BoundingBox((grid.getColumnNo() / 2) - 2, 0));
        hostGrid = grid;
    }


    @Override
    public final void detach() {
        for (Mino c : allMinos) {
            c.detach();
        }
        hostGrid = null;
    }

    protected void setCssClass(String cssClass) {
        for (Mino c : allMinos)
            c.getStyleClass().add(cssClass);
    }


    @Override
    public void pin() {
        BoundingBox bb = getBoundingBox();
        setBoundingBox(new BoundingBox(bb.getX(), Math.ceil(bb.getY())));

        for (Mino c : allMinos) {
            hostGrid.set((int) c.getMinoXProperty().get()
                    , (int) c.getMinoYProperty().get(), c);
            c.getMinoYProperty().unbind();
            c.getMinoXProperty().unbind();
        }
    }


    protected void rebindMinos() {
        if (hasBound) { // unbind first if has bound
            for (Mino m : allMinos) {
                m.getMinoXProperty().unbind();
                m.getMinoYProperty().unbind();
            }
        }

        int[] rotatingArray = getRotatingData()[getStatus()];
        for (int i=0; i < 4; i++) {
            allMinos[i].getMinoXProperty().bind(xProperty().add(rotatingArray[2*i]));
            allMinos[i].getMinoYProperty().bind(yProperty().add(rotatingArray[2*i + 1]));
        }
    }


    abstract public int[][]     getRotatingData();
}
