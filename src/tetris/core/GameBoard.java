package tetris.core;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tetris.api.GameState;


// response for drawing the interface
public class GameBoard extends HBox {
    private static final double ComponentHeightPercentage = 0.80;
    private static final double ComponentWidthPercentage = 0.77;

    private final DoubleProperty componentWidthProperty = new SimpleDoubleProperty();
    private final DoubleProperty componentHeightProperty = new SimpleDoubleProperty();
    private final DoubleProperty topBottomPaddingProperty = new SimpleDoubleProperty();
    private final DoubleProperty leftRightPaddingProperty = new SimpleDoubleProperty();
    private final DoubleProperty mainZoneWidthProperty = new SimpleDoubleProperty();
    private final DoubleProperty rightPaneWidthProperty = new SimpleDoubleProperty();


    private static final double MainZoneWidthPercentage = 0.60;
    private static final double RightPaneWidthPercentage = 0.30;
    private static final double TetriminoZoneHeightPercentage = 0.40;
    private static final double LevelZoneHeightPercentage = 0.20;
    private static final double ScoreZoneHeightPercentage = 0.20;

    private final DoubleProperty unitWidthPixcelsProperty = new SimpleDoubleProperty();
    private final DoubleProperty unitHeightPixcelsProperty = new SimpleDoubleProperty();

    public GameBoard(GameState gs) {

        componentWidthProperty.bind(gs.width().multiply(ComponentWidthPercentage));
        componentHeightProperty.bind(gs.height().multiply(ComponentHeightPercentage));
        topBottomPaddingProperty.bind(gs.height().subtract(componentHeightProperty).divide(2.0f));
        leftRightPaddingProperty.bind(gs.width().subtract(componentWidthProperty).divide(2.0f));
        mainZoneWidthProperty.bind(componentWidthProperty.multiply(MainZoneWidthPercentage));
        rightPaneWidthProperty.bind(componentWidthProperty.multiply(RightPaneWidthPercentage));

        unitHeightPixcelsProperty.bind(componentHeightProperty.divide(22));
        unitWidthPixcelsProperty.bind(mainZoneWidthProperty.divide(10));

        this.setWidth(gs.getWidth());
        this.setHeight(gs.getHeight());
        // set initial width and padding
        this.setPadding(new Insets(
                topBottomPaddingProperty.doubleValue()
                , leftRightPaddingProperty.doubleValue()
                , topBottomPaddingProperty.doubleValue()
                , leftRightPaddingProperty.doubleValue()
        ));

        // add listenser to keep padding
        gs.width().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue
                    , Number oldVal, Number newVal) {
                final double newPadding = leftRightPaddingProperty.doubleValue();
                final Insets oldInsets = GameBoard.this.getPadding();
                final Insets newInsets = new Insets(oldInsets.getTop(), newPadding
                        , oldInsets.getBottom(), newPadding);
                GameBoard.this.setPadding(newInsets);
            }
        });


        gs.height().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue
                    , Number oldVal, Number newVal) {
                final double newPadding = topBottomPaddingProperty.doubleValue();
                final Insets oldInsets = GameBoard.this.getPadding();
                final Insets newInsets = new Insets(newPadding, oldInsets.getRight()
                        , newPadding, oldInsets.getLeft());
                GameBoard.this.setPadding(newInsets);
            }
        });

        final Rectangle mainZone = new Rectangle();
        mainZone.setFill(Color.GREY);
        mainZone.widthProperty().bind(mainZoneWidthProperty);
        mainZone.heightProperty().bind(componentHeightProperty);
        this.getChildren().add(mainZone);


        final VBox rightPane = new VBox();
        rightPane.spacingProperty().bind(componentHeightProperty
         .multiply(1 - TetriminoZoneHeightPercentage
                 - LevelZoneHeightPercentage - ScoreZoneHeightPercentage).multiply(0.5));
        final Rectangle tetriminoZone = new Rectangle();
        final Rectangle levelZone = new Rectangle();
        final Rectangle scoreZone = new Rectangle();
        tetriminoZone.widthProperty().bind(rightPaneWidthProperty);
        levelZone.widthProperty().bind(rightPaneWidthProperty);
        scoreZone.widthProperty().bind(rightPaneWidthProperty);
        tetriminoZone.heightProperty().bind(componentHeightProperty.multiply(TetriminoZoneHeightPercentage));
        levelZone.heightProperty().bind(componentHeightProperty.multiply(LevelZoneHeightPercentage));
        scoreZone.heightProperty().bind(componentHeightProperty.multiply(ScoreZoneHeightPercentage));
        rightPane.getChildren().addAll(tetriminoZone, levelZone, scoreZone);

        this.getChildren().add(rightPane);


        this.spacingProperty().bind(widthProperty()
                .multiply(1 - MainZoneWidthPercentage - RightPaneWidthPercentage));
    }

}


// run all underlying game logic
class GameLogic {


}
