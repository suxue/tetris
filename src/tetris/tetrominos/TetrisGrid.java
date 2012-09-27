package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tetris.api.game.Game;
import tetris.core.GameBoard;
import tetris.tetrominos.shape.IShape;

import java.util.ArrayList;
import java.util.List;


public class TetrisGrid extends AnchorPane {

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


    private CellPool cellPool = new CellPool();
    private Game game = null;


    public TetrisGrid(Game game) {
        super();
        this.game = game;
        cellWidth.bind(widthProperty().divide(columnNumber));
        cellHeight.bind(heightProperty().divide(rowNumber));

        // initialize cell pool
        for (int i = 0; i < columnNumber * rowNumber * 2; i++) {
            getCellPool().add(new Cell());
        }
    }

    public CellPool getCellPool() {
        return cellPool;
    }

}
