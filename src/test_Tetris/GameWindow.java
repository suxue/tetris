package test_Tetris;

import java.util.Random;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameWindow extends Application {

    int windowWidth;
    int windowHeight;
    int cellLength;
    int windowOriginX= 50;
    int windowOriginY= 30;
    Rectangle [][] arrayRect = new Rectangle [10] [22];
    Shapes currentShape;

    Group root = new Group();

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
        int [][] position = currentShape.getStartPositions();

        for(int i = 0; i < position.length; i ++){
            arrayRect[position[i][0]][position[i][1]] = new Rectangle(windowOriginX + cellLength * position[i][0], windowOriginY + cellLength * position[i][1], cellLength, cellLength);
            arrayRect[position[i][0]][position[i][1]].setStroke(Color.LIGHTGRAY);
            arrayRect[position[i][0]][position[i][1]].setFill(currentShape.getColor());
            root.getChildren().add(arrayRect[position[i][0]][position[i][1]]);

        }

    }


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
        


    
        generateShape();

        root.getChildren().add(r);
        //        for(int i = 0; i < 10; i++)
        //            for(int j = 0; j < 22; j++)
        //            {  
        //                {
        //                    arrayRect[i][j].setFill(Color.AQUA);
        //                }
        //                root.getChildren().add(arrayRect[i][j]);
        //            }
        drawShape();

        primaryStage.show();

    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);    
    }

}
