package tetris.tetrominos;

import javafx.geometry.Point2D;
import tetris.api.Grid;

public class TShape extends  SimpleTSZJLTetromino {

    public TShape(Grid grid) {
        super(grid);
        setCssClass("shapeT");
    }

    @Override
    public Point2D getInitialBoundingBoxOffset() {
        return new Point2D(-1.5, -1.5);
    }



    @Override
    public boolean canMoveRight() {
        switch (getStatus()) {
            case 0:
            case 2:
                return allMinos[3].canMoveRight();
            case 1:
                return allMinos[0].canMoveRight();
            case 3:
                return allMinos[1].canMoveRight()
                        && allMinos[2].canMoveRight()
                        && allMinos[3].canMoveRight();
            default:
                return false;
        }
    }


    @Override
    public boolean canMoveLeft() {
        switch (getStatus()) {
            case 0:
            case 2:
                return allMinos[1].canMoveLeft();
            case 1:
                return allMinos[1].canMoveLeft()
                        && allMinos[2].canMoveLeft()
                        && allMinos[3].canMoveLeft();
            case 3:
                return allMinos[0].canMoveLeft();
            default:
                return false;
        }
    }


    @Override
    public boolean canMoveDown(double len) {
        switch (getStatus()) {
            case 0:
                return allMinos[1].canMoveDown(len)
                        && allMinos[2].canMoveDown(len)
                        && allMinos[3].canMoveDown(len);
            case 1:
            case 3:
                return allMinos[3].canMoveDown(len);
            case 2:
                return allMinos[0].canMoveDown(len);
            default:
                return false;
        }
    }

    //     0      1                   1
    //   1 2 3    2 0      1 2 3    0 2
    //            3          0        3
    private static double[][] rotationData = {
            {-0.5, -1.5, -1.5, -0.5, -0.5, -0.5, 0.5, -0.5}
            , {0.5, -0.5, -0.5, -1.5, -0.5, -0.5, -0.5, 0.5}
            , {-0.5, 0.5, -1.5, -0.5, -0.5, -0.5, 0.5, -0.5}
            , {-1.5, -0.5, -0.5, -1.5, -0.5, -0.5, -0.5, 0.5}
    };

    @Override
    public double[][] getRotatingData() {
        return rotationData;
    }
}
