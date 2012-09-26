package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class TetrisGrid extends Rectangle {

    // game is 10x20
    private static final int columnNumber = 10;
    private static final int rowNumber    = 20;

    private DoubleProperty cellWidth = new SimpleDoubleProperty();
    private DoubleProperty cellHeight = new SimpleDoubleProperty();

    ReadOnlyDoubleProperty cellWidthProperty() {
        return cellWidth;
    }

    ReadOnlyDoubleProperty cellHeighthProperty() {
        return cellHeight;
    }


    class CellPool extends  ArrayList<Cell> {}
    private CellPool cellPool = new CellPool();

    public TetrisGrid() {
        super();
        setFill(Color.WHITE);
        cellWidth.bind(widthProperty().divide(columnNumber));
        cellHeight.bind(heightProperty().divide(rowNumber));

        // initialize cell pool
        for (int i = 0; i < columnNumber * rowNumber; i++) {
            getCellPool().add(new Cell(this));
        }
    }

    CellPool getCellPool() {
        return cellPool;
    }


}
