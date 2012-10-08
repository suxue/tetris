/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  entry point class, everything begins from here
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.core;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import javafx.application.Application;
import javafx.stage.Stage;
import tetris.util.IO;

import java.io.IOException;
import java.util.ArrayList;

public class EntryPoint extends Application {

    private Tetris tetrisGame = new Tetris();

    private class ParsingSlave {

        // utility functions
        private LongOpt[] longOptBuilder(StringBuffer shortArgs, LongOpt... items) {
            ArrayList<LongOpt> l = new ArrayList<LongOpt>();
            for (LongOpt i : items) {
                l.add(i);
                shortArgs.append((new Character((char) i.getVal())).toString());
                if (i.getHasArg() == LongOpt.REQUIRED_ARGUMENT) {
                    shortArgs.append(":");
                }
            }
            return l.toArray(new LongOpt[1]);
        }


        private void interpret(String[] args)
                throws IOException { // begin interpret()

            StringBuffer optionString = new StringBuffer("");
            LongOpt[] longOptions = longOptBuilder(optionString
                    , new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h')
                    , new LongOpt("version", LongOpt.NO_ARGUMENT, null, 'v')
                    , new LongOpt("widthProperty", LongOpt.REQUIRED_ARGUMENT, null, 'x')
                    , new LongOpt("heightProperty", LongOpt.REQUIRED_ARGUMENT, null, 'y')
            );

            Getopt g = new Getopt(tetrisGame.getTitle(), args
                    , optionString.toString(), longOptions);

            int result;
            String arg;
            while ((result = g.getopt()) != -1) {

                switch (result) {
                    case 'h':
                        System.out.format(IO.readResource("/txt/help.txt"), tetrisGame.getTitle());
                        System.exit(0);
                        break;
                    case 'v':
                        System.out.format(IO.readResource("/txt/version.txt")
                                , tetrisGame.getTitle()
                                , tetrisGame.getVersion());
                        System.exit(0);
                        break;
                    case 'x':
                        arg = g.getOptarg();
                        try {
                            int width = Integer.parseInt(arg);
                            tetrisGame.setWidth(width);
                        } catch (NumberFormatException e) {
                            System.err.format(
                                    "Sorry, [%s] is not a valid widthProperty value\n"
                                    , arg);
                        }
                        break;
                    case 'y':
                        arg = g.getOptarg();
                        try {
                            int height = Integer.parseInt(arg);
                            tetrisGame.setHeight(height);
                        } catch (NumberFormatException e) {
                            System.err.format(
                                    "Sorry, but [%s] is not a valid heightProperty value\n"
                                    , arg);
                        }
                        break;
                    case '?': // unknown argument, getopt already printed error message
                        System.exit(-1);
                        // never reach here
                        break;
                } // -- end switch
            } // -- end while
        }  // -- end parseAndFill()
    } // -- end class ParsingSlave


    @Override
    public void start(Stage primaryStage) throws Exception {
        Application.Parameters args = getParameters();
        (new ParsingSlave()).interpret(args.getRaw().toArray(new String[0]));
        tetrisGame.init(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        Application.launch(args);
    }
}
