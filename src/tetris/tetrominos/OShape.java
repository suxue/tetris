/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  O shape tetromino 
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.tetrominos;

import tetris.api.Grid;
import tetris.api.Tetromino;

/*
 *    ---------
 *    | 0 | 1 |
 *    ---------
 *    | 2 | 3 |
 *    ---------
 *    color:Yellow
*/
public class OShape extends SimpleTetromino {
    public OShape(Grid grid) {
        super();
        allMinos =  grid.allocateMinos(4);
        setCssClass("oShape");

        allMinos[0].getMinoXProperty().bind(xProperty().subtract(1));
        allMinos[0].getMinoYProperty().bind(yProperty().subtract(1));

        allMinos[1].getMinoXProperty().bind(xProperty());
        allMinos[1].getMinoYProperty().bind(yProperty().subtract(1));

        allMinos[2].getMinoXProperty().bind(xProperty().subtract(1));
        allMinos[2].getMinoYProperty().bind(yProperty());

        allMinos[3].getMinoXProperty().bind(xProperty());
        allMinos[3].getMinoYProperty().bind(yProperty());
    }


    @Override
    public BoundingBox getBoundingBox() {
        return (new BoundingBox(
                allMinos[0].getMinoXProperty().get()
                , allMinos[0].getMinoYProperty().get()
        ));
    }

    @Override
    public void setBoundingBox(BoundingBox bb) {
        xProperty().set(bb.getX() + 2);
        yProperty().set(bb.getY() + 1);
    }

    @Override
    public int[][] getRotatingData() {
        throw  new RuntimeException();
    }

    @Override
    public void moveDown(double len) {
        yProperty().set(yProperty().get() + len);
    }

    @Override
    public void moveLeft() {
        xProperty().set(xProperty().get() - 1);
    }

    @Override
    public void moveRight() {
        xProperty().set(xProperty().get() + 1);
    }

    @Override
    public boolean canMoveDown(double len) {
        if (allMinos[2].canMoveDown(len)
              && allMinos[3].canMoveDown(len) ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canMoveLeft() {
        return allMinos[0].canMoveLeft(1) && allMinos[2].canMoveLeft(1);
    }

    @Override
    public void rotateRight() {
        // do nothing
    }

    @Override
    public boolean canMoveRight() {
        return allMinos[1].canMoveRight(1) && allMinos[3].canMoveRight(1);
    }

    @Override
    public boolean canRotateRight() {
        return true;
    }

}
