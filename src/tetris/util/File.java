package tetris.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class File {
    public static String readResourceFile(String filename) throws IOException {
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        String line = null;
        String fn = File.class.getResource(filename).getFile();
        try {
            in = new BufferedReader(new FileReader(fn));
            while ((line = in.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } finally {
            in.close();
        }
        return sb.toString();
    }
}
