/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  Mino is the basic construction unit for tetrominos
 *  @author: $Author$
 *  @date:   $Date$
 */

package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Rectangle;
import tetris.api.Grid;


public class Mino extends Rectangle {

    private Grid hostGrid = null;

    public DoubleProperty getMinoYProperty() {
        return minoYProperty;
    }

    public DoubleProperty getMinoXProperty() {
        return minoXProperty;
    }

    private DoubleProperty minoXProperty;
    private DoubleProperty minoYProperty;


    public Mino(double x, double y) {
        super();
        minoXProperty = new SimpleDoubleProperty(x);
        minoYProperty = new SimpleDoubleProperty(y);
        setManaged(false);
    }

    public Mino() {
        this(0, 0);
    }


    public void attach(Grid grid) {
        widthProperty().bind(grid.minoWidthProperty());
        heightProperty().bind(grid.minoHeighthProperty());
        xProperty().bind((widthProperty().multiply(minoXProperty)));
        yProperty().bind((heightProperty().multiply(minoYProperty)));
        grid.addMino(this);
        hostGrid = grid;
    }

    public void detach() {
        hostGrid.removeMino(this);
        widthProperty().unbind();
        heightProperty().unbind();
        xProperty().unbind();
        yProperty().unbind();
        hostGrid = null;
    }


    public boolean isAttached() {
        return (hostGrid != null);
    }

    private boolean canMoveHorizontal(int len) {
        int targetX = (int)(getMinoXProperty().get() + len);
        double targetY = getMinoYProperty().get();
        int y1 = (int)(Math.floor(targetY));
        int y2 = (int)(Math.ceil(targetY));

        if (! hostGrid.isAccessible(targetX, y1)) {
            return false;
        }

        if (y2 != y1 && ! hostGrid.isAccessible(targetX, y2)) {
            return false;
        }

        return true;
    }

    public boolean canMoveLeft() {
        return canMoveHorizontal(-1);
    }


    public boolean canMoveRight() {
        return canMoveHorizontal(1);
    }


    public boolean canMoveDown(double len) {
        int targetX = (int)(getMinoXProperty().get());
        int targetY = (int)(Math.ceil(getMinoYProperty().get() + len));

        return hostGrid.isAccessible(targetX, targetY);
    }
}
