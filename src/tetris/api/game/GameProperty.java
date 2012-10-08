/*  Copyright (c) 2012 All Right Reserved
 *
 *  This source is subject to the GNU general public License.  Please see the
 *  gpl.txt file for more information.  All other rights reserved.
 *
 *  @file:   $File$
 *  @brief:  interface for storing game properties which are not variable among runtime
 *  @author: $Author$
 *  @date:   $Date$
 */
package tetris.api.game;

import javafx.beans.property.ReadOnlyStringProperty;

public interface GameProperty {
    ReadOnlyStringProperty version();

    String getVersion();

    ReadOnlyStringProperty name();

    String getName();
}
