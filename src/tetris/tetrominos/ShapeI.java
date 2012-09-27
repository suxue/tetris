package tetris.tetrominos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/*
------------
| |  |  |  |  <-- postionY
------------
    ^
  postionX
*/
public class ShapeI {
    DoubleProperty xProperty;
    DoubleProperty yProperty;

    public ShapeI(double positionX, double positionY) {
        xProperty = new SimpleDoubleProperty(positionX);
        yProperty = new SimpleDoubleProperty(positionY);
    }

}
