/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  Utility IO class
 *  @author: $Author$
 *  @date:   $Date$
 */

package tetris.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IO {
    public static String readResource(String resourceName) {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                IO.class.getResourceAsStream(resourceName)));
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = in.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
