package tetris.core;



public class Position {
        double x;
        double y;

        Position(double x, double y) {
            this.x = x;
            this.y = y;
        }

        Position(Position p) {
            this.x = p.x;
            this.y = p.y;
        }

        /**
         * Are we at a junction in the grid
         * @return true if we're at a junction
         */
        boolean atNode() {
            return (x == (int) x) && (y == (int) y);
        }

        /**
         * Is this position equal to another
         * @param other The other position
         * @return true if they are equal
         */
        boolean equals(Position other) {
            return x == other.x && y == other.y;
        }

        /**
         * Move toward a node.
         * @param distance The distance we want to travel 
         * @param direction The direction we're going
         * @return the distance remaining once we've travelled to the node, or the distance yet to go
         */
        double moveTowardNode(double distance, Direction direction) {
            double value = direction.isHorizontal() ? x : y;
            double wall = direction.isIncreasing() ? Grid.ROWS - 1 : 0;
            double remainder = 0;

            if (value != wall) {
                double delta;
                double goal;
                if (direction.isIncreasing()) {
                    delta = distance;
                    goal = Math.ceil(value);
                    remainder = (value + distance) - goal;
                } else {
                    delta = -distance;
                    goal = Math.floor(value);
                    remainder = goal - (value - distance);
                }

                if (remainder >= 0 && remainder != distance) {
                    value = goal;
                } else {
                    value += delta;
                }

                if (direction.isHorizontal()) {
                    x = value;
                } else {
                    y = value;
                }
            }

            return remainder;
        }

        /**
         * Move toward a goal defined by a particular position
         * 
         * @param goal The position we're aiming to reach
         * @param distance how far we want to travel
         * @return the distance remaining once we've reached the goal
         */
        double moveTowardGoal(Position goal, double distance) {
            double remainder = 0;
            if (goal.x == x) { // vertical movement
                boolean increasing = goal.y > y;
                remainder = increasing ? goal.y - y : y - goal.y;
                if (remainder > distance) {
                    y += increasing ? distance : -distance;
                    return remainder - distance;  // return a positive value, remflects remaing distance
                } else {
                    y = goal.y;
                    return remainder - distance;  // negative reflects remaining distance to travel
                }
            } else { // horizontal movement
                boolean increasing = goal.x > x;
                remainder = increasing ? goal.x - x : x - goal.x;
                if (remainder > distance) {
                    x += increasing ? distance : -distance;
                    return remainder - distance;  // return a positive value, remflects remaing distance
                } else {
                    x = goal.x;
                    return remainder - distance;  // negative reflects remaining distance to travel
                }
            }

        }

        /**
         * Represent as string
         */
        @Override
        public String toString() {
            return "("+x+", "+y+")";
        }
    }

