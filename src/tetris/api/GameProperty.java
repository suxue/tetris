package tetris.api;

import javafx.beans.property.ReadOnlyStringProperty;

public interface  GameProperty {
    ReadOnlyStringProperty version();
    String getVersion();

    ReadOnlyStringProperty name();
    String getName();
}
