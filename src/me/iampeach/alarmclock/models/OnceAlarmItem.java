package me.iampeach.alarmclock.models;

import java.time.LocalDateTime;

public class OnceAlarmItem extends AlarmItem {
    private LocalDateTime dateTime;

    public OnceAlarmItem(String title, LocalDateTime dateTime) {
        super(title);
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public int getHour() {
        return dateTime.getHour();
    }

    @Override
    public int getMinute() {
        return dateTime.getMinute();
    }
}
