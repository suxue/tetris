package tetris.api;

import javafx.scene.shape.Shape;

public class SimpleTetromino implements  Tetromino {


    private Orientation orientation;


    @Override
    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public void setOrientation(Orientation pointTo) {
        assert pointTo == Orientation.UP || pointTo == Orientation.DOWN
                || pointTo == Orientation.LEFT || pointTo == Orientation.RIGHT
                : "invalid oerientation";
        orientation = pointTo;
    }

    @Override
    public Shape getUnitedShape() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean intersectsWith(Tetromino dest) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean intersectsWith(Tetromino dest, Orientation destOrientation) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean intersectsWith(GameState gs) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
