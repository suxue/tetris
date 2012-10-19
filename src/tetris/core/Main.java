/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  entry point of the game, whole story begins from here
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.core;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;




/**
 * @author shellfish
 */
public class Main extends Application {

    private Stage primaryStage;
    private Point2D cachedPosition;
    private UIController uiController;
    
   


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
    public void stop() {
        uiController.getPlayer().close();
    }


    @Override
    public void start(final Stage stage) throws Exception {
        
        primaryStage = stage;
        
     
        
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = (Parent) fxmlLoader.load(getClass().getResource("/fxml/root.fxml").openStream());
        uiController = (UIController) (fxmlLoader.getController());

        /* full screen feature
        *  this is here because the primary stage will be involved
        * */
        stage.setScene(new Scene(root));
        stage.setTitle("Tetris Game(thu10e)");
        stage.show();


        final Node helpContainer =  root.lookup("#helpContainer");
        root.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case F:
                    case F11:
                        toggleFullScreen();
                        break;
                    case N:
                        
                        uiController.newGame();
                        break;
                    case H:
                        helpContainer.setVisible(!helpContainer.isVisible());
                        break;
                    case R:
                        uiController.restartGame();
                        break;
                }
            }
        });

        uiController.getPlayer().listen(0);
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