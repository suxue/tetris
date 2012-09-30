/*
 *  The game state class stores all global variables of a game
 *  Most of them is configurable
 *  Default values list here
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
