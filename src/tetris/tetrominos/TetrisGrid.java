/* The playyard and container for tetrominos
   Copyright (C) 2012, thu10e team.
   This file is part of the implementaion of Tetris Game  made by thu10e team
   for the assessment of COMP1110/67 ** 10 assignment.
 */
package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TetrisGrid extends AnchorPane {

    private Rectangle background = new Rectangle();
    private CellPool cellPool = null;
    private ArrayList<Cell> visibleCells = null;

    private DoubleProperty cellWidth = new SimpleDoubleProperty();
    private DoubleProperty cellHeight = new SimpleDoubleProperty();

    private Cell[][] mirror;

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

    private void resetMirror() {
        for (Cell[] i : mirror) {
            Arrays.fill(i, null);
        }
    }

    public final boolean mirrorGet(int x, int y) {
        return !(mirror[x][y] == null);
    }

    public final void mirrorSet(int x, int y, Cell c) {
        mirror[x][y] = c;
    }


    // test whole lines and erase them
    // return the answer to 'how many lines have been erased?'
    public final int squeeze() {
        boolean[] wholeFlags = new boolean[getRowNumber()];
        Arrays.fill(wholeFlags, false);


        int cellCountInALine;
        for (int i = 0; i < getRowNumber(); i++) {
            cellCountInALine = 0;
            for (int j = 0; j < getColumnNumber(); j++) {
                if (mirrorGet(j, i))
                    cellCountInALine++;
            }

            if (cellCountInALine == getColumnNumber())
                wholeFlags[i] = true;
        }


        int emptyLines = 0;
        for (int i = getRowNumber() - 1; i >= 0; i--) {
            if (wholeFlags[i]) { // clear this line
                for (int j = 0; j < getColumnNumber(); j++) {
                    mirror[j][i].detach();
                    getCellPool().add(mirror[j][i]);
                    mirror[j][i] = null;
                }
                emptyLines++;
            } else { // move down this line emptyLines
                if (emptyLines > 0) {
                    for (int j = 0; j < getColumnNumber(); j++) {
                        // move geometrically
                        if (mirror[j][i] != null) {
                            mirror[j][i].getCellYProperty().set(mirror[j][i].getCellYProperty().get() + emptyLines);
                        }

                        // move line within mirror
                        // int destLine = i + emptyLines;
                        mirror[j][i + emptyLines] = mirror[j][i];
                        mirror[j][i] = null;
                    }
                }
            }
        }

        return emptyLines;
    }


    public TetrisGrid(Paint fill, int rowNo, int columnNo
            , ObservableDoubleValue boundWidthProperty
            , ObservableDoubleValue boundHeightProperty) {
        super();
        mirror = new Cell[columnNo][rowNo];
        resetMirror();

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
            super(columnNumber * rowNumber);
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

            // clean all sunk cells
            resetMirror();
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

