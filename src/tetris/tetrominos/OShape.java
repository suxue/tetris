package tetris.tetrominos;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.List;

public class OShape extends  SimpleTetromino {
    @Override
    protected double getPivotXshift() {
        return 2;
    }

    @Override
    protected double getPivotYshift() {
        return 1;
    }


//    ---------
//    | 0 | 1 |
//    ---------
//    | 2 | 3 |
//    ---------
    public OShape(CellPool cellPool) {
        super(Color.YELLOW);
        tetrominoCells = new Cell[4];
        List<Cell> tmpList = cellPool.retrieveLast(4);
        tmpList.toArray(tetrominoCells);
        cellPool.removeLast(4);

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
    public double getLengthToRightBoundary() {
        return hostGrid.getColumnNumber() - xProperty().get() - 1;
    }

    @Override
    public double getLengthToLeftBoundary() {
        return xProperty().get() - 1;
    }

    @Override
    public double getLengthToBottomBoundary() {
        return hostGrid.getRowNumber() - yProperty().get() - 1;
    }

    @Override
    public double getLengthToTopBoundary() {
        return yProperty().get() - 1;
    }
}
