package tetris.tetrominos;

import tetris.api.Grid;

public abstract  class SimpleTSZJLTetromino extends  SimpleTetromino{
    private static int[][] wallKickData = {
            {0, 0, -1, 0, -1, +1, 0,-2, -1,-2}
           , {0, 0, +1, 0, +1,-1,  0,+2, +1,+2}
            , {0, 0, +1, 0, +1,+1, 0,-2, +1,-2}
            , { 0, 0, -1, 0, -1,-1,  0,+2, -1,+2}
    };

    protected SimpleTSZJLTetromino(Grid grid) {
        super(grid);
    }

    @Override
    public int[][] getWallKickData() {
        return wallKickData;
    }
}
