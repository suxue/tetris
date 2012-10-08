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
import javafx.geometry.Point2D;
import tetris.api.Grid;
import tetris.api.Tetromino;

// combination of cells
// maintain my rotation status
abstract public class SimpleTetromino implements Tetromino {

    protected Mino[] allMinos;  // all cells included within me
    protected Grid hostGrid = null;
    private  int  status;

    protected  boolean hasBound = false;

    protected SimpleTetromino(Grid grid) {
        allMinos = grid.allocateMinos(4);
        setStatus(0);
        rebindMinos();
        hasBound = true;
    }

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

    protected final DoubleProperty xProperty() {
        return xPropertyImpl;
    }

    protected final DoubleProperty yProperty() {
        return yPropertyImpl;
    }

    @Override
    public final Point2D getPivot() {
        return new Point2D(xProperty().get(), yProperty().get());
    }

    @Override
    public void setPivot(Point2D pivot) {
        xProperty().set(pivot.getX());
        yProperty().set(pivot.getY());
    }


    @Override
    public final void attach(Grid grid) {
        for (Mino c : allMinos) {
            c.attach(grid);
        }
        // set to the top middle position
        setPivot(boundingBoxToPivot(new Point2D((grid.getColumnNo() / 2) - 2, 0)));
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
        Mino firstMino = allMinos[0];
        double offset = Math.ceil(firstMino.getMinoYProperty().get())
                    - firstMino.getMinoYProperty().get();
        Point2D pivot = getPivot();
        setPivot(new Point2D(pivot.getX(), pivot.getY() + offset));

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


    private Point2D      pivotToBoundingBox(Point2D pivot) {
        Point2D shift = getInitialBoundingBoxOffset();
        return new Point2D(pivot.getX() + shift.getX(), pivot.getY() + shift.getY());
    }

    private Point2D      boundingBoxToPivot(Point2D bb) {
        Point2D shift = getInitialBoundingBoxOffset();
        return new Point2D(bb.getX() - shift.getX(), bb.getY() - shift.getY());
    }

    @Override
    public void moveDown(double len) {
        yProperty().set(yProperty().get() + len);
    }

    @Override
    public void moveLeft() {
        xProperty().set(xProperty().get() - 1);
    }

    @Override
    public void moveRight() {
        xProperty().set(xProperty().get() + 1);
    }
}
