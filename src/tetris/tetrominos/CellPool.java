package tetris.tetrominos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CellPool extends ArrayList<Cell> {
    public List<Cell> retrieveLast(int number) {
        assert number >= 1;
        assert this.size() >= number;

        int end   = size();
        int start = end - number;
        return subList(start, end);
    }

    public void removeLast(int number) {
        assert size() >= number;
        for (int i = 0; i < number; i++)
            remove(size() - 1);
    }
}

