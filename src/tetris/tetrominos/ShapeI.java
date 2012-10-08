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
import tetris.api.Grid;


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
public final class ShapeI extends SimpleTetromino {

    private static double[][] rotationData = {
                {-2, -1, -1, -1,  0, -1,  1, -1}
            ,   { 0, -2,  0, -1,  0,  0,  0,  1}
            ,   {-2,  0, -1,  0,  0,  0,  1,  0}
            ,   {-1, -2, -1, -1, -1,  0, -1,  1}
    };


    @Override
    public final double[][] getRotatingData() {
        return rotationData;
    }



    public ShapeI(Grid grid) {
        super(grid);
        setCssClass("shapeI");
    }


    @Override
    public Point2D getInitialBoundingBoxOffset() {
        return new Point2D(-2, -1);
    }


    @Override
    public boolean canMoveDown(double len) {
        if (getStatus() % 2 == 0) { // case 0 & 2
            if (allMinos[0].canMoveDown(len)
                && allMinos[1].canMoveDown(len)
                && allMinos[2].canMoveDown(len)
                && allMinos[3].canMoveDown(len) ) {
                return true;
            }
        } else {  // case 1, 3
            if (allMinos[3].canMoveDown(len)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canMoveLeft() {
        if (getStatus() % 2 == 0) { // case 0 & 2
            return allMinos[0].canMoveLeft();
        } else {  // case 1, 3
            return allMinos[0].canMoveLeft()
                   && allMinos[1].canMoveLeft()
                    && allMinos[2].canMoveLeft()
                    && allMinos[3].canMoveLeft();
        }
    }

    @Override
    public boolean canMoveRight() {
        if (getStatus() % 2 == 0) { // case 0 & 2
            return allMinos[3].canMoveRight();
        } else {  // case 1, 3
            return allMinos[0].canMoveRight()
                    && allMinos[1].canMoveRight()
                    && allMinos[2].canMoveRight()
                    && allMinos[3].canMoveRight();
        }
    }

    // only clockwise
    private static int[][] wallKickData = {
        {0, 0, -2, 0, +1, 0, -2,-1, +1,+2}
        ,{ 0, 0,-1, 0,+2, 0,-1,+2,+2,-1}
        ,{ 0, 0 ,+2, 0 ,-1, 0, 2, 1, -1,-2}
        ,{ 0, 0, +1, 0, -2, 0, +1,-2, -2,+1 }
    };


    @Override
    public final int[][] getWallKickData() {
        return  wallKickData;
    }
}
