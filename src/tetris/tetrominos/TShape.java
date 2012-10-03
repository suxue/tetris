package tetris.tetrominos;

import java.util.List;

import javafx.scene.paint.Color;

import tetris.tetrominos.TetrisGrid.CellPool;

public class TShape extends SimpleTetromino{
    
    
    public TShape(CellPool cellPool) {
        super();
        tetrominoCells = new Cell[4];
        List<Cell> tmpList = cellPool.retrieveLast(4);
        tmpList.toArray(tetrominoCells);
        cellPool.removeLast(4);
        setColor(Color.PURPLE);

        tetrominoCells[0].getCellXProperty().bind(xProperty().subtract(1));
        tetrominoCells[0].getCellYProperty().bind(yProperty());

        tetrominoCells[1].getCellXProperty().bind(xProperty());
        tetrominoCells[1].getCellYProperty().bind(yProperty());

        tetrominoCells[2].getCellXProperty().bind(xProperty());
        tetrominoCells[2].getCellYProperty().bind(yProperty().subtract(1));

        tetrominoCells[3].getCellXProperty().bind(xProperty().add(1));
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
        if (tetrominoCells[0].canMoveDown(len) && tetrominoCells[1].canMoveDown(len) && tetrominoCells[3].canMoveDown(len)) {
            yProperty().set(yProperty().get() + len);
            return true;
        } else
            return false;
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
        if ( tetrominoCells[3].canMoveRight(1)) {
            xProperty().set(xProperty().get() + 1);
            return true;
        } else
            return false;
    }

    @Override
    public boolean rotate() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canRotate() {
        // TODO Auto-generated method stub
        return false;
    }
}
