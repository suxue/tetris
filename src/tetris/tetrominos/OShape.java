/* One of seven possible tetromino shapes
   Copyright (C) 2012, thu10e team.
   This file is part of the implementaion of Tetris Game  made by thu10e team
   for the assessment of COMP1110/67 ** 10 assignment.
 */
package tetris.tetrominos;

import javafx.scene.paint.Color;
import tetris.tetrominos.TetrisGrid.CellPool;

import java.util.List;

/*
 *    ---------
 *    | 0 | 1 |
 *    ---------
 *    | 2 | 3 |
 *    ---------
 *    color:Yellow
*/
public class OShape extends SimpleTetromino {
    public OShape(CellPool cellPool) {
        super();
        tetrominoCells = new Cell[4];
        List<Cell> tmpList = cellPool.retrieveLast(4);
        tmpList.toArray(tetrominoCells);
        cellPool.removeLast(4);
        setColor(Color.YELLOW);

        tetrominoCells[0].getCellXProperty().bind(xProperty().subtract(1));
        tetrominoCells[0].getCellYProperty().bind(yProperty().subtract(1));

        tetrominoCells[1].getCellXProperty().bind(xProperty());
        tetrominoCells[1].getCellYProperty().bind(yProperty().subtract(1));

        tetrominoCells[2].getCellXProperty().bind(xProperty().subtract(1));
        tetrominoCells[2].getCellYProperty().bind(yProperty());

        tetrominoCells[3].getCellXProperty().bind(xProperty());
        tetrominoCells[3].getCellYProperty().bind(yProperty());
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
        if (tetrominoCells[2].canMoveDown(len) && tetrominoCells[3].canMoveDown(len)) {
            yProperty().set(yProperty().get() + len);
            return true;
        } else
            return false;
    }

    @Override
    public boolean moveLeft() {
        if (tetrominoCells[0].canMoveLeft(1) && tetrominoCells[2].canMoveLeft(1)) {
            xProperty().set(xProperty().get() - 1);
            return true;
        } else
            return false;
    }

    @Override
    public boolean moveRight() {
        if (tetrominoCells[1].canMoveRight(1) && tetrominoCells[3].canMoveRight(1)) {
            xProperty().set(xProperty().get() + 1);
            return true;
        } else
            return false;
    }
}
