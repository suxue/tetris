/*
 *  The game state class stores all global variables of a game
 *  Most of them is configurable
 *  Default values list here
 */

package tetris.api;

import javafx.beans.property.*;


public interface GameState {
    StringProperty title();
    String getTitle();
    void setTitle(String title);

    DoubleProperty width();
    double getWidth();
    void   setWidth(double width);

    DoubleProperty height();
    double getHeight();
    void   setHeight(double height);
}
