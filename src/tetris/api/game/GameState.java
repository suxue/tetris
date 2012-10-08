/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  interface for storing game state, subclass will only contains data and its accessors
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.api.game;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;


public interface GameState {
    StringProperty title();

    String getTitle();

    void setTitle(String title);

    DoubleProperty widthProperty();

    double getWidth();

    void setWidth(double width);

    DoubleProperty heightProperty();

    double getHeight();

    void setHeight(double height);
}
