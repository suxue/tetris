/* Game entry point class, everything begins from here
   Copyright (C) 2012, thu10e team.
   This file is part of the implementaion of Tetris Game  made by thu10e team
   for the assessment of COMP1110/67 ** 10 assignment.

   Acknowledgement: This class utilize the GNU getopt library to parse command line arguments.
 */

/*
 *  Brief:
 *  It will do:
 *     1. parsing and interpreting  command line options.
 *     2. initialize the entry point class(extends the javafx.application.Application class).
 *     3. initialize  
 */
package tetris.core;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import javafx.application.Application;
import javafx.stage.Stage;
import tetris.util.File;

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
                        System.out.format(File.readResourceFile("/txt/help.txt")
                                , tetrisGame.getTitle());
                        System.exit(0);
                        break;
                    case 'v':
                        System.out.format(File.readResourceFile("/txt/version.txt")
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
