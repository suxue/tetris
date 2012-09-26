       
package test_Tetris;

import javafx.scene.paint.Color;

public enum Shapes {
    I(new int[][] {{3,1},{4,1},{5,1},{6,1}}, Color.RED, new int[][] {{2,1},{1,0},{0,-1},{-1,-2}}), 
    J(new int[][] {{3,0},{3,1},{4,1},{5,1}}, Color.MAGENTA, new int[][] {{2,0},{1,1},{0,0},{-1,-1}}),
    L(new int[][] {{3,1},{4,1},{5,1},{5,0}}, Color.YELLOW, new int[][] {{1,1},{0,0},{-1,-1},{0,-2}}),
    O(new int[][] {{4,0},{4,1},{5,0},{5,1}}, Color.CYAN, new int[][] {{0,0},{0,0},{0,0},{0,0}}), 
    S(new int[][] {{3,1},{4,1},{4,0},{5,0}}, Color.BLUE, new int[][] {{1,1},{0,0},{1,-1},{0,-2}}),
    T(new int[][] {{3,1},{4,1},{4,0},{5,1}}, Color.LIGHTGRAY, new int[][] {{1,1},{0,0},{1,-1},{-1,-1}}),
    Z(new int[][] {{3,0},{4,0},{4,1},{5,1}}, Color.GREEN, new int[][] {{2,0},{1,-1},{0,0},{-1,-1}}); 
    
    private final int[][] positions;
    private final Color color;
    private final int[][] rotation;
    
    Shapes (int[][] positionArray, Color color, int[][] rotateArray) {
        this.positions = positionArray;
        this.color = color;
        this.rotation = rotateArray;
    }

    public int[][] getStartPositions() {
        return positions;
    }

    public Color getColor() {
        return color;
    }
    
    public int[][] getRotationArray() {
        return rotation;
    }

    
};

