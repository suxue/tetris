package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {

    DoubleProperty cellXProperty;
    DoubleProperty cellYProperty;
    private TetrisGrid grid;

    public Cell(final TetrisGrid grid) {
        this(grid, 0, 0);
    }

    public Cell(final TetrisGrid grid, double x, double y) {
        super();
        this.grid = grid;
        cellXProperty = new SimpleDoubleProperty(x);
        cellYProperty = new SimpleDoubleProperty(y);
    }

    void active() {
        widthProperty().bind(grid.cellWidthProperty());
        heightProperty().bind(grid.cellHeighthProperty());
        xProperty().bind(grid.xProperty().add(widthProperty().multiply(cellXProperty)));
        yProperty().bind(grid.yProperty().add(widthProperty().multiply(cellYProperty)));
    }

    void deactive() {
        widthProperty().unbind();
        heightProperty().unbind();
        xProperty().unbind();
        yProperty().unbind();
    }

}
