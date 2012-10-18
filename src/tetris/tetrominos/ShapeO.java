/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  O shape tetromino 
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.tetrominos;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/*
 *    ---------
 *    | 0 | 1 |
 *    ---------
 *    | 2 | 3 |
 *    ---------
 *    color:Yellow
*/
public class ShapeO extends Tetromino {
    private final static double[] line = {-1, -1, 0, -1, -1, 0, 0, 0};
    private final static double[][] data = {line, line, line, line};

    @Override
    protected void setStyle(Shape s) {
        super.setStyle(s);
        s.setFill(Color.GOLD);
        s.setStroke(Color.DARKBLUE);
    }

    @Override
    public final double[][] getRotatingData() {
        return data;
    }

    @Override
    public final int[][] getWallKickData() {
        // should not reach here
        throw new RuntimeException();
    }


    public ShapeO(Grid grid) {
        super(grid);
        setCssClass("shapeO");
    }


    @Override
    public final Point2D getInitialBoundingBoxOffset() {
        return new Point2D(-2, -1);
    }


    @Override
    public final boolean rotate(boolean clockWise) {
        int newSt = getStatus() + (clockWise ? 1 : -1);
        newSt = (newSt < 0) ? (newSt + 4) : newSt;
        newSt = (newSt > 3) ? (newSt - 4) : newSt;
        setStatus(newSt);
        return true;
    }


}
