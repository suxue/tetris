/* One of seven possible tetromino shapes
   Copyright (C) 2012, thu10e team.
   This file is part of the implementaion of Tetris Game  made by thu10e team
   for the assessment of COMP1110/67 ** 10 assignment.
 */
package tetris.tetrominos;

import javafx.scene.paint.Color;
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
        setColor(Color.YELLOW);

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
    public boolean moveDown(double len) {
        if (tetrominoMinos[2].canMoveDown(len) && tetrominoMinos[3].canMoveDown(len)) {
            yProperty().set(yProperty().get() + len);
            return true;
        } else
            return false;
    }

    @Override
    public boolean moveLeft() {
        if (tetrominoMinos[0].canMoveLeft(1) && tetrominoMinos[2].canMoveLeft(1)) {
            xProperty().set(xProperty().get() - 1);
            return true;
        } else
            return false;
    }

    @Override
    public boolean moveRight() {
        if (tetrominoMinos[1].canMoveRight(1) && tetrominoMinos[3].canMoveRight(1)) {
            xProperty().set(xProperty().get() + 1);
            return true;
        } else
            return false;
    }
}
