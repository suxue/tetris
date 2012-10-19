/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  tetromino L
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.tetrominos;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public final class ShapeL extends ShapeJLSTZ {
    @Override
    protected void setStyle(Shape s) {
        super.setStyle(s);
        s.setFill(Color.DARKORANGE);
    }

    public ShapeL(Grid grid) {
        super(grid);
        setCssClass("shapeL");
    }

    @Override
    public final Point2D getInitialBoundingBoxOffset() {
        return new Point2D(-1.5, -1.5);
    }


    /*       3      0
         0 1 2      1        2 1 0       3 2
                    2 3      3             1
                                           0
           pivot is at centre of %1
     */
    private final static double[][] rotationData = {
            {-1.5, -0.5, -0.5, -0.5, 0.5, -0.5, 0.5, -1.5},
            {-0.5, -1.5, -0.5, -0.5, -0.5, 0.5, 0.5, 0.5},
            {0.5, -0.5, -0.5, -0.5, -1.5, -0.5, -1.5, 0.5},
            {-0.5, 0.5, -0.5, -0.5, -0.5, -1.5, -1.5, -1.5}
    };

    @Override
    public final double[][] getRotatingData() {
        return rotationData;
    }
}
