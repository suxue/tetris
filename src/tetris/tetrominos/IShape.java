package tetris.tetrominos;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.List;


/*

    position is same as the pivot, right at the center of this shape
    when rotating, the position(aka pivot) won't change its position relative to grid
   -----------------
   | 0 | 1 | 2 | 3 |
   -----------------
 */
public final class IShape extends SimpleTetromino {
    private boolean isHorizontal;


    private void relayoutToHorizontal() {
        Cell tmpCell;
        for (int i = 0; i < 4; i++) {
            tmpCell = tetrominoCells[i];
            tmpCell.getCellXProperty().bind(xProperty().subtract(2 - i));
            tmpCell.getCellYProperty().bind(yProperty().subtract(0.5));
        }
        isHorizontal = true;
    }

    private void relayoutToVetical() {
        Cell tmpCell;

        for (int i = 0; i < 4; i++) {
            tmpCell = tetrominoCells[i];
            tmpCell.getCellXProperty().bind(xProperty().subtract(0.5));
            tmpCell.getCellYProperty().bind(yProperty().subtract(2 - i));
        }
        isHorizontal = false;
    }


    public IShape(CellPool cellPool) {
        super(Color.BLUE);
        tetrominoCells = new Cell[4];
        List<Cell> tmpList = cellPool.retrieveLast(4);
        tmpList.toArray(tetrominoCells);
        cellPool.removeLast(4);
        relayoutToHorizontal();
    }

    @Override
    public final double getPivotXshift() {
        return 2;
    }

    @Override
    public final double getPivotYshift() {
        return 0.5;
    }

}
