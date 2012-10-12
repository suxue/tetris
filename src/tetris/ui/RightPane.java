package tetris.ui;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import tetris.api.Grid;
import tetris.tetrominos.TetrisGrid;

class PreviewBox extends  VBox {
    public PreviewBox(ReadOnlyDoubleProperty parentWidthProperty) {
        Rectangle background = new Rectangle();
        getChildren().add(background);

        background.widthProperty().bind(parentWidthProperty.multiply(0.3));
        background.heightProperty().bind(background.widthProperty().divide(2.0));

        setId("previewBox");
    }

}

public class RightPane extends VBox {
    public Grid getPreviewField() {
        return previewField;
    }


    private Grid previewField;
    public RightPane(Pane parent) {
        setSpacing(0);
        setPadding(new Insets(0, 20, 0, 0));
        setId("rightPane");

        PreviewBox previewBox = new PreviewBox(parent.widthProperty());
        getChildren().add(previewBox);

        previewField = new TetrisGrid(2, 4, previewBox);

        parent.getChildren().add(this);
    }
}
