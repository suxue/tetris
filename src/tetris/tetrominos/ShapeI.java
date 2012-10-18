/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  tetromino I
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.tetrominos;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;


/*

   position is same as the pivot, right at the center of this shape
   when rotating, the position(aka pivot) won't change its position relative to grid
  -----------------
  | 0 | 1 | 2 | 3 |      case 0, 2
  -----------------

  -----
  | 0 |
  -----
  | 1 |
  -----     case 1, 3
  | 2 |
  -----
  | 3 |
  -----
*/
public final class ShapeI extends Tetromino {

    private static double[][] rotationData = {
            {-2, -1, -1, -1, 0, -1, 1, -1}
            , {0, -2, 0, -1, 0, 0, 0, 1}
            , {-2, 0, -1, 0, 0, 0, 1, 0}
            , {-1, -2, -1, -1, -1, 0, -1, 1}
    };


    @Override
    public final double[][] getRotatingData() {
        return rotationData;
    }


    @Override
    protected void setStyle(Shape s) {
        super.setStyle(s);
        s.setFill(Color.DEEPSKYBLUE);
        s.setStroke(Color.PURPLE);
    }

    public ShapeI(Grid grid) {
        super(grid);
        setCssClass("shapeI");
    }


    @Override
    public final Point2D getInitialBoundingBoxOffset() {
        return new Point2D(-2, -1);
    }


    // only clockwise
    private static int[][] wallKickData = {
            {0, 0, -2, 0, +1, 0, -2, -1, +1, +2}
            , {0, 0, -1, 0, +2, 0, -1, +2, +2, -1}
            , {0, 0, +2, 0, -1, 0, 2, 1, -1, -2}
            , {0, 0, +1, 0, -2, 0, +1, -2, -2, +1}
    };


    @Override
    public final int[][] getWallKickData() {
        return wallKickData;
    }
}
