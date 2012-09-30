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

    }


    enum Status {

        PREPARE_ALL,   // initial status
        SHOW_MENU,
        PLAY_GAME,
        STOP_GAME,
        END_ALL,
    }

    StatusProperty statusProperty();

    Status getStatus();

    void setStatus(Status rs);

    void play();

    void stop();

    void quit();

    void show_menu();
}
