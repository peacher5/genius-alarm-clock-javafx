package me.iampeach.alarmclock.models;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;

public class RepeatAlarmItem extends AlarmItem {
    private LocalTime time;
    private HashSet<DayOfWeek> repeats;

    public RepeatAlarmItem(String title, LocalTime time, HashSet<DayOfWeek> repeats) {
        super(title);
        this.time = time;
        this.repeats = repeats;
    }

    public boolean repeatContains(DayOfWeek day) {
        return repeats.contains(day);
    }

    @Override
    public int getHour() {
        return time.getHour();
    }

    @Override
    public int getMinute() {
        return time.getMinute();
    }
}
