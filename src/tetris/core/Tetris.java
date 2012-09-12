/* centering game class
 */


package tetris.core;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tetris.api.GameProperty;
import tetris.api.GameState;

class GameRoot extends BorderPane {

    private static final double scaleFactor = 0.15;

    private Button _createButton(String id, String text) {
        return ButtonBuilder.create()
                .text(text)
                .id(id)
                .maxHeight(Double.MAX_VALUE)
                .maxWidth(Double.MAX_VALUE)
                .build();
    }

    private class CloseAppHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Platform.exit();
        }
    }

    private class NewGameHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            setCenter(new Grid());
        }
    }

    GameRoot() {
        Menu fileMenu = MenuBuilder.create()
                .text("Demo")
                .items(MenuItemBuilder.create().text("New")
                        .onAction(new NewGameHandler()).build()
                        , MenuItemBuilder.create().text("Save").build()
                        , SeparatorMenuItemBuilder.create().build()
                        , MenuItemBuilder.create().text("Exit")
                        .onAction(new CloseAppHandler()).build())
                .build();

        MenuBar menuBar = MenuBarBuilder.create().menus(fileMenu).build();
        menuBar.prefWidthProperty().bind(this.widthProperty());

        Button exitButton = _createButton("exitButton", "Exit");
        Button newButton = _createButton("newButton", "New Game");
        exitButton.setOnAction(new CloseAppHandler());
        newButton.setOnAction(new NewGameHandler());
        final VBox vbox = VBoxBuilder.create()
                .alignment(Pos.CENTER)
                .children(newButton
                        , _createButton("saveButton", "Save")
                        , exitButton)
                .build();
        vbox.maxWidthProperty().bind(this.widthProperty().multiply(0.77));

        this.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                Insets oldInsets = vbox.getPadding();
                Double newPadding = newValue.doubleValue() * scaleFactor;
                Insets newInsets = new Insets(oldInsets.getTop(), newPadding,
                        oldInsets.getBottom(), newPadding);
                vbox.setPadding(newInsets);
            }
        });

        this.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                Insets oldInsets = vbox.getPadding();
                Double newPadding = newValue.doubleValue() * scaleFactor;
                Insets newInsets = new Insets(newPadding, oldInsets.getLeft(),
                        newPadding, oldInsets.getRight());
                vbox.setPadding(newInsets);
                vbox.setSpacing(newPadding);
            }
        });

        this.setCenter(vbox);
        this.setTop(menuBar);
    }
}

class TetrisStatic implements GameProperty {

    private final static SimpleStringProperty _version = new SimpleStringProperty("0.01");
    private final static SimpleStringProperty _name = new SimpleStringProperty("Tetris Game");

    @Override
    public final ReadOnlyStringProperty version() {
        return _version;
    }

    @Override
    public final String getVersion() {
        return version().getValue();
    }

    @Override
    public final ReadOnlyStringProperty name() {
        return _name;
    }

    @Override
    public final String getName() {
        return name().getValue();
    }
}

class TetrisDynamic extends TetrisStatic implements GameState {
    private final SimpleStringProperty _title = new SimpleStringProperty();
    private final SimpleDoubleProperty _width = new SimpleDoubleProperty();
    private final SimpleDoubleProperty _height = new SimpleDoubleProperty();

    TetrisDynamic(double width, double height) {
        super();
        _title.setValue(name().getValue());
        _width.setValue(width);
        _height.setValue(height);
    }

    TetrisDynamic() {
        this(800, 600);
    }

    @Override
    public final StringProperty title() {
        return _title;
    }

    @Override
    public final String getTitle() {
        return title().getValue();
    }

    @Override
    public final void setTitle(String title) {
        title().setValue(title);
    }

    @Override
    public final DoubleProperty width() {
        return _width;
    }

    @Override
    public final double getWidth() {
        return width().getValue();
    }

    @Override
    public final void setWidth(double width) {
        width().setValue(width);
    }

    @Override
    public DoubleProperty height() {
        return _height;
    }

    @Override
    public double getHeight() {
        return height().getValue();
    }

    @Override
    public final void setHeight(double height) {
        height().setValue(height);
    }
}

public class Tetris extends TetrisDynamic {
    private Stage primaryStage;

    public void init(Stage primaryStage) {
        this.primaryStage = primaryStage;
        String csspath = this.getClass()
                .getResource("/css/stylesheet.css")
                .toExternalForm();

        Scene primaryScene = SceneBuilder.create()
                .root(new GameRoot())
                .stylesheets(csspath)
                .width(getWidth())
                .height(getHeight())
                .fill(Color.AQUAMARINE)
                .build();

        primaryStage.titleProperty().bindBidirectional(title());
        width().bind(primaryStage.widthProperty());
        height().bind(primaryStage.heightProperty());
        primaryStage.setScene(primaryScene);
    }


    public void start() {
        primaryStage.show();
    }

    public void stop() {
        Platform.exit();
    }
}
