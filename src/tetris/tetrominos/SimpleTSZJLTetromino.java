/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  base class of T,S, Z, J, L, T shape
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.tetrominos;


import tetris.tetrominos.Grid;

public abstract class SimpleTSZJLTetromino extends SimpleTetromino {
    private static int[][] wallKickData = {
            {0, 0, -1, 0, -1, +1, 0, -2, -1, -2}
            , {0, 0, +1, 0, +1, -1, 0, +2, +1, +2}
            , {0, 0, +1, 0, +1, +1, 0, -2, +1, -2}
            , {0, 0, -1, 0, -1, -1, 0, +2, -1, +2}
    };

    protected SimpleTSZJLTetromino(Grid grid) {
        super(grid);
    }

    @Override
    public int[][] getWallKickData() {
        return wallKickData;
    }
}