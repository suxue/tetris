package tetris.util.midi;

import javax.sound.midi.*;
import java.io.IOException;
import java.io.InputStream;

public class Track
{
    public static final int END_OF_TRACK_MESSAGE = 47;
    private static final int DRUM_TRACK = 1;
    Sequencer player;

    public void close() {
        player.close();
    }


    public Track(String file) {
        InputStream is = getClass().getResourceAsStream(file);
        Sequence seq;
        try {
            seq = MidiSystem.getSequence(is);
            player = MidiSystem.getSequencer();
            player.addMetaEventListener(new MetaEventListener() {
                @Override
                public void meta(MetaMessage event) {
                    if (event.getType() == END_OF_TRACK_MESSAGE) {
                        System.out.println("restart");
                        player.setMicrosecondPosition(0);
                        player.start();
                    }
                }
            });
            player.setSequence(seq);
            player.open();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    void play() {
        player.start();
    }

    void stop() {
        player.stop();
    }

    public static void main(String[] args) {
        Track t = new Track("/sound/1.mid");
        Track t2 = new Track("/sound/2.mid");
        try {
            Thread.sleep(10000000000l);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}

