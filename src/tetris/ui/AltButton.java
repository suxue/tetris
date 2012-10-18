/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  Alternative version of javafx button
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.ui;

import javafx.scene.control.Button;

public class AltButton extends Button {
    public AltButton() {
        getStyleClass().add("alt-button");
        setFocusTraversable(false);
    }
}
