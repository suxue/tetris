/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  The playfield and core game logic
 *  @author: $Author$
 *  @date:   $Date$
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

import java.util.ArrayList;
import java.util.Arrays;


public final class TetrisGrid extends AnchorPane implements Grid {


    private MinoPool minoPool = null;
    private OccupationMonitor occupationMonitor = null;

    private OccupationMonitor getOccupationMonitor() {
        if (occupationMonitor == null) {
            occupationMonitor = new OccupationMonitor();
            occupationMonitor.unsetAll();
        }
        return occupationMonitor;
    }


    private MinoPool getMinoPool() {
        if (minoPool == null) {
            minoPool = new MinoPool();
        }
        return minoPool;
    }

    @Override
    public boolean isAccessible(int x, int y) {
        return getOccupationMonitor().isAccessible(x, y);
    }


    private class OccupationMonitor {
        private Mino[][] chessBoard;

        void set(int x, int y, Mino c) {
            chessBoard[x][y] = c;
        }

        void unset(int x, int y) {
            chessBoard[x][y] = null;
        }

        Mino get(int x, int y) {
            return chessBoard[x][y];
        }

        void unsetAll() {
            for (Mino[] i : chessBoard) {
                Arrays.fill(i, null);
            }
        }

        // a Mino can reach/access/occupy this gridblock of (x, y)
        boolean isAccessible(int x, int y) {
            // do boundary check
            if (y < -2 || x < 0 || x >= getColumnNo() || y >= getRowNo()) {
                return false;
            } else if (y >= -2 && y < 0) {
                return true;
            } else {
                return isAccessibleWithoutBoundaryCheck(x, y);
            }
        }

        private boolean isAccessibleWithoutBoundaryCheck(int x, int y) {
            return get(x, y) == null;
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
                        getMinoPool().add(chessBoard[j][i]);
                        chessBoard[j][i] = null;
                    }
                    emptyLines++;
                } else { // move down this line emptyLines
                    if (emptyLines > 0) {
                        for (int j = 0; j < getColumnNo(); j++) {
                            // move geometrically
                            if (chessBoard[j][i] != null) {
                                chessBoard[j][i].getMinoYProperty().set(chessBoard[j][i].getMinoYProperty().get() + emptyLines);
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

        OccupationMonitor() {
            chessBoard = new Mino[getColumnNo()][getRowNo()];
        }
    }

    private class MinoPool extends ArrayList<Mino> {
        private Mino[] allMinos;

        MinoPool() {
            super(getColumnNo() * getRowNo());
            for (int i = 0; i < getColumnNo() * getRowNo(); i++)
                add(new Mino());

            allMinos = toArray(new Mino[size()]);
        }

        void recoverAllAllocatedMinos() {
            for (int i = 0; i < allMinos.length; i++) {
                if (allMinos[i].isAttached())
                    allMinos[i].detach();
                if (i < size())
                    add(i, allMinos[i]);
                else
                    set(i, allMinos[i]);
            }

            unsetAllCooridinate();
        }

        Mino[] allocateMinos(int number) {
            Mino[] allocMinos = new Mino[number];

            int end = size();
            int start = end - number;

            allocMinos = subList(start, end).toArray(allocMinos);
            removeLast(number);
            return allocMinos;
        }

        private void removeLast(int number) {
            assert size() >= number;
            for (int i = 0; i < number; i++)
                remove(size() - 1);
        }
    }


    @Override
    public int squeeze() {
        return getOccupationMonitor().squeeze();
    }

    @Override
    public void unsetAllCooridinate() {
        getOccupationMonitor().unsetAll();
    }

    @Override
    public Mino get(int x, int y) {
        return getOccupationMonitor().get(x, y);
    }


    @Override
    public Mino[] allocateMinos(int number) {
        return getMinoPool().allocateMinos(number);
    }

    @Override
    public void recoverAllocatedMinos() {
        getMinoPool().recoverAllAllocatedMinos();
    }


    @Override
    public void set(int x, int y, Mino c) {
        getOccupationMonitor().set(x, y, c);
    }

    @Override
    public void unset(int x, int y) {
        getOccupationMonitor().unset(x, y);
    }

    private DoubleProperty minoWidth = new SimpleDoubleProperty();
    private DoubleProperty minoHeight = new SimpleDoubleProperty();

    @Override
    public ReadOnlyDoubleProperty minoWidthProperty() {
        return minoWidth;
    }

    @Override
    public void removeMino(Mino c) {
        getChildren().remove(c);
    }


    @Override
    public void addMino(Mino c) {
        getChildren().add(c);
    }

    @Override
    public ReadOnlyDoubleProperty minoHeighthProperty() {
        return minoHeight;
    }

    @Override
    public AnchorPane toJavaFXNode() {
        return (AnchorPane) this;
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
        minoWidth.bind(background.widthProperty().divide(getColumnNo()));
        minoHeight.bind(background.heightProperty().divide(getRowNo()));
    }
}

