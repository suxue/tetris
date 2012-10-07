/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  all (seven) tetrominos should inplement me.
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.api;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point2D;

// a shape can mostly has four orientation
//  see http://tetrisconcept.net/wiki/Orientation
//  they are: Point UP|RIGHT|DOWN|LEFT
public interface Tetromino {

    /*
     *   @xProperty: x-coordinate of my pivot
     *   @yProperty: y-coordinate of my pivot
     */
    public DoubleProperty xProperty();
    public DoubleProperty yProperty();

    public Point2D      getPivot();
    public void         setPivot(Point2D pivot);
    public Point2D      pivotToBoundingBox(Point2D pivot);
    public Point2D      boundingBoxToPivot(Point2D bb);

    public int          getStatus();

    // after attaching, I'll be showed in that grid
    public void attach(Grid grid);
    public void detach();
    public void pin();

    // movement function family
    public void moveDown(double len);
    public void moveLeft();
    public void moveRight();

    public boolean canMoveDown(double len);
    public boolean canMoveLeft();
    public boolean canMoveRight();

    public void    rotateRight();
    public void    rotateLeft();
}
