/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  midi player, can hold multiple tracks.
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.util.midi;

import java.util.ArrayList;

public class Player {
    private ArrayList<Track> trackList = new ArrayList<Track>();
    private int currentTrack = -1;

    public void listen(int trackNo) {
        if (trackNo != currentTrack) {
            if (currentTrack >= 0) {
                trackList.get(currentTrack).stop();
            }
            trackList.get(trackNo).play();
            currentTrack = trackNo;
        }
    }

    public void pause() {
        trackList.get(currentTrack).stop();
    }

    public void add(Track t) {
        trackList.add(t);
    }

    public void close() {
        for (Track t : trackList) {
            t.close();
        }
    }
}
