package me.iampeach.alarmclock.models;

import java.util.Date;
import java.util.Timer;

public abstract class AlarmItem {
    private String title;
    private Timer timer = new Timer();
    protected AlarmTask task;

    public AlarmItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getTimeText() {
        return String.format("%02d:%02d", getHour(), getMinute());
    }

    public void activateAlarm() {
        timer.schedule(task, getUpcomingDateTime());
    }

    public void cancelAlarm() {
        task.cancel();
        timer.cancel();
    }

    public abstract int getHour();

    public abstract int getMinute();

    public abstract Date getUpcomingDateTime();
}
