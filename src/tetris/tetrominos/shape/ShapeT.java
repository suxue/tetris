/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  tetromino T
 *  @author: $Author$
 *  @date:   $Date$
 */package tetris.tetrominos.shape;

import javafx.geometry.Point2D;
import tetris.api.Grid;
import tetris.tetrominos.SimpleTSZJLTetromino;

public class ShapeT extends SimpleTSZJLTetromino {

    public ShapeT(Grid grid) {
        super(grid);
        setCssClass("shapeT");
    }

    @Override
    public Point2D getInitialBoundingBoxOffset() {
        return new Point2D(-1.5, -1.5);
    }




    //     0      1                   1
    //   1 2 3    2 0      1 2 3    0 2
    //            3          0        3
    private static double[][] rotationData = {
            {-0.5, -1.5, -1.5, -0.5, -0.5, -0.5, 0.5, -0.5}
            , {0.5, -0.5, -0.5, -1.5, -0.5, -0.5, -0.5, 0.5}
            , {-0.5, 0.5, -1.5, -0.5, -0.5, -0.5, 0.5, -0.5}
            , {-1.5, -0.5, -0.5, -1.5, -0.5, -0.5, -0.5, 0.5}
    };

    @Override
    public double[][] getRotatingData() {
        return rotationData;
    }
}
