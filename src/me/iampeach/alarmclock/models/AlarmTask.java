package me.iampeach.alarmclock.models;

import javafx.application.Platform;
import me.iampeach.alarmclock.controllers.SceneUtil;

import java.util.TimerTask;

public class AlarmTask extends TimerTask {
    private String title;
    private Runnable onTaskExecute;

    public AlarmTask(String title) {
        this.title = title;
    }

    public void setOnTaskExecute(Runnable onTaskExecute) {
        this.onTaskExecute = onTaskExecute;
    }

    @Override
    public void run() {
        if (onTaskExecute != null)
            Platform.runLater(() -> onTaskExecute.run());

        Platform.runLater(() -> SceneUtil.launchAlarmWindow(title));
    }
}
