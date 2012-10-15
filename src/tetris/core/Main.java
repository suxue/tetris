/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.core;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author shellfish
 */
public class Main extends Application {

    private Stage primaryStage;
    private Point2D cachedPosition;

    private void toggleFullScreen() {
        if (primaryStage.fullScreenProperty().get()) {
            primaryStage.setFullScreen(false);
            primaryStage.setX(cachedPosition.getX());
            primaryStage.setY(cachedPosition.getY());
        } else {
            cachedPosition = new Point2D(primaryStage.getX(), primaryStage.getY());
            primaryStage.setFullScreen(true);
        }
    }


    @Override
	public void start(final Stage stage) throws Exception {
        primaryStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader();
		Parent root = (Parent) fxmlLoader.load(getClass().getResource("/fxml/root.fxml").openStream());
        final UIController uiController = (UIController)(fxmlLoader.getController());

        /* full screen feature
        *  this is here because the primary stage will be involved
        * */
        primaryStage.fullScreenProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean a, Boolean st) {
                uiController.getFullscreenButton().setSelected(st);
            }
        });

        uiController.getFullscreenButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                toggleFullScreen();
            }
        });


		stage.setScene(new Scene(root));
		stage.show();


        root.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.F) {
                    toggleFullScreen();
                }
            }
        });

	}

	/**
	 * The main() method is ignored in correctly deployed JavaFX
	 * application. main() serves only as fallback in case the application
	 * can not be launched through deployment artifacts, e.g., in IDEs with
	 * limited FX support. NetBeans ignores main().
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}