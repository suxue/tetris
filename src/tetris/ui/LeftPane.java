package tetris.ui;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tetris.api.Grid;
import tetris.tetrominos.TetrisGrid;

public class LeftPane extends Box {

    TetrisGrid playField;

    public LeftPane(Pane parent) {
        this(parent.widthProperty(), parent.heightProperty());
        parent.getChildren().add(this);
    }

    public Grid getPlayField() {
        return playField;
    }

    public LeftPane(ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty) {

        setId("leftPane");
        playField = new TetrisGrid(20, 10, this);
        prefWidthProperty().bind(widthProperty);

        Rectangle wall = new Rectangle();
        wall.setManaged(false);
        wall.xProperty().bind(playField.xShiftProperty());
        wall.yProperty().bind(playField.yShiftProperty());
        wall.widthProperty().bind(playField.minoWidthProperty().multiply(playField.getColumnNo()));
        wall.heightProperty().bind(playField.minoHeightProperty().multiply(playField.getRowNo()));
        wall.setFill(Color.web("#2c183a", 0.77));
        wall.setStroke(Color.BLACK);
        wall.setStrokeWidth(2);
        getChildren().add(wall);
    }
}
