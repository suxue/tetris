package tetris.api;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import tetris.tetrominos.Mino;

public interface  Grid {
    public Mino get(int x, int y);
    public Mino[] allocateMinos(int number);
    public Node toJavaFXNode();
    public ReadOnlyDoubleProperty minoHeighthProperty();
    public ReadOnlyDoubleProperty minoWidthProperty();
    public boolean isAccessible(int x, int y);
    public boolean isAccessible(int x, int y, int width, int height);
    public boolean isAccessiblePlain(int x, int y);
    public int getColumnNo();
    public int getRowNo();
    public int squeeze();
    public void addMino(Mino c);
    public void recoverAllocatedMinos();
    public void removeMino(Mino c);
    public void set(int x, int y, Mino c);
    public void unset(int x, int y);
    public void unsetAllCooridinate();
}
