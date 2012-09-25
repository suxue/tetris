package tetris.core;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;




public class Grid extends Group {
    static final int ROWS = 10;
    static final int COLS = 22;
    
    private Group dots;
    private double pixelsPerGrid;
    
    Grid(Group parent) {
        pixelsPerGrid = Math.min(parent.getScene().getWidth()/ROWS, parent.getScene().getHeight())/COLS;
        pixelsPerGrid =20;
        dots = new Group();
        
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                GridCircle circle = new GridCircle(this, r, c, 10, Color.RED);
                dots.getChildren().add(circle);
            }
        }
        getChildren().add(dots);
    }
    
    /**
     * Convert a grid position to a pixel offset
     * 
     * @param value A grid position
     * @return The pixel position corresponding to the input grid position
     */
    double gridToPixels(double value) {
        return (value + 0.5) * pixelsPerGrid;
    }
    
    /**
     * @return return the scale function used to convert from grid positions
     * to pixels
     */
    double gridScale() {
        return pixelsPerGrid;
    }


    public Grid() {
        super();

        for (int i = 1; i <= 22; i++)
            for (int j = 1; j <= 10; j++) {

                Rectangle r = new Rectangle(j * 20, i * 20, 20, 20);
                r.setFill(Color.LIGHTGRAY);
                r.setStroke(Color.BLACK);
                r.setStrokeWidth(0.5);
                this.getChildren().add(r);
            }


        Rectangle r2 = new Rectangle(315, 100, 200, 100);
        r2.setFill(Color.LIGHTSEAGREEN);
        r2.setStroke(Color.GRAY);
        r2.setStrokeWidth(2);
        this.getChildren().add(r2);

        Text t1 = new Text("TETRIMINO");
        t1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
        t1.setTranslateX(350);
        t1.setTranslateY(85);
        this.getChildren().add(t1);

        Rectangle r3 = new Rectangle(315, 275, 200, 30);
        r3.setFill(Color.LIGHTSEAGREEN);
        r3.setStroke(Color.GRAY);
        r3.setStrokeWidth(2);
        this.getChildren().add(r3);

        Text t2 = new Text("LEVEL");
        t2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
        t2.setTranslateX(370);
        t2.setTranslateY(260);
        this.getChildren().add(t2);

        Rectangle r4 = new Rectangle(315, 350, 200, 30);
        r4.setFill(Color.LIGHTSEAGREEN);
        r4.setStroke(Color.GRAY);
        r4.setStrokeWidth(2);
        this.getChildren().add(r4);

        Text t3 = new Text("SCORE");
        t3.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
        t3.setTranslateX(365);
        t3.setTranslateY(340);
        this.getChildren().add(t3);
    } 
}
