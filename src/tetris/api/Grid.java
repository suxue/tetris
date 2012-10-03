package tetris.api;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import tetris.tetrominos.Cell;

public interface  Grid {
    public Cell get(int x, int y);
    public Cell[] allocateCells(int number);
    public Node toJavaFXNode();
    public ReadOnlyDoubleProperty cellHeighthProperty();
    public ReadOnlyDoubleProperty cellWidthProperty();
    public boolean isAccessible(int x, int y);
    public boolean isAccessible(int x, int y, int width, int height);
    public boolean isAccessiblePlain(int x, int y);
    public int getColumnNo();
    public int getRowNo();
    public int squeeze();
    public void addCell(Cell c);
    public void recoverAllocatedCells();
    public void removeCell(Cell c);
    public void set(int x, int y, Cell c);
    public void unset(int x, int y);
    public void unsetAllCooridinate();
}
