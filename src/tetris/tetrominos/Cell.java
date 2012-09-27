package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {

    public DoubleProperty getCellYProperty() {
        return cellYProperty;
    }

    public DoubleProperty getCellXProperty() {
        return cellXProperty;
    }

    private DoubleProperty cellXProperty;
    private DoubleProperty cellYProperty;


    public Cell(double x, double y) {
        super();
        cellXProperty = new SimpleDoubleProperty(x);
        cellYProperty = new SimpleDoubleProperty(y);
    }

    public Cell() {
        this(0, 0);
    }

    private TetrisGrid grid = null;
    public void attach(TetrisGrid grid) {
        this.grid = grid;
        grid.show(this);
        widthProperty().bind(grid.cellWidthProperty());
        heightProperty().bind(grid.cellHeighthProperty());
        //TODO bind x/y cooridate
        xProperty().bind(grid.getParent().layoutXProperty().multiply(-1).add(widthProperty().multiply(cellXProperty)));
        yProperty().bind(grid.getParent().layoutYProperty().multiply(-1).add(widthProperty().multiply(cellYProperty)));
    }

    public void detach() {
        widthProperty().unbind();
        heightProperty().unbind();
        xProperty().unbind();
        yProperty().unbind();
        grid.hide(this);
    }
}
