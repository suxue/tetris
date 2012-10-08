/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  tetromino J
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.tetrominos.shape;

import javafx.geometry.Point2D;
import tetris.api.Grid;
import tetris.tetrominos.SimpleTSZJLTetromino;

public class ShapeJ extends SimpleTSZJLTetromino {
    public ShapeJ(Grid grid) {
        super(grid);
        setCssClass("shapeJ");
    }

    @Override
    public Point2D getInitialBoundingBoxOffset() {
        return new Point2D(-1.5, -1.5);
    }


    @Override
    public boolean canMoveDown(double len) {
        switch (getStatus()) {
            case 0:
                return allMinos[1].canMoveDown(len)
                        && allMinos[2].canMoveDown(len)
                        && allMinos[3].canMoveDown(len);
            case 1:
                return allMinos[0].canMoveDown(len)
                        && allMinos[3].canMoveDown(len);
            case 2:
                return allMinos[0].canMoveDown(len)
                        && allMinos[2].canMoveDown(len)
                        && allMinos[3].canMoveDown(len);
            case 3:
                return allMinos[0].canMoveDown(len)
                        && allMinos[1].canMoveDown(len);
        }
        return false;
    }

    @Override
    public boolean canMoveRight() {
        switch (getStatus()) {
            case 0:
                return allMinos[1].canMoveRight()
                        && allMinos[3].canMoveRight();
            case 1:
                return allMinos[0].canMoveRight()
                        && allMinos[2].canMoveRight()
                        && allMinos[3].canMoveRight();
            case 2:
                return allMinos[0].canMoveRight()
                        && allMinos[1].canMoveRight();
            case 3:
                return allMinos[1].canMoveRight()
                        && allMinos[2].canMoveRight()
                        && allMinos[3].canMoveRight();
        }
        return false;
    }

    @Override
    public boolean canMoveLeft() {
        switch (getStatus()) {
            case 0:
                return allMinos[0].canMoveLeft()
                        && allMinos[1].canMoveLeft();
            case 1:
                return allMinos[1].canMoveLeft()
                        && allMinos[2].canMoveLeft()
                        && allMinos[3].canMoveLeft();
            case 2:
                return allMinos[0].canMoveLeft()
                        && allMinos[3].canMoveLeft();
            case 3:
                return allMinos[0].canMoveLeft()
                        && allMinos[2].canMoveLeft()
                        && allMinos[3].canMoveLeft();
        }
        return false;
    }

    /*
       0          1 0         3 2 1         3
       1 2 3      2               0         2
                  3                       0 1
                  pivot is at $2
     */
    private static double[][] rotationData = {
            {-1.5, -1.5, -1.5, -0.5, -0.5, -0.5, 0.5, -0.5},
            {0.5, -1.5, -0.5, -1.5, -0.5, -0.5, -0.5, 0.5},
            {0.5, 0.5, 0.5, -0.5, -0.5, -0.5, -1.5, -0.5},
            {-1.5, 0.5, -0.5, 0.5, -0.5, -0.5, -0.5, -1.5}
    };

    @Override
    public double[][] getRotatingData() {
        return rotationData;
    }
}
