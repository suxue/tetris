package tetris.tetrominos;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.List;

import tetris.api.Tetromino;
import tetris.tetrominos.TetrisGrid.CellPool;


/*

    position is same as the pivot, right at the center of this shape
    when rotating, the position(aka pivot) won't change its position relative to grid
   -----------------
   | 0 | 1 | 2 | 3 |
   -----------------
 */
public final class IShape extends SimpleTetromino implements Tetromino {


    private void relayoutToHorizontal() {
        Cell tmpCell;

        for (int i = 0; i < 4; i++) {
            tmpCell = tetrominoCells[i];
            tmpCell.getCellXProperty().bind(xProperty().subtract(2 - i));
            tmpCell.getCellYProperty().bind(yProperty().subtract(0.5));
        }
    }

    public IShape(CellPool cellPool) {
        tetrominoCells = new Cell[4];
        List<Cell> tmpList = cellPool.retrieveLast(4);
        tmpList.toArray(tetrominoCells);
        cellPool.removeLast(4);
        setColor(Color.BLUE);
        relayoutToHorizontal();
    }

    @Override
    public void setToTopMiddle(TetrisGrid g) {
        xProperty().set(g.getColumnNumber() / 2);
        yProperty().set(0.5);
    }

}
