/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  the core class of whole project, connects EntryPoint to the UI and logic part
 *  @author: $Author$
 *  @date:   $Date$
 */

package tetris.core;

import java.util.HashMap;
import java.util.Map;

public final class Option {
    Map map = new HashMap<String, Object>();

    public Object get(String key) {
        return map.get(key);
    }

    public int getInteger(String key) {
        return Integer.class.cast(get(key));
    }

    public String getString(String key) {
        return String.class.cast(get(key));
    }

    public void set(String key, Object value) {
        map.put(key, value);
    }

    public Option() {
        // default values
        set("level", 1);
        set("column", 10);
        set("row", 20);
        set("user", "John");
        set("version", "0.01");
        set("title", "Option Game");
        set("frameRate", 60);
        set("baseSpeed", 1.0/48);
    }
}