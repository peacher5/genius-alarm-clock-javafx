package me.iampeach.alarmclock.models;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

public class RepeatAlarmItem extends AlarmItem {
    private LocalTime time;
    private HashSet<DayOfWeek> repeats;

    public RepeatAlarmItem(String title, LocalTime time, HashSet<DayOfWeek> repeats) {
        super(title);
        this.time = time;
        this.repeats = repeats;
    }

    public boolean repeatsContains(DayOfWeek day) {
        return repeats.contains(day);
    }

    public String getRepeatsText() {
        ArrayList<String> days = new ArrayList<>();
        for (DayOfWeek day : DayOfWeek.values())
            if (repeatsContains(day))
                days.add(day.getDisplayName(TextStyle.SHORT, new Locale("th")).replace(".", ""));
        return String.join(", ", days);
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
