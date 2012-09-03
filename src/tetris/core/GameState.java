/*
 *  The game state class stores all global variables of a game
 *  Most of them is configurable
 *  Default values list here
 */

package tetris.core;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;




public class GameState {
    
    public class FrozenPropertyException extends Exception {
        public FrozenPropertyException() {
            super("Error, frozen Property cannot be changed any more!");
        }
    }

    private final SimpleStringProperty      version;
    public final ReadOnlyStringProperty versionProperty() {
        return version;
    }
    
    private final SimpleStringProperty      title;
    public final ReadOnlyStringProperty titleProperty() {
        return title;
    }
    
    /* note, width & height property only writable  before game.fire()
     *  after that, they will be binded to the corresponding properties of the
     *  primary stage.
     * */
    private final SimpleIntegerProperty     width;
    public final IntegerProperty widthProperty() {
        return width;
    }
    
    private final SimpleIntegerProperty     height;
    public final IntegerProperty heightProperty() {
        return height;
    }
    
    

    public GameState() {
        super();
        version = new SimpleStringProperty(this, "version" , "0.01");
        title = new SimpleStringProperty(this, "title" , "Tetris Game");
        width = new SimpleIntegerProperty(this, "width" , 800);
        height = new SimpleIntegerProperty(this, "height" , 600);
    }

    public final String getVersion() {
        return version.getValue();
    }

    public final int getWidth() {
        return width.getValue();
    }

    public final void setWidth(int width) throws FrozenPropertyException {
        if (widthProperty().isBound()) {
            throw new FrozenPropertyException();
        }
        this.width.setValue(width);
    }

    public final int getHeight() {
        return height.getValue();
    }

    public final void setHeight(int height) throws FrozenPropertyException {
        if (widthProperty().isBound()) {
            throw new FrozenPropertyException();
        }
        this.height.setValue(height);
    }

    public final String getTitle() {
        return title.getValue();
    }
}
