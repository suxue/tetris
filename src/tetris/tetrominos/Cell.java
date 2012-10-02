package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Rectangle;


public class Cell extends Rectangle {

    private TetrisGrid hostGrid = null;

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
        setManaged(false);
    }

    public Cell() {
        this(0, 0);
    }


    public void attach(TetrisGrid grid) {
        widthProperty().bind(grid.cellWidthProperty());
        heightProperty().bind(grid.cellHeighthProperty());
        xProperty().bind((widthProperty().multiply(cellXProperty)));
        yProperty().bind((heightProperty().multiply(cellYProperty)));
        grid.getChildren().add(this);
        hostGrid = grid;
    }

    public void detach() {
        hostGrid.getChildren().remove(this);
        widthProperty().unbind();
        heightProperty().unbind();
        xProperty().unbind();
        yProperty().unbind();
        hostGrid = null;
    }

    public boolean  isAttached() {
        return (hostGrid != null);
    }
}
