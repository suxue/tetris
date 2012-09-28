package tetris.core;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import tetris.api.game.Game;
import tetris.api.game.GameState;
import tetris.tetrominos.TetrisGrid;
import tetris.tetrominos.shape.IShape;

// response for drawing the interface
public class GameBoard extends HBox {

    private Game game = null;
    private TetrisGrid playField = null;
    private TetrisGrid predicationField = null;


    private class GameLogic {

        public GameLogic() {
            game.runningStatusProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue
                        , Boolean oldStatus, Boolean newStatus) {
                    if (newStatus) { // start
                        IShape i1 = new IShape(playField.getCellPool());
                        IShape i2 = new IShape(playField.getCellPool());
                        i1.attach(playField);
                        i2.attach(predicationField);
                    } else {

                    }
                }
            });
        }

    }

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
        ComponentWidthPercentage = 0.77;
        MainZoneWidthPercentage = 0.60;
        RightPaneWidthPercentage = 0.30;
        TetrominoZoneHeightPercentage = 0.15;
        LevelZoneHeightPercentage = 0.30;
        ScoreZoneHeightPercentage = 0.30;
    }

    private TetrisGrid createPlayFieldGrid() {
        return (playField = new TetrisGrid(game, Color.BLACK, 20, 10, mainZoneWidthProperty, componentHeightProperty));
    }

    private TetrisGrid createPredicationField() {
        return (predicationField = new TetrisGrid(game, Color.BLACK, 2, 4, rightPaneWidthProperty, componentHeightProperty.multiply(TetrominoZoneHeightPercentage)));
    }

    public GameBoard(GameState gameState) {
        game = (Game)gameState;

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

        TetrisGrid playField = createPlayFieldGrid();
        this.getChildren().add(playField);

        final VBox rightPane = new VBox();
        rightPane.spacingProperty().bind(componentHeightProperty
         .multiply(1 - TetrominoZoneHeightPercentage
                 - LevelZoneHeightPercentage - ScoreZoneHeightPercentage).multiply(0.5));

        final TetrisGrid tetrominoZone = createPredicationField();

        final Rectangle levelZone = new Rectangle();
        final Rectangle scoreZone = new Rectangle();
        levelZone.widthProperty().bind(rightPaneWidthProperty);
        scoreZone.widthProperty().bind(rightPaneWidthProperty);
        levelZone.heightProperty().bind(componentHeightProperty.multiply(LevelZoneHeightPercentage));
        scoreZone.heightProperty().bind(componentHeightProperty.multiply(ScoreZoneHeightPercentage));
        rightPane.getChildren().addAll(tetrominoZone, levelZone, scoreZone);


        this.getChildren().add(rightPane);

        this.spacingProperty().bind(widthProperty()
                .multiply(1 - MainZoneWidthPercentage - RightPaneWidthPercentage));


        new GameLogic();
    }

}
