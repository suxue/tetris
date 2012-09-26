package tetris.api.game;

import javafx.beans.property.ReadOnlyStringProperty;

public interface  GameProperty {
    ReadOnlyStringProperty version();
    String getVersion();

    ReadOnlyStringProperty name();
    String getName();
}
