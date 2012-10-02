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
import java.util.List;


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


    public class CellPool extends ArrayList<Cell> {
        private Cell[] allCells;

        public CellPool() {
            super(columnNumber*rowNumber);
            for (int i = 0; i < columnNumber * rowNumber; i++)
                add(new Cell());

            allCells = toArray(new Cell[size()]);
        }

        public void reInitialize() {
            for (int i = 0; i < allCells.length; i++) {
                if (allCells[i].isAttached())
                    allCells[i].detach();
                if (i < size())
                    add(i, allCells[i]);
                else
                    set(i, allCells[i]);
            }
        }

        public List<Cell> retrieveLast(int number) {
            assert number >= 1;
            assert this.size() >= number;

            int end = size();
            int start = end - number;
            return subList(start, end);
        }

        public void removeLast(int number) {
            assert size() >= number;
            for (int i = 0; i < number; i++)
                remove(size() - 1);
        }
    }

    public CellPool getCellPool() {
        if (cellPool == null)
            cellPool = new CellPool();

        return cellPool;
    }

}

