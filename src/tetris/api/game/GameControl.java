/* interface for general game control purpose.
   Copyright (C) 2012, thu10e team
   This file is part of the implementaion of Tetris Game ** made by thu10e team
   for the assessment of COMP1110/67 ** 10 assignment.
 */

package tetris.api.game;

import javafx.beans.property.SimpleIntegerProperty;


public interface GameControl {

    public static class StatusProperty extends SimpleIntegerProperty {
        public StatusProperty(Status s) {
            super();
            setValue(s);
        }

        public void setValue(Status s) {
            setValue(s.ordinal() - Status.values()[0].ordinal());
        }

        public static Status toStatus(Number s) {
            return Status.values()[s.intValue()];
        }
    }


    enum Status {
        PREPARE_ALL,   // initial status
        SHOW_MENU,
        PLAY_GAME,
        RESTART_GAME,
        STOP_GAME,
        END_ALL,
    }


    interface StatusListener {
        void callback(Status oldStatus, Status newStatus);
    }

    void addStatusListener(final StatusListener sl);


    StatusProperty statusProperty();

    Status getStatus();

    void setStatus(Status rs);

    void play();

    void restart();

    void stop();

    void quit();

    void show_menu();
}
