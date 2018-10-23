package me.iampeach.alarmclock.models;

import javafx.application.Platform;

import java.util.TimerTask;

public class AlarmTask extends TimerTask {

    private String title;
    private Runnable callback;

    public AlarmTask(String title) {
        this.title = title;
    }

    public void setOnTaskExecute(Runnable callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        // TODO
        System.out.println(title);

        if (callback != null)
            Platform.runLater(() -> callback.run());
    }
}
