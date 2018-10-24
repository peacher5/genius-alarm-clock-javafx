package me.iampeach.alarmclock.models;

import javafx.application.Platform;
import javafx.stage.Stage;
import me.iampeach.alarmclock.controllers.SceneUtil;

import java.util.TimerTask;

public class AlarmTask extends TimerTask {
    private String title;
    private Runnable onTaskExecute;

    public AlarmTask(String title) {
        this.title = title;
    }

    public void setOnTaskExecute(Runnable callback) {
        this.onTaskExecute = callback;
    }

    @Override
    public void run() {
        if (onTaskExecute != null)
            Platform.runLater(() -> onTaskExecute.run());

        Platform.runLater(() -> {
            Stage window = new Stage();
            SceneUtil.loadAlarmScene(window, title);
            window.show();
        });
    }
}
