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
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import tetris.api.Grid;
import tetris.util.Toolkit;

import java.util.ArrayList;
import java.util.Arrays;


public class TetrisGrid extends AnchorPane implements Grid{


    private CellPool cellPool = null;
    private CollisionChecker collisionChecker = null;
    private CollisionChecker getCollisionChecker() {
        if (collisionChecker == null) {
            collisionChecker = new CollisionChecker();
            collisionChecker.unsetAll();
        }
        return collisionChecker;
    }


    private CellPool getCellPool() {
        if (cellPool == null) {
            cellPool = new CellPool();
        }
        return cellPool;
    }

    @Override
    public boolean cooridinateIsAccessibleWithoutBoundaryCheck(int x, int y) {
        return getCollisionChecker().isAccessibleWithoutBoundaryCheck(x, y);
    }


    private class CollisionChecker {
        private Cell[][] chessBoard;

        void set(int x, int y, Cell c) {}
        void unset(int x, int y) {
            chessBoard[x][y] = null;
        }

        Cell get(int x, int y) {
            return chessBoard[x][y];
        }

        void unsetAll() {
            for (Cell[] i : chessBoard) {
                Arrays.fill(i, null);
            }
        }

        // a Cell can reach/access/occupy this gridblock of (x, y)
        boolean isAccessible(int x, int y) {
            // do boundary check
            if (x >= getColumnNo() || y >= getRowNo()) {
                return false;
            } else {
                return isAccessibleWithoutBoundaryCheck(x, y);
            }
        }

        public boolean isAccessibleWithoutBoundaryCheck(int x, int y) {
            return get(x, y) == null;
        }

        boolean isAccessible(int x, int y, int width, int height) {
            if (x >= getColumnNo() || y >= getRowNo()) {
                return false;
            } else {
                int X = Toolkit.max(x + width, getColumnNo());
                int Y = Toolkit.max(y + height, getRowNo());
                boolean isAccessibleFlag = true;

                for (int i=0; i < X; i++) {
                    for (int j=0; j < Y; j++) {
                        if (get(x, y) != null)
                            isAccessibleFlag = false;
                    }
                }
                return isAccessibleFlag;
            }
        }


        // test whole lines and erase them
        // return the answer to 'how many lines have been erased?'
        final int squeeze() {
            boolean[] wholeFlags = new boolean[getRowNo()];
            Arrays.fill(wholeFlags, false);


            int cellCountInALine;
            for (int i = 0; i < getRowNo(); i++) {
                cellCountInALine = 0;
                for (int j = 0; j < getColumnNo(); j++) {
                    if (!isAccessibleWithoutBoundaryCheck(j, i))
                        cellCountInALine++;
                }

                if (cellCountInALine == getColumnNo())
                    wholeFlags[i] = true;
            }


            int emptyLines = 0;
            for (int i = getRowNo() - 1; i >= 0; i--) {
                if (wholeFlags[i]) { // clear this line
                    for (int j = 0; j < getColumnNo(); j++) {
                        chessBoard[j][i].detach();
                        getCellPool().add(chessBoard[j][i]);
                        chessBoard[j][i] = null;
                    }
                    emptyLines++;
                } else { // move down this line emptyLines
                    if (emptyLines > 0) {
                        for (int j = 0; j < getColumnNo(); j++) {
                            // move geometrically
                            if (chessBoard[j][i] != null) {
                                chessBoard[j][i].getCellYProperty().set(chessBoard[j][i].getCellYProperty().get() + emptyLines);
                            }

                            // move line within mirror
                            // int destLine = i + emptyLines;
                            chessBoard[j][i + emptyLines] = chessBoard[j][i];
                            chessBoard[j][i] = null;
                        }
                    }
                }
            }

            return emptyLines;
        }

        CollisionChecker() {
            chessBoard = new Cell[getColumnNo()][getRowNo()];
        }
    }
    private class CellPool extends ArrayList<Cell> {
        private Cell[] allCells;

        CellPool() {
            super(getColumnNo() * getRowNo());
            for (int i = 0; i < getColumnNo() * getRowNo(); i++)
                add(new Cell());

            allCells = toArray(new Cell[size()]);
        }

        void recoverAllAllocatedCells() {
            for (int i = 0; i < allCells.length; i++) {
                if (allCells[i].isAttached())
                    allCells[i].detach();
                if (i < size())
                    add(i, allCells[i]);
                else
                    set(i, allCells[i]);
            }

            unsetAllCooridinate();
        }

        Cell[] allocateCells(int number) {
            Cell[] allocCells = new Cell[number];

            int end = size();
            int start = end - number;

            allocCells = subList(start, end).toArray(allocCells);
            removeLast(number);
            return allocCells;
        }

        private void removeLast(int number) {
            assert size() >= number;
            for (int i = 0; i < number; i++)
                remove(size() - 1);
        }
    }


    @Override
    public int squeeze() {
        return getCollisionChecker().squeeze();
    }

    @Override
    public void unsetAllCooridinate() {
        getCollisionChecker().unsetAll();
    }

    @Override
    public Cell getCooridinate(int x, int y) {
        return getCollisionChecker().get(x, y);
    }

    @Override
    public boolean cooridinateIsAccessible(int x, int y) {
        return getCollisionChecker().isAccessible(x, y);
    }

    @Override
    public Cell[] allocateCells(int number) {
        return getCellPool().allocateCells(number);
    }

    @Override
    public void recoverAllAllocatedCells() {
        getCellPool().recoverAllAllocatedCells();
    }

    @Override
    public boolean isAccessible(int x, int y, int width, int height) {
        return getCollisionChecker().isAccessible(x, y, width, height);
    }

    @Override
    public void setCooridinate(int x, int y, Cell c) {
        getCollisionChecker().set(x, y, c);
    }

    @Override
    public void unsetCooridinate(int x, int y) {
        getCollisionChecker().unset(x, y);
    }

    private DoubleProperty cellWidth = new SimpleDoubleProperty();
    private DoubleProperty cellHeight = new SimpleDoubleProperty();

    @Override
    public ReadOnlyDoubleProperty cellWidthProperty() {
        return cellWidth;
    }

    @Override
    public void removeCell(Cell c) {
        getChildren().remove(c);
    }


    @Override
    public void addCell(Cell c) {
        getChildren().add(c);
    }

    @Override
    public ReadOnlyDoubleProperty cellHeighthProperty() {
        return cellHeight;
    }

    @Override
    public Node toJavaFXNode() {
        return (Node)this;
    }

    private int columnNumber;
    private int rowNumber;

    @Override
    public final int getColumnNo() {
        return columnNumber;
    }
    @Override
    public final int getRowNo() {
        return rowNumber;
    }



    public TetrisGrid(Paint fill, int rowNo, int columnNo
            , ObservableDoubleValue boundWidthProperty
            , ObservableDoubleValue boundHeightProperty) {
        super();

        this.columnNumber = columnNo;
        this.rowNumber = rowNo;

        Rectangle background = new Rectangle();
        background.setFill(fill);
        getChildren().add(background);
        background.widthProperty().bind(boundWidthProperty);
        background.heightProperty().bind(boundHeightProperty);
        cellWidth.bind(background.widthProperty().divide(getColumnNo()));
        cellHeight.bind(background.heightProperty().divide(getRowNo()));
    }
}

