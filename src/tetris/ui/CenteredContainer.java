package tetris.ui;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class CenteredContainer extends StackPane{

    private GridPaneAuxiliary ga = new GridPaneAuxiliary(this);

    private GridPaneAuxiliary getGridPaneAuxiliary() {
        return ga;
    }

    public int getColumn() {
        return getGridPaneAuxiliary().getColumn();
    }

    public int getRow() {
        return getGridPaneAuxiliary().getRow();
    }

    public void setRow(int row) {
        getGridPaneAuxiliary().setRow(row);
    }

    public void setColumn(int column) {
        getGridPaneAuxiliary().setColumn(column);
    }

    public CenteredContainer() {
        setAlignment(Pos.CENTER);
    }
}
