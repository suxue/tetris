package tetris.util;

import java.util.ArrayList;
import java.util.Arrays;

class Point {
    int x;
    int y;
    Tree tree;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override public String toString() {
        return x + ", " + y;
    }

}


class Tree extends ArrayList<Point>  {
    private Point root;
    private int offset;
    private Tree parent;

    Tree(Point root) {
        this.root = root;
        this.add(root);
        offset = 0;
    }

    Point getRoot() {
        return root;
    }

    // t will be subtree of me
    void merge(Tree t) {
        t.parent = this;
    }

    @Override
    public boolean add(Point point) {
        point.tree = this;
        return super.add(point);
    }

    int getOffset() {return  offset;}
    void increaseOffset(int add) {offset += add;}
}

public class Algo {
    public static Tree buildTree(Point root, Object[][] grid, int columnNo, int rowNo) {
        Tree tree = new Tree(root);
        buildTree(root, tree, grid, columnNo, rowNo);
        return tree;
    }

    public static void buildTree(Point root, Tree destTree, Object[][] grid, int columnNo, int rowNo) {
        Point p;
        if (root.x < columnNo  - 1 && grid[root.x + 1][root.y] != null)  { // test right
            p = new Point(root.x + 1, root.y);
            destTree.add(p);
            buildTree(p, destTree, grid, columnNo, rowNo);
        }

        if (root.y > 0 && grid[root.x][root.y - 1] != null) {
            p = new Point(root.x, root.y - 1);
            destTree.add(p);
            buildTree(p, destTree, grid, columnNo, rowNo);
        }
    }

    public static void compact(Object[][]  grid) {
        int column = grid.length;
        int row    = grid[0].length;


        // how many items has been scanned in this row
        int[] record = new int[row];
        int scanRow    = row - 1;
        Arrays.fill(record, 0);
        Point root;
        // scan grid to forest
        // scane from left to right, bottom to top

        Point p;
        int r = row - 1;
        // find next root
        do {
            root = null;
            // find first not full-scanned row
            while (r >= 0) {
                if (record[r] < column) { // scan this row
                    for (int i = record[r]; i < column; i++) {
                        record[r]++;
                        if (grid[i][r] != null) {
                            // find the root
                            root = new Point(i, r);
                            break;
                        }
                    }
                }

                if (root == null) {
                    r--;
                } else {
                    break;
                }
            }


            // use this root to composite tree
            Tree tree = buildTree(root, grid, column, row);
        } while (root != null);
    }
}
