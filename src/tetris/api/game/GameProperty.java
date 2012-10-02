/*  interface for storing game properties which are not variable among runtime
    Copyright (C) 2012, thu10e team
    This file is part of the implementaion of Tetris Game ** made by thu10e team for the assessment of COMP1110/67 ** 10 assignment.
 */
package tetris.api.game;

import javafx.beans.property.ReadOnlyStringProperty;

public interface GameProperty {
    ReadOnlyStringProperty version();

    String getVersion();

    ReadOnlyStringProperty name();

    String getName();
}
