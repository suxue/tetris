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
    public OShape(Grid grid) {
        super();
        tetrominoMinos =  grid.allocateMinos(4);
        setCssClass("oShape");

        tetrominoMinos[0].getMinoXProperty().bind(xProperty().subtract(1));
        tetrominoMinos[0].getMinoYProperty().bind(yProperty().subtract(1));

        tetrominoMinos[1].getMinoXProperty().bind(xProperty());
        tetrominoMinos[1].getMinoYProperty().bind(yProperty().subtract(1));

        tetrominoMinos[2].getMinoXProperty().bind(xProperty().subtract(1));
        tetrominoMinos[2].getMinoYProperty().bind(yProperty());

        tetrominoMinos[3].getMinoXProperty().bind(xProperty());
        tetrominoMinos[3].getMinoYProperty().bind(yProperty());
    }

    @Override
    public final double getPivotXShift() {
        return 1;
    }

    @Override
    public final double getPivotYShift() {
        return 1;
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
        if (tetrominoMinos[2].canMoveDown(len)
              && tetrominoMinos[3].canMoveDown(len) ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canMoveLeft() {
        return tetrominoMinos[0].canMoveLeft(1) && tetrominoMinos[2].canMoveLeft(1);
    }

    @Override
    public boolean canMoveRight() {
        return tetrominoMinos[1].canMoveRight(1) && tetrominoMinos[3].canMoveRight(1);
    }
}
