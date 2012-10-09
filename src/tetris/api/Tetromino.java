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

import javafx.geometry.Point2D;

// a shape can mostly has four orientation
//  see http://tetrisconcept.net/wiki/Orientation
//  they are: Point UP|RIGHT|DOWN|LEFT
public interface Tetromino {

    public Point2D getPivot();

    public void setPivot(Point2D pivot);

    // the offset to transfer current pivot to the corresponding bonding box(4x2)
    // only need to be implemented for the first of the four shapes
    public Point2D getInitialBoundingBoxOffset();

    // 0, 1, 2, 3
    public int getStatus();

    // after attaching, I'll be showed in that grid
    public void attach(Grid grid);

    public void detach();

    public void pin();
    public void align();

    // movement function family
    public void moveDown(double len);

    public void moveLeft();

    public void moveRight();

    public boolean canMoveDown(double len);

    public boolean canMoveLeft();

    public boolean canMoveRight();


    //  clock-wise/right
    //  counter clock-wise/left
    public boolean rotate(boolean clockWise, boolean canRotateUp);
}


