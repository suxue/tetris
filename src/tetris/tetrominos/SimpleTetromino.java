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
    private int status;

    protected boolean hasBound = false;

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
            throw new RuntimeException();

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
        for (Mino c : allMinos) {
            c.getStyleClass().clear();
            c.getStyleClass().add(cssClass);
        }

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

        double[] rotatingArray = getRotatingData()[getStatus()];
        for (int i = 0; i < 4; i++) {
            allMinos[i].getMinoXProperty().bind(xProperty().add(rotatingArray[2 * i]));
            allMinos[i].getMinoYProperty().bind(yProperty().add(rotatingArray[2 * i + 1]));
        }
    }


    abstract public double[][] getRotatingData();

    abstract public int[][] getWallKickData();


    private Point2D boundingBoxToPivot(Point2D bb) {
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


    @Override
    public boolean rotate(boolean clockWise) {
        Point2D pivot = getPivot();
        double kickOffset;
        {
            double y = pivot.getY();
            double t = Math.ceil(y) - y;
            kickOffset = (t > 0 && t < 0.2) ? t : 0;
        }

        int st;
        {
            st = getStatus() + (clockWise ? 1 : -1);
            st = (st == -1) ? 3 : st;
            st = (st == 4) ? 0 : st;
        }
        double[] rd = getRotatingData()[st];
        int[] wd;
        if (clockWise) {
            wd = getWallKickData()[getStatus()];
        } else {
            int[] p = getWallKickData()[st];
            wd = new int[10];
            for (int i = 0; i < 10; i++) {
                wd[i] = -p[i];
            }
        }


        double x;
        double y;
        int floor;
        int ceil;
        int i = 0;
        int j = 0;
        int k = 0;
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 4; j++) {
                x = pivot.getX() + wd[i * 2] + rd[j * 2];
                y = pivot.getY() + wd[i * 2 + 1] + rd[j * 2 + 1];
                floor = (int) Math.floor(y);
                ceil = (int) Math.ceil(y);
                if (!hostGrid.isAccessible((int) x, floor)) {
                    break;
                }

                if (floor != ceil && !hostGrid.isAccessible((int) x, ceil)) {
                    break;
                }
            }

            if (j == 4) {
                break;
            }

            // check kicked postion
            if (kickOffset != 0) {
                for (k = 0; k < 4; k++) {
                    x = pivot.getX() + wd[i * 2] + rd[k * 2];
                    y = pivot.getY() + wd[i * 2 + 1] + rd[k * 2 + 1] + kickOffset;
                    if (!hostGrid.isAccessible((int) x, (int) y)) {
                        break;
                    }
                }

                if (k == 4) {
                    break;
                }
            }
        }

        if (i == 5) {
            // fail completely
            return false;
        }

        if (j == 4) {
            setPivot(new Point2D(pivot.getX() + wd[i * 2], pivot.getY() + wd[i * 2 + 1]));
        } else if (k == 4) {
            setPivot(new Point2D(pivot.getX() + wd[i * 2], pivot.getY() + wd[i * 2 + 1] + kickOffset));
        } else {
            throw new RuntimeException();
        }


        setStatus(st);
        rebindMinos();
        return true;
    }
}
