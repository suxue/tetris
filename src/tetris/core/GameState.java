package tetris.core;

public class GameState {
    
    private final String version = "0.01";
    private final String title    = "Tetris Game";
    private int resolutionX = 800;
    private int resolutionY = 600;
    
    public GameState() {
        super();
    }
    
    public String getVersion() {
        return version;
    }
    
    public int getResolutionX() {
        return resolutionX;
    }
    
    public void setResolutionX(int resolutionX) {
        this.resolutionX = resolutionX;
    }
    
    public int getResolutionY() {
        return resolutionY;
    }
    
    public void setResolutionY(int resolutionY) {
        this.resolutionY = resolutionY;
    }

    public String getTitle() {
        return title;
    }
}