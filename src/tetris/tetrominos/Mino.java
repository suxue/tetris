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


public final class Mino extends Rectangle {

    private Grid hostGrid = null;

    public final DoubleProperty getMinoYProperty() {
        return minoYProperty;
    }

    public final DoubleProperty getMinoXProperty() {
        return minoXProperty;
    }

    private final DoubleProperty minoXProperty;
    private final DoubleProperty minoYProperty;


    public Mino(double x, double y) {
        super();
        minoXProperty = new SimpleDoubleProperty(x);
        minoYProperty = new SimpleDoubleProperty(y);
        setManaged(false);
    }

    public Mino() {
        this(0, 0);
    }


    public final void attach(Grid grid) {
        widthProperty().bind(grid.minoWidthProperty());
        heightProperty().bind(grid.minoHeighthProperty());
        xProperty().bind((widthProperty().multiply(minoXProperty)));
        yProperty().bind((heightProperty().multiply(minoYProperty)));
        grid.addMino(this);
        hostGrid = grid;
    }

    public final void detach() {
        hostGrid.removeMino(this);
        widthProperty().unbind();
        heightProperty().unbind();
        xProperty().unbind();
        yProperty().unbind();
        hostGrid = null;
    }


    public final  boolean isAttached() {
        return (hostGrid != null);
    }

    private final boolean canMoveHorizontal(int len) {
        int targetX = (int) (getMinoXProperty().get() + len);
        double targetY = getMinoYProperty().get();
        int y1 = (int) (Math.floor(targetY));
        int y2 = (int) (Math.ceil(targetY));

        if (!hostGrid.isAccessible(targetX, y1)) {
            return false;
        }

        if (y2 != y1 && !hostGrid.isAccessible(targetX, y2)) {
            return false;
        }

        return true;
    }

    public final boolean canMoveLeft() {
        return canMoveHorizontal(-1);
    }


    public final boolean canMoveRight() {
        return canMoveHorizontal(1);
    }


    public final boolean canMoveDown(double len) {
        int targetX = (int) (getMinoXProperty().get());
        int targetY = (int) (Math.ceil(getMinoYProperty().get() + len));

        return hostGrid.isAccessible(targetX, targetY);
    }
}
