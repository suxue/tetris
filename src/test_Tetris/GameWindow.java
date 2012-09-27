package test_Tetris;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.junit.Test;



public class GameWindow extends Application {

    private Text fps = new Text(0, 30, "");
    private long lastFrame = 0;
    private long frameCount = 0;
    
    int windowWidth;
    int windowHeight;
    int cellLength;
    int windowOriginX= 50;
    int windowOriginY= 30;
    Rectangle [][] arrayRect = new Rectangle [10] [22];
    Shapes currentShape,nextShape;
    Group root = new Group();
    Stage _primaryStage;
    Scene _primaryScene;
    Rectangle r1;
    int [][] initialPosition;
    double gravity = 20;
    double gridMoved;
    Predictor p;
    
    void generateShape(){
        Random rand = new Random();
        int s = rand.nextInt(7);
        switch(s){
        case 0:
            nextShape = Shapes.I;
            break;
        case 1:
            nextShape = Shapes.J;
            break;
        case 2: 
            nextShape = Shapes.L;
            break;
        case 3:
            nextShape = Shapes.O;
            break;
        case 4:
            nextShape = Shapes.S;
            break;
        case 5: 
            nextShape = Shapes.T;
            break;
        case 6: 
            nextShape = Shapes.Z;
            break;
        }
    }



    void drawCurrentShape(){

        initialPosition = currentShape.getStartPositions();
       // Rectangle r = new Rectangle(windowOriginX, windowOriginY, cellLength * 10, cellLength * 22 );
        //ddroot.getChildren().add(r);

        for(int i = 0; i < initialPosition.length; i ++){
            arrayRect[initialPosition[i][0]][initialPosition[i][1]] = new Rectangle(windowOriginX + cellLength * initialPosition[i][0], windowOriginY + cellLength * initialPosition[i][1], cellLength, cellLength);
            arrayRect[initialPosition[i][0]][initialPosition[i][1]].setStroke(Color.LIGHTGRAY);
            arrayRect[initialPosition[i][0]][initialPosition[i][1]].setFill(currentShape.getColor());
            root.getChildren().add(arrayRect[initialPosition[i][0]][initialPosition[i][1]]);

        }    

    }

    void drawNextShape(){

        initialPosition = nextShape.getStartPositions();
        Rectangle r = new Rectangle(windowOriginX, windowOriginY, cellLength * 10, cellLength * 22 );
        root.getChildren().add(r);

        for(int i = 0; i < initialPosition.length; i ++){
            arrayRect[initialPosition[i][0]][initialPosition[i][1]] = new Rectangle(windowOriginX+300 + cellLength * initialPosition[i][0], windowOriginY + cellLength * initialPosition[i][1], cellLength, cellLength);
            arrayRect[initialPosition[i][0]][initialPosition[i][1]].setStroke(Color.CRIMSON);
            arrayRect[initialPosition[i][0]][initialPosition[i][1]].setFill(nextShape.getColor());
            root.getChildren().add(arrayRect[initialPosition[i][0]][initialPosition[i][1]]);

        }    

    }

    


    void movement(Direction direction){
        if (direction==Direction.LEFT){
            for(int i = 0; i < initialPosition.length; i ++){
                initialPosition[i][0]--;                
            }
            drawCurrentShape();
        }

        else if (direction==Direction.RIGHT){
            for(int i = 0; i < initialPosition.length; i ++){
                initialPosition[i][0]++;                
            }    

            drawCurrentShape();
        }
        else if (direction==Direction.DOWN){
            moveDown();
        }
    }

    void moveDown(){
        System.out.println("movedY : "+initialPosition[0][1]*cellLength);
        if(initialPosition[0][1]*cellLength<=600){

            for(int i = 0; i < initialPosition.length; i ++){

                initialPosition[i][1]++;
            }

            drawCurrentShape();
        }
        else if(initialPosition[0][1]*cellLength>=600) {
            System.out.println("Drawing new shape.......");
            changeShape();

        }
    }
    void changeShape(){
        drawNextShape();
        currentShape=nextShape;
    }


    /**
     * Update the game state.
     * 
     * This is called once every JavaFX frame.
     * 
     * @param nowNanos The time (in nanoseconds) when the call
     * is made.
     */
    void updateGameState(long nowNanos) {
        if (lastFrame == 0) {
            lastFrame = nowNanos;
        } else {
            frameCount++;
            long nanos = nowNanos - lastFrame;
            updateFPS(nanos);
           
            gridMoved = (gravity * nanos) / 1000000000;
           
          
        }
        lastFrame = nowNanos;
    }

    /**
     * Update the FPS display, given the elapsed time for the last frame.
     * 
     * @param elapsedNanos  The elapsed time of the last frame, in nanoseconds
     */
    private void updateFPS(long elapsedNanos) {
        double elapsedSec = elapsedNanos / 1000000000.0;
        if (frameCount % 20 == 0) {
        drawCurrentShape();
          //  System.out.println("fps:" + 1/elapsedSec);
        }
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
                System.out.println("Left Key Pressed");

            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                /* send caterpillar right */
                movement(Direction.RIGHT);
                System.out.println("Right Key Pressed");
            }
            else if (keyEvent.getCode() == KeyCode.DOWN) {
                /* send caterpillar down */
                movement(Direction.DOWN);
                System.out.println("Down Key Pressed");
            }
            else if (keyEvent.getCode() == KeyCode.T) {
                /* send caterpillar down */
                changeShape();
                System.out.println("Down Key Pressed");
            }
        }
    };



    public GameWindow(){
        this.windowHeight = 820;
        this.cellLength = 30;
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
       
        drawNextShape();
        changeShape();
        drawCurrentShape();
        //drawShape();

        //p.generateShape();
        /* handle keyboard input */
        scene.setOnKeyPressed(inputHandler);

        root.getChildren().add(r);



        /* call updateGameState() once each frame */
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // System.err.println(now);
                updateGameState(now);
            }
        }.start();


        primaryStage.show();
        //Predictor p = new Predictor();
        //p.generateShape();
        // p.drawPredictorShape(_primaryScene, _primaryStage, root, r);
        // p.drawCurrentShape(_primaryScene, _primaryStage, root, r);
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
        int[][] testJ={{2,0},{1,1},{0,0},{-1,-1}};
        for (int i=0;i<4;i++)
            for(int j=0;j<2;j++)  {                  
                assertTrue(testI[i][j]==Shapes.I.rotation[i][j]);
                assertTrue(testJ[i][j]==Shapes.J.rotation[i][j]);
            }

    }

}


