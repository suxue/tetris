package tetris.tetrominos;



import javafx.scene.paint.Color;
import tetris.tetrominos.TetrisGrid.CellPool;

import java.util.List;

/*
*    -----
*    | 0 |
*    -----
*    | 1 |
*    ---------
*    | 2 | 3 |
*    ---------
*    color:Orange
*/
public class LShape extends SimpleTetromino {
 public LShape(CellPool cellPool) {
     super();
     tetrominoCells = new Cell[4];
     List<Cell> tmpList = cellPool.retrieveLast(4);
     tmpList.toArray(tetrominoCells);
     cellPool.removeLast(4);
     setColor(Color.ORANGE);

     tetrominoCells[0].getCellXProperty().bind(xProperty().subtract(2));
     tetrominoCells[0].getCellYProperty().bind(yProperty());

     tetrominoCells[1].getCellXProperty().bind(xProperty().subtract(1));
     tetrominoCells[1].getCellYProperty().bind(yProperty());

     tetrominoCells[2].getCellXProperty().bind(xProperty());
     tetrominoCells[2].getCellYProperty().bind(yProperty());

     tetrominoCells[3].getCellXProperty().bind(xProperty());
     tetrominoCells[3].getCellYProperty().bind(yProperty().subtract(1));
 }

 @Override
 public final double getPivotXShift() {
     return 0;
 }

 @Override
 public final double getPivotYShift() {
     return 1;
 }

 @Override
 public boolean moveDown(double len) {
     if (tetrominoCells[0].canMoveDown(len) && tetrominoCells[1].canMoveDown(len) && tetrominoCells[2].canMoveDown(len)) {
         yProperty().set(yProperty().get() + len);
         return true;
     } else
         return false;
 }

 @Override
 public boolean moveLeft() {
     if (tetrominoCells[0].canMoveLeft(1) ) {
         xProperty().set(xProperty().get() - 1);
         return true;
     } else
         return false;
 }

 @Override
 public boolean moveRight() {
     if (tetrominoCells[2].canMoveRight(1) && tetrominoCells[3].canMoveRight(1)) {
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

