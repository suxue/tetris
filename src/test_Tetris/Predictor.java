package test_Tetris;

import java.util.Random;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Predictor extends Application {

    int windowWidth;
    int windowHeight;
    int cellLength;
    int windowOriginX= 365;
    int windowOriginY= 60;
    int nextShapeId;
    Rectangle [][] arrayRect = new Rectangle [10] [22];
    Shapes nextShape;
    // generates a random shape
    void generateShape()
    {
        nextShapeId = GenerateRandomShapeID();
        switch(nextShapeId){
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
//returns shape ID
    private int GenerateRandomShapeID() {
        Random rand = new Random();
        int s = rand.nextInt(7);
        return s;
    }

    void drawShape(Scene _scene, Stage stage, Group root, Rectangle rec){
        stage.setScene(_scene);
        int [][] position = nextShape.getStartPositions();

        for(int i = 0; i < position.length; i ++){
            arrayRect[position[i][0]][position[i][1]] = new Rectangle(windowOriginX + cellLength * position[i][0], windowOriginY + cellLength * position[i][1], cellLength, cellLength);
            arrayRect[position[i][0]][position[i][1]].setStroke(Color.LIGHTGRAY);
            arrayRect[position[i][0]][position[i][1]].setFill(nextShape.getColor());
            root.getChildren().add(arrayRect[position[i][0]][position[i][1]]);

        }

        stage.show();
    }


    public Predictor(){
        this.windowHeight = 850;
        this.cellLength = 35;
        this.windowWidth = 650;


    }
}
