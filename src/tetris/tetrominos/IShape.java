package tetris.tetrominos;

import javafx.scene.paint.Color;
import tetris.tetrominos.TetrisGrid.CellPool;

import java.util.List;


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
            tmpCell.getCellYProperty().bind(yProperty());
        }
    }

    public IShape(CellPool cellPool) {
        tetrominoCells = new Cell[4];
        List<Cell> tmpList = cellPool.retrieveLast(4);
        tmpList.toArray(tetrominoCells);
        cellPool.removeLast(4);
        setColor(Color.CYAN);
        relayoutToHorizontal();
    }

    @Override
    public final double getPivotXShift() {
        return 2;
    }

    @Override
    public final double getPivotYShift() {
        return 1;
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

    @Override
    public boolean rotate() {
        if(canRotate()){
        tetrominoCells[0].getCellXProperty().unbind();
        tetrominoCells[0].getCellYProperty().unbind();
        tetrominoCells[0].getCellXProperty().bind(xProperty());
        tetrominoCells[0].getCellYProperty().bind(yProperty().subtract(2));

        tetrominoCells[0].getCellXProperty().unbind();
        tetrominoCells[0].getCellYProperty().unbind();
        tetrominoCells[1].getCellXProperty().bind(xProperty());
        tetrominoCells[1].getCellYProperty().bind(yProperty());

      //  tetrominoCells[2].getCellXProperty().bind(xProperty().subtract(1));
      //  tetrominoCells[2].getCellYProperty().bind(yProperty());

        tetrominoCells[0].getCellXProperty().unbind();
        tetrominoCells[0].getCellYProperty().unbind();
        tetrominoCells[3].getCellXProperty().bind(xProperty());
        tetrominoCells[3].getCellYProperty().bind(yProperty().subtract(1));

        return true;
        }
        else return false;
    }

    private int status=0;
    @Override
    public boolean canRotate() {
        
        //switch(status){
        //case 0:
            return !hostGrid.mirrorGetRectangle(-2, -2, 2, 2)  && !hostGrid.mirrorGetRectangle(0, 0, 2, 2);
            //break;
        }
     //   case 1:
        //    return !hostGrid.mirrorGetRectangle(0, -2, 2, 2)  && !hostGrid.mirrorGetRectangle(-2, 0, 2, 2);
        //    break;
     //   case 2:
        //    return !hostGrid.mirrorGetRectangle(-2, -1, 2, 2)  && !hostGrid.mirrorGetRectangle(2, 3, 2, 2);
        //    break;
    //    case 3:
        //    return !hostGrid.mirrorGetRectangle(-2, -2, 2, 2)  && !hostGrid.mirrorGetRectangle(0, 0, 2, 2);
       //     break;
        
        
        
    }
    
    

