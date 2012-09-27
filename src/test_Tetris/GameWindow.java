package test_Tetris;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import org.junit.Test;



public class GameWindow extends Application {

    int windowWidth;
    int windowHeight;
    int cellLength;
    int windowOriginX= 50;
    int windowOriginY= 30;
    Rectangle [][] arrayRect = new Rectangle [10] [22];
    Shapes currentShape;
    Group root = new Group();
    Stage _primaryStage;
    Scene _primaryScene;
    Rectangle r1;


    
    void generateShape(){
        Random rand = new Random();
        int s = rand.nextInt(7);
        switch(s){
        case 0:
            currentShape = Shapes.I;
            break;
        case 1:
            currentShape = Shapes.J;
            break;
        case 2: 
            currentShape = Shapes.L;
            break;
        case 3:
            currentShape = Shapes.O;
            break;
        case 4:
            currentShape = Shapes.S;
            break;
        case 5: 
            currentShape = Shapes.T;
            break;
        case 6: 
            currentShape = Shapes.Z;
            break;
        }
    }




    void drawShape(){
        int [][] initialPosition = currentShape.getStartPositions();


        for(int i = 0; i < initialPosition.length; i ++){
            arrayRect[initialPosition[i][0]][initialPosition[i][1]] = new Rectangle(windowOriginX + cellLength * initialPosition[i][0], windowOriginY + cellLength * initialPosition[i][1], cellLength, cellLength);
            arrayRect[initialPosition[i][0]][initialPosition[i][1]].setStroke(Color.LIGHTGRAY);
            arrayRect[initialPosition[i][0]][initialPosition[i][1]].setFill(currentShape.getColor());
            root.getChildren().add(arrayRect[initialPosition[i][0]][initialPosition[i][1]]);

        }

    }


    void movement(Direction direction){


    }


    /**
     * Handle input events from the keyboard.
     */
    private final EventHandler<KeyEvent> inputHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(final KeyEvent keyEvent) {
            if (keyEvent.getCode() == KeyCode.Q) {
                /* exit when Q is pressed */
                System.exit(0);
            } else if (keyEvent.getCode() == KeyCode.LEFT) {
                /* send tetro left */
                movement(Direction.LEFT);

            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                /* send caterpillar right */

            }
        }
    };



    public GameWindow(){
        this.windowHeight = 850;
        this.cellLength = 35;
        this.windowWidth = 650;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Test Tetris");
        Scene scene = new Scene(root, windowWidth, windowHeight);
        primaryStage.setScene(scene);

        Rectangle r = new Rectangle(windowOriginX, windowOriginY, cellLength * 10, cellLength * 22 );

        _primaryScene = scene;
        _primaryStage = primaryStage;
        r1 = new Rectangle(450, 45, 150, 120 );


        generateShape();

        root.getChildren().add(r);

        drawShape();

        primaryStage.show();
        Predictor p = new Predictor();
        p.generateShape();
        p.drawShape(_primaryScene, _primaryStage, root, r1);
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);    
    }


    @Test
    public void testRandomShapeGenerator(){

        int[][] testI= {{2,1},{1,0},{0,-1},{-1,-2}};
        for (int i=0;i<4;i++)
            for(int j=0;j<2;j++)                    
                assertTrue(testI[i][j]==Shapes.I.rotation[i][j]);

    }




