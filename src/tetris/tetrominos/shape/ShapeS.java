/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  tetromino S
 *  @author: $Author$
 *  @date:   $Date$
 */package tetris.tetrominos.shape;

import javafx.geometry.Point2D;
import tetris.api.Grid;
import tetris.tetrominos.SimpleTSZJLTetromino;

public class ShapeS extends SimpleTSZJLTetromino {
    public ShapeS(Grid grid) {
        super(grid);
        setCssClass("shapeS");
    }

    @Override
    public final Point2D getInitialBoundingBoxOffset() {
        return new Point2D(-1.5, -1.5);
    }


    /*
         2 3    0              3
       0 1      1 2      1 0   2 1
                  3    3 2       0
       pivot is always at the centre of %1
    */
    private final static double[][] rotationData = {
            {-1.5, -0.5, -0.5, -0.5, -0.5, -1.5, 0.5, -1.5},
            {-0.5, -1.5, -0.5, -0.5, 0.5, -0.5, 0.5, 0.5},
            {0.5, -0.5, -0.5, -0.5, -0.5, 0.5, -1.5, 0.5},
            {-0.5, 0.5, -0.5, -0.5, -1.5, -0.5, -1.5, -1.5}
    };




    @Override
    public final double[][] getRotatingData() {
        return rotationData;
    }
}
