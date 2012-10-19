/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  spacer which will extend all free space horizontally
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.ui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class HSpacer extends Region {
    public HSpacer() {
        HBox.setHgrow(this, Priority.ALWAYS);
    }
}
