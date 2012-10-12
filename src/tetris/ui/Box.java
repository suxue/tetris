package tetris.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

public class Box extends HBox {

    public Box(double up,
               double left) {
        this(up, up, left, left);
    }

    public Box() {
        this(0, 0);
    }

    public Box(final double top, final double bottom
            , final double left, final double right) {

        if (top < 0 || top > 1 ||
            bottom < 0 || bottom > 1 ||
            left < 0 || left > 1 ||
            right < 0 || right > 1) {
            throw  new RuntimeException();
        }

        widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number width) {
                Insets oldPadding = getPadding();
                double oldTop = oldPadding.getTop();
                double oldBottom = oldPadding.getBottom();
                double newLeft =  width.doubleValue()*left;
                double newRight = width.doubleValue()*right;
                Insets newPadding = new Insets(oldTop
                        , newRight
                        , oldBottom
                        , newLeft
                );
                setPadding(newPadding);
            }
        });

        heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number height) {
                Insets oldPadding = getPadding();
                double oldLeft = oldPadding.getLeft();
                double oldRight = oldPadding.getRight();
                double newTop =  height.doubleValue()*top;
                double newBottom = height.doubleValue()*bottom;
                Insets newPadding = new Insets(newTop
                        , oldRight
                        , newBottom
                        , oldLeft
                );
                setPadding(newPadding);
            }
        });
    }
}
