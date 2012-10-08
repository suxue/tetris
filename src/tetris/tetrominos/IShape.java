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
    public Point2D getInitialBoundingBoxOffset() {
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

    // only clockwise
    private static int[][] wallKickData = {
        {0, 0, -2, 0, +1, 0, -2,-1, +1,+2}
        ,{ 0, 0,-1, 0,+2, 0,-1,+2,+2,-1}
        ,{ 0, 0 ,+2, 0 ,-1, 0, 2, 1, -1,-2}
        ,{ 0, 0, +1, 0, -2, 0, +1,-2, -2,+1 }
    };

    @Override
    public boolean rotate(boolean clockWise) {
        Point2D pivot = getPivot();
        double kickOffset;
        {
            double y = pivot.getY();
            double t = Math.ceil(y) - y;
            kickOffset = (t > 0 && t < 0.2) ? t: 0;
        }

        int st;
        {
            st = getStatus() + (clockWise ? 1 : -1);
            st = (st == -1) ? 3 : st;
            st = (st == 4) ? 0 : st;
        }
        int[]  rd = getRotatingData()[st];
        int[]  wd;
        if (clockWise) {
            wd = wallKickData[getStatus()];
        } else {
            int[] p = wallKickData[st];
            wd      = new int[10];
            for (int i=0; i<10; i++) {
                wd[i] = -p[i];
            }
        }



        double x;
        double y;
        int    floor;
        int    ceil;
        int    i = 0;
        int    j = 0;
        int    k = 0;
        for (i=0; i < 5; i++) {
            for (j=0; j < 4; j++) {
                x = pivot.getX() +  wd[i*2]   + rd[j*2];
                y = pivot.getY() +  wd[i*2+1] + rd[j*2 + 1];
                floor = (int)Math.floor(y);
                ceil  = (int)Math.ceil(y);
                if (! hostGrid.isAccessible((int) x, floor)) {
                    break;
                }

                if (floor != ceil && ! hostGrid.isAccessible((int)x, ceil)) {
                    break;
                }
            }

            if (j == 4) {
                break;
            }

            // check kicked postion
            if (kickOffset != 0) {
                for (k=0; k < 4; k++) {
                    x = pivot.getX() + wd[i*2]     +  rd[k*2];
                    y = pivot.getY() + wd[i*2 + 1] +  rd[k*2 + 1] + kickOffset;
                    if (! hostGrid.isAccessible((int) x, (int) y)) {
                        break;
                    }
                }

                if (k == 4) {
                    break;
                }
            }
        }

        if (i == 5) {
            // fail completely
            return false;
        }

        if (j == 4) {
            setPivot(new Point2D(pivot.getX() + wd[i*2], pivot.getY() + wd[i*2+1]));
        } else if (k == 4) {
            setPivot(new Point2D(pivot.getX() + wd[i*2], pivot.getY() + wd[i*2+1] + kickOffset));
        } else {
            throw  new RuntimeException();
        }


        setStatus(st);
        rebindMinos();
        return true;
    }
}
