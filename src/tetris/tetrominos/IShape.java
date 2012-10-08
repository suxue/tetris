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
  | 0 | 1 | 2 | 3 |
  -----------------
*/
public final class IShape extends SimpleTetromino {

    private static int[][] rdata = {
                {-2, -1, -1, -1,  0, -1,  1, -1}
            ,   { 0, -2,  0, -1,  0,  0,  0, -2}
            ,   {-2,  0, -1,  0,  0,  0,  1,  0}
            ,   {-1, -2, -1, -1, -1,  0, -1,  1}
    };


    @Override
    public int[][] getRotatingData() {
        return rdata;
    }



    public IShape(Grid grid) {
        super(grid);
        setCssClass("iShape");
    }


    @Override
    public Point2D getBoundingBoxOffset() {
        return new Point2D(-2, -1);
    }


    @Override
    public boolean canMoveDown(double len) {
        if (allMinos[0].canMoveDown(len)
            && allMinos[1].canMoveDown(len)
            && allMinos[2].canMoveDown(len)
            && allMinos[3].canMoveDown(len) ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canMoveLeft() {
        return allMinos[0].canMoveLeft();
    }

    @Override
    public boolean canMoveRight() {
        return allMinos[3].canMoveRight();
    }


    @Override
    protected boolean rotateTo(int status) {
        return false;
    }

}
