package tetris.tetrominos.shape;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import tetris.tetrominos.Cell;
import tetris.tetrominos.CellPool;
import tetris.tetrominos.TetrisGrid;
import tetris.tetrominos.Tetromino;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/*

    position is same as the pivot, right at the center of this shape
    when rotating, the position(aka pivot) won't change its position relative to grid
   -----------------
   | 0 | 1 | 2 | 3 |
   -----------------
 */
public final class IShape extends Tetromino {
    private static Paint color = Color.BLUE;
    private final List<Cell> allCells;
    private boolean isHorizontal;


    private void relayoutToHorizontal() {
        Cell tmpCell;
        for (int i = 0; i < 4; i++) {
            tmpCell = allCells.get(i);
            tmpCell.getCellXProperty().bind(xProperty().subtract(2 - i));
            tmpCell.getCellYProperty().bind(yProperty().subtract(0.5));
        }
        isHorizontal = true;
    }

    private void relayoutToVetical() {
        Cell tmpCell;

        for (int i = 0; i < 4; i++) {
            tmpCell = allCells.get(i);
            tmpCell.getCellXProperty().bind(xProperty().subtract(0.5));
            tmpCell.getCellYProperty().bind(yProperty().subtract(2 - i));
        }
        isHorizontal = false;
    }

    private void setColor() {
        for (Cell c : allCells) {
            c.setFill(color);
        }
    }

    public IShape(CellPool cellPool) {
        super();
        allCells = new CopyOnWriteArrayList<Cell>(cellPool.retrieveLast(4));
        cellPool.removeLast(4);
        setColor();
    }

    @Override
    public boolean rotate(Direction dir) {
        // TODO check boundary
        if (isHorizontal) {
            relayoutToVetical();
        } else {
            relayoutToHorizontal();
        }
        return true;
    }

    @Override
    public void attach(TetrisGrid grid) {
        for (Cell c : allCells) {
            c.attach(grid);
        }
        relayoutToHorizontal();
        xProperty().set(2);
        yProperty().set(0.5);
    }

    @Override
    public void detach() {
        for (Cell c : allCells) {
            c.detach();
        }
    }
}
