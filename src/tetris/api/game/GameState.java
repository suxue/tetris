/* interface for storing game state, subclass will only contains data and its accessors
   Copyright (C) 2012, thu10e team

   This file is part of the implementaion of Tetris Game  made by thu10e team
   for the assessment of COMP1110/67  10 assignment.
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
