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
import javafx.beans.property.StringProperty;




public class GameState {
    

    private final SimpleStringProperty      version;
    public final ReadOnlyStringProperty versionProperty() {
        return version;
    }
    
    private final SimpleStringProperty      title;
    public final StringProperty titleProperty() {
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

    public final void setWidth(int width) {
        this.width.setValue(width);
    }

    public final int getHeight() {
        return height.getValue();
    }

    public final void setHeight(int height) {
        this.height.setValue(height);
    }

    public final String getTitle() {
        return title.getValue();
    }
    
    public final void setTitle(String title) {
        titleProperty().setValue(title);
    }
}
