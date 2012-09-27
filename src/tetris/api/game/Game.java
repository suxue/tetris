package tetris.api.game;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.Node;


public interface Game {
    ReadOnlyBooleanProperty runningStatusProperty();
    boolean getRunningStatus();
    void setRunningStatus(boolean rs);

    void addNode(Node n);
    void removeNode(Node n);

    void start();
    void stop();
    void quit();
}
