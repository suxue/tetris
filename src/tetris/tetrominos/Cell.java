package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
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
        setStroke(Color.WHITE);
    }

    public Cell() {
        this(0, 0);
    }


    public void attach(TetrisGrid grid) {
        grid.getChildren().add(this);
        widthProperty().bind(grid.cellWidthProperty());
        heightProperty().bind(grid.cellHeighthProperty());

        xProperty().bind((widthProperty().multiply(cellXProperty)));
        yProperty().bind((widthProperty().multiply(cellYProperty)));
    }

    public void detach(TetrisGrid grid) {
        widthProperty().unbind();
        heightProperty().unbind();
        xProperty().unbind();
        yProperty().unbind();
        grid.getChildren().remove(this);
    }
}
