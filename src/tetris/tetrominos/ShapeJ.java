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
package tetris.tetrominos;

import javafx.geometry.Point2D;

public class ShapeJ extends ShapeJLSTZ {
    public ShapeJ(Grid grid) {
        super(grid);
        setCssClass("shapeJ");
    }

    @Override
    public final Point2D getInitialBoundingBoxOffset() {
        return new Point2D(-1.5, -1.5);
    }


    /*
       0          1 0         3 2 1         3
       1 2 3      2               0         2
                  3                       0 1
                  pivot is at $2
     */
    private final static double[][] rotationData = {
            {-1.5, -1.5, -1.5, -0.5, -0.5, -0.5, 0.5, -0.5},
            {0.5, -1.5, -0.5, -1.5, -0.5, -0.5, -0.5, 0.5},
            {0.5, 0.5, 0.5, -0.5, -0.5, -0.5, -1.5, -0.5},
            {-1.5, 0.5, -0.5, 0.5, -0.5, -0.5, -0.5, -1.5}
    };

    @Override
    public final double[][] getRotatingData() {
        return rotationData;
    }
}
