package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import javax.swing.*;

public class Block extends Rectangle {
    public Block(Paint painter, DoubleProperty sizeProperty) {
        super();
        setFill(painter);
        widthProperty().bind(sizeProperty);
        heightProperty().bind(sizeProperty);
    }
}
