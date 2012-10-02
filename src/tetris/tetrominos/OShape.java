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
public class OShape extends  SimpleTetromino {
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

}
