package test_Tetris;

public enum Direction {
    RIGHT,LEFT;
    

  
        boolean isVertical() {
            return (this == LEFT || this == RIGHT);
        }
       
}
    

