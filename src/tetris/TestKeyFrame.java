package tetris;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TestKeyFrame extends Application{
    
    private static final Duration keyFrameInterval =  Duration.millis(1000/60); 
    private int frameCount = 0;
    private final KeyFrame oneFrame = new KeyFrame(keyFrameInterval, 
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                       System.out.format("hello world, the %d frame\n", frameCount++);
                }
    });
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        TimelineBuilder.create()
            .cycleCount(Animation.INDEFINITE)
            .keyFrames(oneFrame)
            .build()
            .play();
        primaryStage.setTitle("test");
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }

}
