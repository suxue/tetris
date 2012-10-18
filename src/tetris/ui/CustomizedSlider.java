/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  ease the effort of putting a slider into a gridpane
 *  @author: $Author$
 *  @date:   $Date$
 */

package tetris.ui;

import javafx.scene.control.Slider;

public class CustomizedSlider extends Slider {
    public void setRow(int row) {
        ga.setRow(row);
    }

    public int getColumn() {
        return ga.getColumn();
    }

    public int getRow() {
        return ga.getRow();
    }

    public void setColumn(int column) {
        ga.setColumn(column);
    }

    public GridPaneAuxiliary getGa() {
        return ga;
    }

    private GridPaneAuxiliary ga;

    public CustomizedSlider() {
        ga = new GridPaneAuxiliary(this);
        getStyleClass().add("customized-slider");
        setShowTickLabels(true);
        setShowTickMarks(true);
        setMinorTickCount(1);
        setBlockIncrement(5);
        setMajorTickUnit(5);
    }
}
