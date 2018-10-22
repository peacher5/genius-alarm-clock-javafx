package me.iampeach.alarmclock.models;

public abstract class AlarmItem {
    private String title;

    public AlarmItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public abstract int getHour();

    public abstract int getMinute();
}
