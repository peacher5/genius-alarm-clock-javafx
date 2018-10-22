package me.iampeach.alarmclock;

import javafx.application.Application;
import javafx.stage.Stage;
import me.iampeach.alarmclock.controllers.SceneUtil;

public class App extends Application {

    @Override
    public void start(Stage window) throws Exception {
        SceneUtil.loadMainScene(window);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
