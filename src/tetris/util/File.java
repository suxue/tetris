/* Utility class
   Copyright (C) 2012, thu10e team.
   This file is part of the implementaion of Tetris Game  made by thu10e team
   for the assessment of COMP1110/67 ** 10 assignment.
 */

package tetris.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class File {
    public static String readResourceFile(String filename) throws IOException {
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        String line;
        String fn = File.class.getResource(filename).getFile();
        try {
            in = new BufferedReader(new FileReader(fn));
            while ((line = in.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } finally {
            if (in != null)
                in.close();
        }
        return sb.toString();
    }
}
