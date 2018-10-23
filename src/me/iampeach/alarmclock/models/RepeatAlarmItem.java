package me.iampeach.alarmclock.models;

import java.time.*;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

public class RepeatAlarmItem extends AlarmItem {
    private LocalTime time;
    private HashSet<DayOfWeek> repeats;

    public RepeatAlarmItem(String title, LocalTime time, HashSet<DayOfWeek> repeats) {
        super(title);
        this.time = time;

        if (repeats.isEmpty())
            throw new IllegalArgumentException("Repeat days cannot be empty");
        this.repeats = repeats;

        AlarmTask task = new AlarmTask(title + " - " + getTimeText());
        task.setOnTaskExecute(this::activateAlarm);
        setTask(task);
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

    @Override
    public Date getUpcomingDateTime() {
        LocalDate dateNow = LocalDate.now();
        LocalDate result = null;
        for (DayOfWeek day : DayOfWeek.values()) {
            if (repeats.contains(day)) {
                result = dateNow.with(day);
                if (!result.isBefore(dateNow)) {
                    LocalTime timeNow = LocalTime.now();
                    if (!time.isBefore(timeNow))
                        break;
                }
            }
        }
        LocalDateTime upcoming = LocalDateTime.of(result, time);
        return Date.from(upcoming.atZone(ZoneId.systemDefault()).toInstant());
    }
}
