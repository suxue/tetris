package tetris.core;

public enum Direction {
     DOWN, LEFT, RIGHT;

    /**
     * @return true if the direction is on the horizontal plane
     */
    boolean isHorizontal() {
        return (this == LEFT || this == RIGHT);
    }

    /**
     * @return true if the direction is in the increasing direction
     */
    boolean isIncreasing() {
        return (this == DOWN || this == RIGHT);
    }
}
