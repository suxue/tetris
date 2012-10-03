package tetris.tetrominos;



public class WallKick extends Cell {
    
    
    public boolean canRotateRight(double width) {
        return canMoveRight(width);

        }
    public boolean canRotateLeft (double width){
        return canMoveLeft (width);
    }
}
//http://tetrisconcept.net/wiki/SRS#Wall_Kicks