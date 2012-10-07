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

import javafx.scene.paint.Color;
import tetris.api.Grid;



/*

    position is same as the pivot, right at the center of this shape
    when rotating, the position(aka pivot) won't change its position relative to grid
   -----------------
   | 0 | 1 | 2 | 3 |
   -----------------
 */
public final class IShape extends SimpleTetromino {


    private void relayoutToHorizontal() {
        Mino tmpMino;

        for (int i = 0; i < 4; i++) {
            tmpMino = tetrominoMinos[i];
            tmpMino.getMinoXProperty().bind(xProperty().subtract(2 - i));
            tmpMino.getMinoYProperty().bind(yProperty().subtract(0.5));
        }
    }

    public IShape(Grid grid) {
        tetrominoMinos = grid.allocateMinos(4);
        setColor(Color.BLUE);
        relayoutToHorizontal();
    }

    @Override
    public final double getPivotXShift() {
        return 2;
    }

    @Override
    public final double getPivotYShift() {
        return 0.5;
    }

    @Override
    public void moveDown(double len) {
        yProperty().set(yProperty().get() + len);
    }

    @Override
    public void moveLeft() {
        xProperty().set(xProperty().get() - 1);
    }

    @Override
    public void moveRight() {
        xProperty().set(xProperty().get() + 1);
    }

    @Override
    public boolean canMoveDown(double len) {
        if (tetrominoMinos[0].canMoveDown(len)
            && tetrominoMinos[1].canMoveDown(len)
            && tetrominoMinos[2].canMoveDown(len)
            && tetrominoMinos[3].canMoveDown(len) ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canMoveLeft() {
        return tetrominoMinos[0].canMoveLeft(1);
    }

    @Override
    public boolean canMoveRight() {
        return tetrominoMinos[3].canMoveRight(1);
    }
}
