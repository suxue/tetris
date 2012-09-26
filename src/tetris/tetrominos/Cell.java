package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
    public Cell(Paint painter, DoubleProperty sizeProperty) {
        super();
        setFill(painter);
        widthProperty().bind(sizeProperty);
        heightProperty().bind(sizeProperty);
    }
}
