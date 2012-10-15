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

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;

import java.io.IOException;

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
        getStyleClass().add("customizedSlider");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/customizedSlider.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
