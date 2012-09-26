package tetris.api.game;

import javafx.beans.property.ReadOnlyBooleanProperty;


public interface Game {
    ReadOnlyBooleanProperty runningStatusProperty();
    boolean getRunningStatus();
    void setRunningStatus(boolean rs);

    void start();
    void stop();
    void quit();
}
