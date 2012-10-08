/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  Generate batch of randomized sequence of tetrominos, and give out
 *  them on by one
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.util;

import java.util.Arrays;
import java.util.Random;

public class Rand {
    private boolean[] record = new boolean[7];
    private int counter = 0;
    private Random rand = new Random();

    public Rand() {
        Arrays.fill(record, false);
    }

    public int get() {
        if (counter == 7) {
            Arrays.fill(record, false);
            counter = 0;
        }

        counter++;
        int x = rand.nextInt(7);
        while (record[x]) {
            x = rand.nextInt(7);
        }
        record[x] = true;
        return x;
    }
}
