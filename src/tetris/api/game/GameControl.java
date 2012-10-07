/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  interface for general game control purpose.
 *  @author: $Author$
 *  @date:   $Date$
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
