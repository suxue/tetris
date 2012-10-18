/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  set default label font larger and add extra properties for the ease
 *          to be put into a gridPane.
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.ui;


import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/*
  delegation pattern
 */
class GridPaneAuxiliary {
    private Node me;
    private int row;
    private int column;

    public void setRow(int row) {
        GridPane.setRowIndex(me, row);
        this.row = row;
    }

    public int getRow() {
        return row;
    }



    public void setColumn(int column) {
        GridPane.setColumnIndex(me, column);
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public GridPaneAuxiliary(Node me) {
        this.me = me;
    }
}

public class LargeLabel extends Label {
    private GridPaneAuxiliary ga = new GridPaneAuxiliary(this);

    public int getColumn() {
        return ga.getColumn();
    }

    public int getRow() {
        return ga.getRow();
    }

    public void setRow(int row) {
        ga.setRow(row);
    }


    public void setColumn(int column) {
        ga.setColumn(column);
    }

    private GridPaneAuxiliary getGridPaneAuxiliary() {
        return ga;
    }


    public LargeLabel() {
        getStyleClass().add("large-label");
    }
}
