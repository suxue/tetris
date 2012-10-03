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
        Cell tmpCell;

        for (int i = 0; i < 4; i++) {
            tmpCell = tetrominoCells[i];
            tmpCell.getCellXProperty().bind(xProperty().subtract(2 - i));
            tmpCell.getCellYProperty().bind(yProperty().subtract(0.5));
        }
    }

    public IShape(Grid grid) {
        tetrominoCells = grid.allocateCells(4);
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
    public boolean moveDown(double len) {
        for (Cell c : tetrominoCells) {
            if (!c.canMoveDown(len))
                return false;
        }

        yProperty().set(yProperty().get() + len);
        return true;
    }

    @Override
    public boolean moveLeft() {
        if (tetrominoCells[0].canMoveLeft(1)) {
            xProperty().set(xProperty().get() - 1);
            return true;
        } else
            return false;
    }

    @Override
    public boolean moveRight() {
        if (tetrominoCells[3].canMoveRight(1)) {
            xProperty().set(xProperty().get() + 1);
            return true;
        } else
            return false;
    }
}
