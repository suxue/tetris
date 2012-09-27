package test_Tetris;

public enum Direction {
    RIGHT,LEFT,DOWN;
    

  
        boolean isVertical() {
            return (this == LEFT || this == RIGHT||this==DOWN);
        }
       
}
    

