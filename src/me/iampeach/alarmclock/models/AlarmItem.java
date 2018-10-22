package me.iampeach.alarmclock.models;

public abstract class AlarmItem {
    private String title;

    public AlarmItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getTimeText() {
        return String.format("%02d:%02d", getHour(), getMinute());
    }

    public abstract int getHour();

    public abstract int getMinute();
}
