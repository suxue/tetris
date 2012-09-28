package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import tetris.api.game.Game;
import tetris.core.GameBoard;
import tetris.tetrominos.shape.IShape;

import java.util.ArrayList;
import java.util.List;


public class TetrisGrid extends AnchorPane {

    // game is 10x20

    private DoubleProperty cellWidth = new SimpleDoubleProperty();
    private DoubleProperty cellHeight = new SimpleDoubleProperty();

    private int columnNumber;
    private int rowNumber;


    ReadOnlyDoubleProperty cellWidthProperty() {
        return cellWidth;
    }

    ReadOnlyDoubleProperty cellHeighthProperty() {
        return cellHeight;
    }


    private CellPool cellPool = null;
    private Game game = null;


    public TetrisGrid(Game game, Paint fill, int rowNo, int columnNo
                     ,  ObservableDoubleValue  boundWidthProperty
                    ,   ObservableDoubleValue  boundHeightProperty) {
        super();
        this.game = game;
        this.columnNumber = columnNo;
        this.rowNumber    = rowNo;
        Rectangle background = new Rectangle();
        background.setFill(fill);
        getChildren().add(background);
        background.widthProperty().bind(boundWidthProperty);
        background.heightProperty().bind(boundHeightProperty);
        cellWidth.bind(background.widthProperty().divide(columnNumber));
        cellHeight.bind(background.heightProperty().divide(rowNumber));
    }

    public CellPool getCellPool() {
        if (cellPool  == null) {
            cellPool = new CellPool();
            // initialize cell pool
            for (int i = 0; i < columnNumber * rowNumber; i++) {
                getCellPool().add(new Cell());
            }
        }
        return cellPool;
    }

}
