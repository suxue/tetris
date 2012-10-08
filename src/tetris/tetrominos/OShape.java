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
import tetris.api.Grid;

/*
 *    ---------
 *    | 0 | 1 |
 *    ---------
 *    | 2 | 3 |
 *    ---------
 *    color:Yellow
*/
public class OShape extends SimpleTetromino {
    private static int[] line = {-1, -1, 0, -1, -1, 0, 0, 0};
    private static int[][] data = {line, line, line, line};


    @Override
    public int[][] getRotatingData() {
        return data;
    }


    public OShape(Grid grid) {
        super(grid);
        setCssClass("oShape");
    }



    @Override
    public Point2D getInitialBoundingBoxOffset() {
        return new Point2D(-2, -1);
    }


    @Override
    public boolean canMoveDown(double len) {
        if (allMinos[2].canMoveDown(len)
              && allMinos[3].canMoveDown(len) ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canMoveLeft() {
        return allMinos[0].canMoveLeft() && allMinos[2].canMoveLeft();
    }


    @Override
    public boolean canMoveRight() {
        return allMinos[1].canMoveRight() && allMinos[3].canMoveRight();
    }

    @Override
    public boolean rotate(boolean clockWise) {
        int newSt = getStatus() + (clockWise ? 1 : -1);
        newSt = (newSt < 0) ? (newSt + 4) : newSt;
        newSt = (newSt > 4) ? (newSt - 4) : newSt;
        setStatus(newSt);
        return true;
    }


}
