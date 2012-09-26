package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tetris.api.game.Game;


public class TetrisGrid extends Rectangle {

    // game is 10x20
    private static final int columnNumber = 10;
    private static final int rowNumber    = 20;

    private DoubleProperty cellWidthProperty = new SimpleDoubleProperty();
    private DoubleProperty cellHeightProperty = new SimpleDoubleProperty();

    public TetrisGrid() {
        super();
        setFill(Color.WHITE);
        cellWidthProperty.bind(widthProperty().divide(columnNumber));
        cellHeightProperty.bind(heightProperty().divide(rowNumber));
    }


    // maintain a inner cell buffer
}
