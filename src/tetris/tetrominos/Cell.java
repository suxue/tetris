/* Cell is the basic construction unit for tetrominos
   Copyright (C) 2012, thu10e team.
   This file is part of the implementaion of Tetris Game  made by thu10e team
   for the assessment of COMP1110/67 ** 10 assignment.
 */

package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Rectangle;
import tetris.api.Grid;


public class Cell extends Rectangle {

    private Grid hostGrid = null;

    public DoubleProperty getCellYProperty() {
        return cellYProperty;
    }

    public DoubleProperty getCellXProperty() {
        return cellXProperty;
    }

    private DoubleProperty cellXProperty;
    private DoubleProperty cellYProperty;


    public Cell(double x, double y) {
        super();
        cellXProperty = new SimpleDoubleProperty(x);
        cellYProperty = new SimpleDoubleProperty(y);
        setManaged(false);
    }

    public Cell() {
        this(0, 0);
    }


    public void attach(Grid grid) {
        widthProperty().bind(grid.cellWidthProperty());
        heightProperty().bind(grid.cellHeighthProperty());
        xProperty().bind((widthProperty().multiply(cellXProperty)));
        yProperty().bind((heightProperty().multiply(cellYProperty)));
        grid.addCell(this);
        hostGrid = grid;
    }

    public void detach() {
        hostGrid.removeCell(this);
        widthProperty().unbind();
        heightProperty().unbind();
        xProperty().unbind();
        yProperty().unbind();
        hostGrid = null;
    }


    public boolean isAttached() {
        return (hostGrid != null);
    }

    public boolean canMoveLeft(double len) {
        int targetX = (int) (getCellXProperty().get() - len);
        double targetY = getCellYProperty().get();
        int y1 = (int) (Math.floor(targetY));
        int y2 = (int) (Math.ceil(targetY));

        if (targetX < 0)
            return false;
        else {
            // check y1
            if (!hostGrid.isAccessiblePlain(targetX, y1)) {
                return false;
            } else if (y2 != y1
                 && ! hostGrid.isAccessiblePlain(targetX, y2)) {
                return false;
            } else
                return true;
        }
    }


    public boolean canMoveRight(double len) {
        int targetX = (int) (getCellXProperty().get() + len);
        double targetY = getCellYProperty().get();
        int y1 = (int) (Math.floor(targetY));
        int y2 = (int) (Math.ceil(targetY));

        if (targetX > (hostGrid.getColumnNo() - 1))
            return false;
        else {
            // check y1
            if (!hostGrid.isAccessiblePlain(targetX, y1)) {
                return false;
            } else if (y2 != y1
                   && !hostGrid.isAccessiblePlain(targetX, y2)) {
                    return false;
            } else { // can move
                return true;
            }
        }
    }


    public boolean canMoveDown(double len) {
        int targetX = (int) getCellXProperty().get();
        double targetY = getCellYProperty().get() + 1 + len;
        int y = (int) (Math.floor(targetY));

        // check y1
        if (targetY >= hostGrid.getRowNo()) {
            return false;
        }

        return hostGrid.isAccessiblePlain(targetX, y);
    }
}
