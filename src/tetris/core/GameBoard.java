package tetris.core;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import tetris.api.game.Game;
import tetris.api.game.GameState;
import tetris.tetrominos.TetrisGrid;
import tetris.tetrominos.shape.IShape;

// response for drawing the interface
public class GameBoard extends HBox {

    /* java beans properties */
    private final DoubleProperty componentWidthProperty = new SimpleDoubleProperty();
    private final DoubleProperty componentHeightProperty = new SimpleDoubleProperty();
    private final DoubleProperty topBottomPaddingProperty = new SimpleDoubleProperty();
    private final DoubleProperty leftRightPaddingProperty = new SimpleDoubleProperty();
    private final DoubleProperty mainZoneWidthProperty = new SimpleDoubleProperty();
    private final DoubleProperty rightPaneWidthProperty = new SimpleDoubleProperty();

    /* layout constants */
    static final double ComponentHeightPercentage;
    static final double ComponentWidthPercentage;
    static final double MainZoneWidthPercentage;
    static final double RightPaneWidthPercentage;
    static final double TetrominoZoneHeightPercentage;
    static final double LevelZoneHeightPercentage;
    static final double ScoreZoneHeightPercentage;
    static  {
        ComponentHeightPercentage = 0.80;
        ScoreZoneHeightPercentage = 0.20;
        ComponentWidthPercentage = 0.77;
        MainZoneWidthPercentage = 0.60;
        RightPaneWidthPercentage = 0.30;
        TetrominoZoneHeightPercentage = 0.40;
        LevelZoneHeightPercentage = 0.20;
    }



    public GameBoard(GameState gameState) {

        componentWidthProperty.bind(gameState.widthProperty().multiply(ComponentWidthPercentage));
        componentHeightProperty.bind(gameState.heightProperty().multiply(ComponentHeightPercentage));
        topBottomPaddingProperty.bind(gameState.heightProperty().subtract(componentHeightProperty).divide(2.0f));
        leftRightPaddingProperty.bind(gameState.widthProperty().subtract(componentWidthProperty).divide(2.0f));
        mainZoneWidthProperty.bind(componentWidthProperty.multiply(MainZoneWidthPercentage));
        rightPaneWidthProperty.bind(componentWidthProperty.multiply(RightPaneWidthPercentage));


        this.setWidth(gameState.getWidth());
        this.setHeight(gameState.getHeight());
        // set initial widthProperty and padding
        this.setPadding(new Insets(
                topBottomPaddingProperty.doubleValue()
                , leftRightPaddingProperty.doubleValue()
                , topBottomPaddingProperty.doubleValue()
                , leftRightPaddingProperty.doubleValue()
        ));

        // add listenser to keep padding
        gameState.widthProperty().addListener(new ChangeListener<Number>() {
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


        gameState.heightProperty().addListener(new ChangeListener<Number>() {
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

        final TetrisGrid mainZone = new TetrisGrid((Game)gameState);
        Rectangle x = new Rectangle();
        mainZone.getChildren().add(x);
        x.widthProperty().bind(mainZoneWidthProperty);
        x.heightProperty().bind(componentHeightProperty);
        this.getChildren().add(mainZone);
        IShape i = new IShape(mainZone.getCellPool());
        i.attach(mainZone);

        final VBox rightPane = new VBox();
        rightPane.spacingProperty().bind(componentHeightProperty
         .multiply(1 - TetrominoZoneHeightPercentage
                 - LevelZoneHeightPercentage - ScoreZoneHeightPercentage).multiply(0.5));
        final Rectangle tetrominoZone = new Rectangle();
        final Rectangle levelZone = new Rectangle();
        final Rectangle scoreZone = new Rectangle();
        tetrominoZone.widthProperty().bind(rightPaneWidthProperty);
        levelZone.widthProperty().bind(rightPaneWidthProperty);
        scoreZone.widthProperty().bind(rightPaneWidthProperty);
        tetrominoZone.heightProperty().bind(componentHeightProperty.multiply(TetrominoZoneHeightPercentage));
        levelZone.heightProperty().bind(componentHeightProperty.multiply(LevelZoneHeightPercentage));
        scoreZone.heightProperty().bind(componentHeightProperty.multiply(ScoreZoneHeightPercentage));
        rightPane.getChildren().addAll(tetrominoZone, levelZone, scoreZone);

        this.getChildren().add(rightPane);

        this.spacingProperty().bind(widthProperty()
                .multiply(1 - MainZoneWidthPercentage - RightPaneWidthPercentage));
    }

}
