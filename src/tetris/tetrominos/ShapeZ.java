/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  tetromino Z
 *  @author: $Author$
 *  @date:   $Date$
 */package tetris.tetrominos;

import javafx.geometry.Point2D;

public class ShapeZ extends ShapeJLSTZ {
    public ShapeZ(Grid grid) {
        super(grid);
        setCssClass("shapeZ");
    }

    @Override
    public final Point2D getInitialBoundingBoxOffset() {
        return new Point2D(-1.5, -1.5);
    }

    /*
         0 1         0                  3
           2 3     2 1      3 2       1 2
                   3          1 0     0
           pivot is always in the centre of $2
     */
    private final static double[][] rotationData = {
            {-1.5, -1.5, -0.5, -1.5, -0.5, -0.5, 0.5, -0.5},
            {0.5, -1.5, 0.5, -0.5, -0.5, -0.5, -0.5, 0.5},
            {0.5, 0.5, -0.5, 0.5, -0.5, -0.5, -1.5, -0.5},
            {-1.5, 0.5, -1.5, -0.5, -0.5, -0.5, -0.5, -1.5}
    };

    @Override
    public final double[][] getRotatingData() {
        return rotationData;
    }
}
