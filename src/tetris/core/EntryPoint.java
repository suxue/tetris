/*
 *  Brief: Game entry point class, everything begins from here
 *  It will do:
 *     1. parsing and interpreting  command line options.
 *     2. initialize the entry point class(extends the javafx.application.Application class).
 *     3. initialize  
 */
package tetris.core;

import tetris.util.File;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;

import javafx.stage.Stage;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

public class EntryPoint extends Application {
    private static class ParsingSlave {

        // utility functions
        static private LongOpt[] longOptBuilder(StringBuffer shortArgs, LongOpt...items)
        {
            ArrayList<LongOpt> l = new ArrayList<LongOpt>();
            for (LongOpt i : items) {
                l.add(i);
                shortArgs.append( (new Character((char)i.getVal())).toString());
                if (i.getHasArg() == LongOpt.REQUIRED_ARGUMENT) {
                    shortArgs.append(":");
                }
            }
            return l.toArray(new LongOpt[1]);
        }


        static private void intepret(String[] args) throws IOException {
            StringBuffer optionString = new StringBuffer("");   
            LongOpt[] longOptions = longOptBuilder(optionString
                    , new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h')
                    , new LongOpt("version", LongOpt.NO_ARGUMENT, null, 'v')
                    , new LongOpt("width", LongOpt.REQUIRED_ARGUMENT, null, 'x')
                    , new LongOpt("height", LongOpt.REQUIRED_ARGUMENT, null, 'y')
                    );

            Getopt g = new Getopt(gameState.getTitle(), args 
                    ,optionString.toString(), longOptions);

            int result;
            String arg;
            while ((result = g.getopt()) != -1) {

                switch (result) {
                    case 'h':
                        System.out.format(File.readResourceFile("/txt/help.txt")
                                , gameState.getTitle());
                        System.exit(0);
                        break;
                    case 'v':
                        System.out.format(File.readResourceFile("/txt/version.txt")
                                , gameState.getTitle()
                                , gameState.getVersion());
                        System.exit(0);
                        break;
                    case 'x':
                        arg = g.getOptarg();
                        try {
                            int width = Integer.parseInt(arg);
                            gameState.setResolutionX(width);
                        } catch (NumberFormatException e) {
                            System.err.format(
                                    "Sorry, [%s] is not a valid width value\n"
                                    , arg);
                        }
                        break;
                    case 'y':
                        arg = g.getOptarg();
                        try {
                            int height = Integer.parseInt(arg);
                            System.out.println(height);
                            gameState.setResolutionY(height);
                        } catch (NumberFormatException e) {
                            System.err.format(
                                    "Sorry, but [%s] is not a valid height value\n"
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

    private static GameState gameState = new GameState();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(gameState.getTitle());
        Group root = new Group();
        Scene scene = SceneBuilder.create()
            .width(gameState.getResolutionX())
            .height(gameState.getResolutionY())
            .root(root)
            .build();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        ParsingSlave.intepret(args);
        Application.launch(args);
    }
}
