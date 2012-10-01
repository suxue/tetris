package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import tetris.api.Tetromino;

import java.util.ArrayList;
import java.util.Iterator;


public class TetrisGrid extends AnchorPane {

    private Rectangle       background  = new Rectangle();
    private CellPool        cellPool    = null;
    private ArrayList<Cell> visibleCells   = null;

    private DoubleProperty  cellWidth   = new SimpleDoubleProperty();
    private DoubleProperty  cellHeight  = new SimpleDoubleProperty();

    ReadOnlyDoubleProperty cellWidthProperty() {
        return cellWidth;
    }

    ReadOnlyDoubleProperty cellHeighthProperty() {
        return cellHeight;
    }

    private int columnNumber;
    private int rowNumber;

    public final int getColumnNumber() {
        return columnNumber;
    }

    public final int getRowNumber() {
        return rowNumber;
    }



    public void sink(Cell c) {
        if (visibleCells == null) {
            visibleCells = new ArrayList<Cell>();
        }
        visibleCells.add(c);
    }

    public void clearTetrominos() {
        if (visibleCells != null) {
            for (Cell c: visibleCells) {
                c.detach(this);
                getCellPool().add(c);
            }
        }
        visibleCells = null;
    }


    public TetrisGrid(Paint fill, int rowNo, int columnNo
            , ObservableDoubleValue boundWidthProperty
            , ObservableDoubleValue boundHeightProperty) {
        super();
        this.columnNumber = columnNo;
        this.rowNumber = rowNo;
        background.setFill(fill);
        getChildren().add(background);
        background.widthProperty().bind(boundWidthProperty);
        background.heightProperty().bind(boundHeightProperty);
        cellWidth.bind(background.widthProperty().divide(columnNumber));
        cellHeight.bind(background.heightProperty().divide(rowNumber));
    }

    public CellPool getCellPool() {
        if (cellPool == null) {
            cellPool = new CellPool();
            // initialize cell pool
            for (int i = 0; i < columnNumber * rowNumber; i++) {
                getCellPool().add(new Cell());
            }
        }
        return cellPool;
    }

}
