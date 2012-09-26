package tetris.api;

import javafx.scene.shape.Shape;

// a shape can mostly has four orientation
//  see http://tetrisconcept.net/wiki/Orientation
//  they are: Point UP|RIGHT|DOWN|LEFT
public interface Tetromino {
    enum Orientation {
        UP, LEFT, RIGHT, DOWN
    }

    Orientation getOrientation();
    void setOrientation(Orientation pointTo);

    Shape getUnitedShape();

    // test if intersects with other tetrominos
    // or boundary
    boolean intersectsWith(Tetromino dest);
    boolean intersectsWith(Tetromino dest, Orientation destOrientation);
    boolean intersectsWith(GameState gs);
}
