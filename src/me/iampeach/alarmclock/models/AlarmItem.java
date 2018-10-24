package me.iampeach.alarmclock.models;

import java.util.Date;
import java.util.Timer;

public abstract class AlarmItem {
    private String title;
    private Timer timer = new Timer();
    private AlarmTask task;

    AlarmItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getTimeText() {
        return String.format("%02d:%02d", getHour(), getMinute());
    }

    void setTask(AlarmTask task) {
        this.task = task;
    }

    public AlarmTask getTask() {
        return task;
    }

    public void activateAlarm() {
        timer.schedule(task, getUpcomingDateTime());
    }

    public void cancelAlarm() {
        timer.cancel();
    }

    public abstract int getHour();

    public abstract int getMinute();

    protected abstract Date getUpcomingDateTime();
}
