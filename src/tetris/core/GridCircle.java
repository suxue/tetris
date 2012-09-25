package tetris.core;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;


public class GridCircle extends Circle{
    protected Position position;
    protected double radius;
    protected Grid grid;

  GridCircle(Grid grid, double x, double y, double r, Paint fill) {
    super(grid.gridToPixels(x),grid.gridToPixels(y), grid.gridScale() * r, fill);
    this.grid = grid;
    this.position = new Position(x, y);
    this.radius = r;
    updatePosition();
  }

  GridCircle(Grid grid, double x, double y, int r, Paint fill) {
    super(grid.gridToPixels(x),grid.gridToPixels(y), r, fill);
    this.grid = grid;
    this.position = new Position(x, y);
  }

  GridCircle(Grid grid, double r, Paint fill) {
    super(grid.gridScale() * r, fill);
    this.grid = grid;
  }

  /**
   * Set the radius for this circle, where the radius is expressed in grid units
   * 
   * @param radius  The new radius of the circle, expressed in grid units.
   */
  void setR(double radius) {
    this.radius = radius;
    setRadius(grid.gridScale() * radius);
  }
  
    /**
     * Move this circle to a new position
     * 
     * @param p The position where this circle should be moved to.
     */
    void moveToPosition(Position p) {
        position.x = p.x;
        position.y = p.y;
        updatePosition();
    }
    
    /**
     * Update the position of this circle to reflect its new position.
     */
    void updatePosition() {
        setCenterX(grid.gridToPixels(position.x));
        setCenterY(grid.gridToPixels(position.y));
    }

}
