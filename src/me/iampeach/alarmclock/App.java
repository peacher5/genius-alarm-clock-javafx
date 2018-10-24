package me.iampeach.alarmclock;

import javafx.application.Application;
import javafx.stage.Stage;
import me.iampeach.alarmclock.controllers.SceneUtil;
import me.iampeach.alarmclock.models.AlarmItemList;

public class App extends Application {

    @Override
    public void start(Stage window) {
        SceneUtil.loadMainScene(window);
        SceneUtil.setMinSize(window);
        window.show();
    }

    @Override
    public void stop() {
        AlarmItemList.getInstance().cancelAlarmTasks();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
