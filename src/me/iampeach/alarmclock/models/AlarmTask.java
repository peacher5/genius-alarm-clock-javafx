package me.iampeach.alarmclock.models;

import java.util.TimerTask;

public class AlarmTask extends TimerTask {

    private String title;

    public AlarmTask(String title) {
        this.title = title;
    }

    @Override
    public void run() {
        // TODO
        System.out.println(title);
    }
}
